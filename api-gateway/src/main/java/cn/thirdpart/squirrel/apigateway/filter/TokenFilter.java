package cn.thirdpart.squirrel.apigateway.filter;

import cn.thirdpart.squirrel.apigateway.authfeignclient.AuthFeignClient;
import cn.thirdpart.squirrel.apigateway.config.redis.RedisUtil;
import cn.thirdpart.squirrel.apigateway.vo.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
@Component
public class TokenFilter extends ZuulFilter {


    @Autowired
    RedisUtil redisUtil;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;//前置过滤
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;//filter执行顺序，值越小越先执行
//        return 0;
    }

    @Override
    public boolean shouldFilter() {//是否应该过滤
        //这里写一下例外访问地址，不对这些地址进行过滤，其他的都过滤
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("uri:{}；method：{}", request.getRequestURI(), request.getMethod());

         if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            log.info("预检请求方法是："+request.getMethod());
            return false;
        }

        if("/user/user/user".equalsIgnoreCase(request.getRequestURI())){
            return false;
        } else if("/user/user/login".equalsIgnoreCase(request.getRequestURI())){
            return false;
        } else if ("/user/user/register".equalsIgnoreCase(request.getRequestURI())){
            return false;
        } else if ("/app/token".equalsIgnoreCase(request.getRequestURI())){
            return false;
        } else if (request.getRequestURI().contains("/oauth")){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        ResponseResult result = null;//返回对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            token = request.getHeader("authorization");
        }
        if(token.startsWith("Basic") || token.startsWith("basic")){
            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(HttpStatus.OK.value());
            requestContext.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
            return  null;
        }
        if((token.startsWith("Bearer") || token.startsWith("bearer"))&&StringUtils.isNotBlank(token)){
            token = token.substring(7);
        }
        if((token.startsWith("Bearer") || token.startsWith("bearer"))&&StringUtils.isBlank(token)){
            token = request.getParameter("Authorization");
            token = token .substring(7);
        }
        if(StringUtils.isBlank(token)){
            log.error("token参数为空，禁止访问");
            //设置为false则不往下走(不调用api接口)authorization_code,refresh_token,password,implicit,client_credentials,refresh_token
            requestContext.setSendZuulResponse(false);
            //响应一个状态码：401
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.set("isSuccess", false);
            result = new ResponseResult(0, "token参数为空，禁止访问", requestContext.getResponse());
//            return  JSON.toJSONString(request, true);
            return null;
        } else {
            //校验token
            String authorization = "Basic YXV0aF9zZXJ2ZXI6MTIzNDU2";//auth_server:123456
            //通过认证服务拿到json格式的token
//            String token_r = authFeignClient.checkToken(authorization, token);
            Jwt jwt_object = JwtHelper.decode(token);
            //从jwt_object中获取payload负载部分
            String jwt_claims = jwt_object.getClaims();
//            String token_r = JSON.toJSONString(jwt_claims);
            //通过access_token获取当前用户id，
            JSONObject tokenJson = JSON.parseObject(jwt_claims);
            long deadline = tokenJson.getLongValue("exp");//过期时间

            Date now = new Date();
            long nowTime = now.getTime()/1000;
            if((nowTime - deadline) >= 0){
                //token过期了
                log.error("token过期了，禁止访问");
                //设置为false则不往下走(不调用api接口)
                requestContext.setSendZuulResponse(false);
                //响应一个状态码：401
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                requestContext.set("isSuccess", false);
                result = new ResponseResult(0, "token过期了，禁止访问", requestContext.getResponse());
//                return  JSON.toJSONString(request, true);
                return null;
            } else {
                String identifier = tokenJson.getString("identifier");
                String currentUserId = tokenJson.getString("currentUserId");

                if(StringUtils.isNotBlank(identifier) && StringUtils.isNotBlank(currentUserId)){
                    //token未过期，比对与redis中的token是否相同，不同返回，相同向下走流程
                    String userJwtKey = identifier + "_" + currentUserId;
                    String jwt_redis = (String)redisUtil.get(userJwtKey);
                    //判断jwt中的access_token是否和传递的token相同
                    JSONObject jwtJson = JSON.parseObject(jwt_redis);
                    String redis_access_token = jwtJson.getString("access_token");
                    if( ! redis_access_token.equals(token)){
                        //token与redis的不同
                        log.error("token异常，禁止访问");
                        //设置为false则不往下走(不调用api接口)
                        requestContext.setSendZuulResponse(false);
                        //响应一个状态码：401
                        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                        requestContext.set("isSuccess", false);// 设值，让下一个Filter看到上一个Filter的状态
                        result = new ResponseResult(0, "token异常，禁止访问", requestContext.getResponse());
//                        return  JSON.toJSONString(request, true);
                        return null;
                    } else {
                        //将token替换成解析的json格式（jwt_claims）,将jwt的负载部分放到参数中
                        log.info("成功过滤！！！niddnlskh");

                        request.getParameterMap();// 关键步骤，一定要get一下,下面这行代码才能取到值
                        Map<String, List<String>> requestQueryParams = requestContext.getRequestQueryParams();

                        if (requestQueryParams == null) {
                            requestQueryParams = new HashMap<>();
                        }

                    // 添加jwt_claims参数
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(jwt_claims+"");
                        requestQueryParams.put("jwt_claims", arrayList);

                        requestContext.setRequestQueryParams(requestQueryParams);

                        try{
                            InputStream in = requestContext.getRequest().getInputStream();
                            String newbody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                            //查看最新的请求参数
                            log.info("******************"+newbody+"==============="+JSON.toJSONString(requestContext.getRequestQueryParams()));
                        } catch (IOException e){
                            e.printStackTrace();
                        }


                        requestContext.setSendZuulResponse(true);
                        requestContext.setResponseStatusCode(HttpStatus.OK.value());
                        requestContext.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
                        return  null;
                    }
                } else {
                    return null;
                }

            }
        }


    }
}
