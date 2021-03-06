package cn.thirdpart.squirrel.apigateway.authfeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign客户端，主要用来调用auth-server服务的接口
 */
@FeignClient(value="auth-server")
public interface AuthFeignClient {

    @RequestMapping(value ="/oauth/check_token", method = RequestMethod.GET)
    String checkToken(@RequestHeader("Authorization") String authorization,
                    @RequestParam("token") String token);

}
