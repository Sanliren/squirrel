package cn.thirdpart.squirrel.user;

import cn.thirdpart.squirrel.user.authfeignclient.AuthServiceClient;
import cn.thirdpart.squirrel.user.vo.JWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    AuthServiceClient authServiceClient;

    @Test
    public void getToken(){//测试通过，可以获取到token
//        JWT jwt = authServiceClient.getToken("Basic dXNlcjoxMjM0NTY=", "password", "admin", "123456");
//        System.out.println("jwt的返回内容是："+jwt.getAccess_token());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String up = "Basic dXNlcjoxMjM0NTY=";
        headers.add("Authorization", up);
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
        HttpEntity<String> formEntity = new HttpEntity<String>(headers);

        ResponseEntity<JWT> forEntity = restTemplate.postForEntity("http://localhost:9999/oauth/token?grant_type=password&username=admin&password=123456", formEntity, JWT.class);
        log.info("返回的结果是：{}", forEntity);
    }

}
