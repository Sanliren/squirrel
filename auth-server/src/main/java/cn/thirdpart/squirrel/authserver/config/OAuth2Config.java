package cn.thirdpart.squirrel.authserver.config;

import cn.thirdpart.squirrel.authserver.service.MyUserDetails;
import cn.thirdpart.squirrel.authserver.service.UserDetailsServiceImpl;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer // 开启授权服务功能
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl ;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    // 配置客户端基本信息（给谁发令牌）
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory().withClient("user")// 创建一个客户端 名字是user-service
                .secret(passwordEncoder().encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("refresh_token", "password");
        */
//                .accessTokenValiditySeconds(3600);
        //在这里创建，数据会出现在：oauth_client_details 表中
        /*clients.jdbc(dataSource)
                .withClient("user")
                .secret(passwordEncoder().encode("123456"))
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("service");*/
        //这个地方指的是从jdbc查出数据来存储
        clients.withClientDetails(clientDetails());

         /*clients.jdbc(dataSource)
                .withClient("app_ceshi")
                .secret(passwordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit")
                .scopes("app")
                .redirectUris("http://www.baidu.com")
         ;*/

    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
//                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();//允许表单登录
    }
//    TokenEnhancer
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl)//若无，refresh_token会有UserDetailsService is required错误

        ;
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /*public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("cnsesan-jwt.jks"),
                "cnsesan123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("cnsesan-jwt"));
        return converter;
    }*/

    public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("cnsesan-jwt.jks"),
                "cnsesan123".toCharArray());

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter(){
            /**
             * 重写增强token的方法
             * 自定义返回相应的信息
             *
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                if(authentication.getUserAuthentication() != null){
                    String username = authentication.getUserAuthentication().getName();
                    MyUserDetails currentUser = (MyUserDetails) authentication.getUserAuthentication().getPrincipal();
                    //自定义token属性
                    Map<String, Object> map = new HashMap<>();
                    map.put("identifier", username);
//                map.put("roles", currentUser.getAuthorities());
                    map.put("currentUserId", currentUser.getLoginUser().getId());
                    map.put("nickName", currentUser.getLoginUser().getUserinfo().getNickname());

                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
                    OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);

//                return super.enhance(accessToken, authentication);
                    return enhancedToken;
                } else {
                    return super.enhance(accessToken, authentication);
                }


            }
        };
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("cnsesan-jwt"));
        return converter;
    }

}

