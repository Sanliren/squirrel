package cn.thirdpart.squirrel.authserver;

import cn.thirdpart.squirrel.authserver.feignclient.UserFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    UserFeignClient userFeignClient;

    @Test
    public void feignTest(){
        String s = userFeignClient.findByUsername("admin");
        System.out.println("返回的信息是："+s);
    }

}
