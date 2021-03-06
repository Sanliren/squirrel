package cn.thirdpart.squirrel.poetry.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.any())
                .build()
                .apiInfo(demoApiInfo());
    }

    private ApiInfo demoApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "squirrel-user的API文档",//大标题
                "Test REST API, all the applications could access the Object model data via JSON.",//小标题
                "1.0",//版本
                "NO terms of service",//服务条款
                new Contact("Sanliren","Sanliren","123@qq.com"),//作者
                "Spring Boot Demo",//链接显示文字
                "http://localhost:8080/demo/getAll"//网站链接
        );
        return apiInfo;
    }

}
