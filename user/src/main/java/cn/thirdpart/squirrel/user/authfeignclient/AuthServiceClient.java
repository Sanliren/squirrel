package cn.thirdpart.squirrel.user.authfeignclient;

import cn.thirdpart.squirrel.user.vo.JWT;
import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * feign客户端，主要用来调用auth-server服务的接口
 */
@FeignClient(value="auth-server")
public interface AuthServiceClient {


    @RequestMapping(value ="/oauth/token", method = RequestMethod.POST)
    String getToken(@RequestHeader("Authorization")String authorization,
                 @RequestParam("grant_type")String type,
                 @RequestParam("username")String username,
                 @RequestParam("password")String password);





   /* @Component
    static class FeignConfiguration{
        @Bean
        public Contract feignContract(){
            return new feign.Contract.Default();
        }

        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
            return new BasicAuthRequestInterceptor("user","123456");
        }
    }*/



//    @Component
//    static class AuthServiceHystrix implements AuthServiceClient{
//        @Override
//        public JWT getToken(String authorization, String type, String username, String password) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//    }

}
