package cn.thirdpart.squirrel.lovewords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LovewordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovewordsApplication.class, args);
    }
}
