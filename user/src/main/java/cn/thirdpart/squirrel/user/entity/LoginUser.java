package cn.thirdpart.squirrel.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_loginuser")
/**
 * //用户登录表
 */
public class LoginUser {

    /**
     * id
     */
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 用户信息表id
     */
//    @Column(name = "userinfo_id", unique = false, nullable = true, length = 64)
//    private String userinfoId;
    @JoinColumn(name="userinfo_id")
    @ManyToOne(targetEntity=Userinfo.class, fetch=FetchType.LAZY, cascade={CascadeType.ALL}, optional = true)
    private Userinfo userinfo;



    /**
     * 登录类型 身份类型<br/>（站内username 邮箱email 手机mobile 或者第三方的qq weibo weixin等等）
     */
    @Column(name = "identity_type", unique = true, nullable = false, length = 15)
    private String identityType;

    /**
     * 登录标识 身份唯一标识相当与username<br/>（存储唯一标识，比如账号、邮箱、手机号、第三方获取的唯一标识等）
     */
    @Column(name = "identifier", unique = true, nullable = false, length = 150)
    private String identifier;

    /**
     * 密码凭证 授权凭证（比如密码 第三方登录的token等）
     */
    @Column(name = "credential", unique = true, nullable = false, length = 200)
    private String credential;

    /**
     * 账号状态，0：未激活；1：已激活,未绑定用户信息；2：停用；3：……
     */
    @Column(name = "status", nullable = false)
    private int status;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createTime", unique = false, nullable = true)
    private Date createTime;

    /**
     * 创建者，存储的是创建人的id（t_loginuser)
     */
    @Column(name = "creator", unique = false, nullable = true, length = 64)
    private String creator;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modifyTime", unique = false, nullable = true)
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    @Column(name = "modifier", unique = false, nullable = true, length = 64)
    private String modifier;


}
