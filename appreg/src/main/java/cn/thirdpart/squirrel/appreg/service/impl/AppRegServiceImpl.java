package cn.thirdpart.squirrel.appreg.service.impl;

import cn.thirdpart.squirrel.appreg.authfeignclient.AuthFeignClient;
import cn.thirdpart.squirrel.appreg.config.redis.RedisUtil;
import cn.thirdpart.squirrel.appreg.dao.AppRegJdbcDao;
import cn.thirdpart.squirrel.appreg.dao.InterfaceJdbcDao;
import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import cn.thirdpart.squirrel.appreg.entity.AppReg;
import cn.thirdpart.squirrel.appreg.service.AppRegService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.List;


@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class AppRegServiceImpl implements AppRegService {

    @Autowired
    AuthFeignClient authFeignClient;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    AppRegJdbcDao appRegJdbcDao;
    @Autowired
    InterfaceJdbcDao interfaceJdbcDao;

    public AppReg appReg(AppReg appReg){

        return appRegJdbcDao.appReg(appReg);
    }

    public AppReg getAppByAppkey(String appKey){

        return appRegJdbcDao.getAppByAppkey(appKey);
    }

    public String getAppToken(String appKey){

        AppReg appReg = this.getAppByAppkey(appKey);
        if(appReg != null){

            String app_redis_jwt = (String) redisUtil.get("App_"+appKey);
            if(StringUtils.isNotBlank(app_redis_jwt)){
                return app_redis_jwt;
            }

            String authorization = "";
            String content = Base64Utils.encodeToString((appReg.getAppKey()+":"+appReg.getAppSecret()).getBytes());
            authorization = "Basic " + content;
            String jwt_app_token = authFeignClient.getToken(authorization, "client_credentials");
            JSONObject jsonObject = JSON.parseObject(jwt_app_token);
            long deadline = jsonObject.getLongValue("expires_in");//过期时间
            String app_redis_key = "App_" + appKey;
            //将jwt保存到redis
            redisUtil.set(app_redis_key, jwt_app_token, deadline);

            return jwt_app_token;

        } else {
            return null;
        }

    }


    public String getAppToken2(String id){

        AppInterfaceRelation relation = interfaceJdbcDao.findAppInterface(id);

        if(relation != null && relation.getStatus() == 1){

            String app_redis_jwt = (String) redisUtil.get("App_Interface_Relation_"+id);
            if(StringUtils.isNotBlank(app_redis_jwt)){
                return app_redis_jwt;
            } else {
                AppReg appReg = this.getAppByAppkey(relation.getAppId());
                String authorization = "";
                String content = Base64Utils.encodeToString((appReg.getAppKey()+":"+appReg.getAppSecret()).getBytes());
                authorization = "Basic " + content;
                String jwt_app_token = authFeignClient.getToken(authorization, "client_credentials");
                JSONObject jsonObject = JSON.parseObject(jwt_app_token);
                long deadline = jsonObject.getLongValue("expires_in");//过期时间
                String app_redis_key = "App_Interface_Relation_"+id;
                //将jwt保存到redis
                redisUtil.set(app_redis_key, jwt_app_token, deadline);

                return jwt_app_token;
            }

        } else {
            log.error("应用和接口状态是："+relation.getStatus() + "，无法访问！！");
            return null;
        }

    }


    public List<AppReg> getMyApps(String userid){
        return appRegJdbcDao.getMyAppRegs(userid);
    }


    public List<AppReg> getHotApp(int num) {

        return appRegJdbcDao.findApps(num);
    }


}
