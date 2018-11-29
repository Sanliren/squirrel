package cn.thirdpart.squirrel.authserver.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 提供调用user服务的接口，这个应该写在user服务中<br/>
 * 然后再在本服务中引入user的jar包<br/>
 * @EnableFeignClients(basePackages = "***")
 */
@FeignClient(name = "user")
public interface UserFeignClient {

    /**
     * 根据用户名获取用户信息
     * @param identifier
     * @return
     */
    @RequestMapping(value = "/user/user", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String findByUsername(@RequestParam(value = "username") String identifier);

}
