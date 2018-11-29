package cn.thirdpart.squirrel.user.vo;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import lombok.Data;

/**
 * 这个类主要用来进行登录时进行数据传输
 */
@Data
public class UserLoginDTO {

    private LoginUser loginUser;

    private JWT jwt;
}
