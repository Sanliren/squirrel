package cn.thirdpart.squirrel.authserver.service;

import cn.thirdpart.squirrel.authserver.vo.LoginUserVo;
import cn.thirdpart.squirrel.authserver.vo.RoleVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class MyUserDetails implements UserDetails {

    private LoginUserVo loginUser;

    public MyUserDetails(LoginUserVo loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roleList = new HashSet<>();
        if(this.loginUser.getUserinfo().getRoles() != null){
            for(RoleVo roleVo : this.loginUser.getUserinfo().getRoles()){
                GrantedAuthority authority = new SimpleGrantedAuthority(roleVo.getRoleName());
                roleList.add(authority);
            }
        }

        return roleList;
    }

    @Override
    public String getPassword() {
        return loginUser.getCredential();
    }

    @Override
    public String getUsername() {
        return loginUser.getIdentifier();
    }

    /**
     * isAccountNonExpired----账号是否过期
     * isAccountNonLocked----账号是否冻结，可恢复
     * isCredentialsNonExpired----密码是否过期
     * isEnabled----账号是否删除，不可恢复
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
