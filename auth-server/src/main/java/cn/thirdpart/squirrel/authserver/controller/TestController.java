package cn.thirdpart.squirrel.authserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class TestController {

    /**
     * 测试Feign调用
     */
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public String testFeignReturn(){
        return "auth-server feign return is OK";
    }

}
