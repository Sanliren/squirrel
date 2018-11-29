package cn.thirdpart.squirrel.authserver.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
/**
 * 用户登录表
 */
public class LoginUserVo implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 用户信息表id
     */
    private UserinfoVo userinfo;



    /**
     * 登录类型 身份类型<br/>（站内username 邮箱email 手机mobile 或者第三方的qq weibo weixin等等）
     */
    private String identityType;

    /**
     * 登录标识 身份唯一标识<br/>（存储唯一标识，比如账号、邮箱、手机号、第三方获取的唯一标识等）
     */
    private String identifier;

    /**
     * 密码凭证 授权凭证（比如密码 第三方登录的token等）
     */
    private String credential;

    /**
     * 账号状态，0：未激活；1：已激活；2：停用；3：……
     */
    private int status;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者，存储的是创建人的id（t_loginuser)
     */
    @JSONField(serialize=false)
    private String creator;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize=false)
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    @JSONField(serialize=false)
    private String modifier;


}
