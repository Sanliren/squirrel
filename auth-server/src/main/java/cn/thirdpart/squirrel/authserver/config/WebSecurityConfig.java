package cn.thirdpart.squirrel.authserver.config;

import cn.thirdpart.squirrel.authserver.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//主要是为了在资源上添加@PreAuthorize("hasAuthority('FOO_WRITE')")注解时，访问需要权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 实现了UserDetailsService，主要负责用户信息的获取
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl ;//自己写的类




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request,response,authException)->response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests()
            .antMatchers( "/oauth/token", "/auth/test").permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .httpBasic()
        ;
        //不拦截 oauth 开放的资源
        /*http.csrf().disable();
        http.requestMatchers()
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().permitAll();*/


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
//        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }


    /**
     * 通过authenticationManager， WebSecurityConfig 与 OAuth2Configuration关联起来了。
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
