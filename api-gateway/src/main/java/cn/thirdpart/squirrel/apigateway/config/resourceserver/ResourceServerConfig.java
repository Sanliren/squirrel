package cn.thirdpart.squirrel.apigateway.config.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore ;
    /*@Autowired
    CorsFilter corsFilter;*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //Spring Security的filter默认再整个filter chain的最前边，因此需要把我们自己写的跨域的filter放在最前边
//        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);

        http.csrf().disable()
                .authorizeRequests()
                //对预检请求（pre-flight）放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(
                        "/user/user/login",
                        "/user/user/register",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/user/user/user",
                        "/auth/oauth/token",
                        "/auth/**",
                        "/auth/auth/test",
                        "/application/routes",
                        "/app/token").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
//                .antMatchers("/**").authenticated();
                    .anyRequest().authenticated();

        // 禁用缓存
        http.headers().cacheControl();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

}

