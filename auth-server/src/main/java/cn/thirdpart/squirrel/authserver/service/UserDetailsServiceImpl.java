package cn.thirdpart.squirrel.authserver.service;

import cn.thirdpart.squirrel.authserver.feignclient.UserFeignClient;
import cn.thirdpart.squirrel.authserver.vo.LoginUserVo;
import cn.thirdpart.squirrel.authserver.vo.RoleVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 这个类主要是自定义用户认证逻辑
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserFeignClient userFeignClient;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        String userJson = userFeignClient.findByUsername(s);
        log.info(userJson);
        //将userjson字符串转换成Json对象
        JSONObject jsonObject = JSON.parseObject(userJson);
        JSONObject data = jsonObject.getJSONObject("data");
        log.info("the data json is : "+data.toJSONString());
        JSONObject loginUserJson = data.getJSONObject("user");
        log.info("the user json is : "+loginUserJson.toJSONString());
        //将userinfo的json字符串转换成userinfoVo对象
        LoginUserVo loginUserVo = JSON.parseObject(loginUserJson.toJSONString(), new TypeReference<LoginUserVo>(){});

        log.info("在auth-server服务中json转换对象的用户昵称："+loginUserVo.getUserinfo().getNickname());

        //先通过role对象构造GrantedAuthority对象
        Set<GrantedAuthority> roleList = new HashSet<>();
        for(RoleVo roleVo : loginUserVo.getUserinfo().getRoles()){
            GrantedAuthority authority = new SimpleGrantedAuthority(roleVo.getRoleName());
            roleList.add(authority);
        }
        log.info("在auth-server服务中GrantedAuthority的json字符串为："+JSON.toJSONString(roleList));

        log.info("identifier is : "+loginUserVo.getIdentifier() + "; and credential is : "+loginUserVo.getCredential() + "; and roleList is : " + roleList);
        //构造UserDetails对象
        UserDetails userDetails = new User(loginUserVo.getIdentifier(), loginUserVo.getCredential(), roleList);

        /**
         * isAccountNonExpired----账号是否过期
         * isAccountNonLocked----账号是否冻结，可恢复
         * isCredentialsNonExpired----密码是否过期
         * isEnabled----账号是否删除，不可恢复
         */

        return userDetails;
    }



}
