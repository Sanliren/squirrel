package cn.thirdpart.squirrel.user.vo;

import lombok.Data;

@Data
public class JWT {
    public JWT(){}
    private String access_token,token_type,refresh_token,scope,jti;
    private int expires_in;
}
