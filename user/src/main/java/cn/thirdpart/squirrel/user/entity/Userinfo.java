package cn.thirdpart.squirrel.user.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_userinfo")
/**
 * //用户信息表（与用户登录表是一对多关系）
 */
public class Userinfo {

    /**
     * id
     */
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 昵称
     */
    @Column(name = "nickname", unique = false, nullable = true, length = 15)
    private String nickname;

    /**
     * 头像URL
     */
    @Column(name = "avatar", unique = false, nullable = true, length = 250)
    private String avatar;

    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd")
    @Column(name = "birthday", unique = false, nullable = true)
    private Date birthday;

    /**
     * 星座
     */
    @Column(name = "xingzuo", unique = false, nullable = true, length = 8)
    private String xingzuo;

    /**
     * 个性签名
     */
    @Column(name = "gxqm", unique = false, nullable = true, length = 200)
    private String gxqm;

    /**
     * 组织机构表id
     */
//    @Column(name = "orgid", unique = false, nullable = true, length = 64)
//    private String orgid;
    @OneToOne//由于不需要对两者进行级联操作所以不用配置cascadeType操作
    @JoinColumn(name = "orgid")
    private Org org;

    /**
     * 用户的组
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="t_userinfo_group_r",joinColumns={@JoinColumn(name="userinfoId")}
            ,inverseJoinColumns={@JoinColumn(name="groupId")})
    private List<Group> groups;


    /**
     * 用户的角色
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="t_userinfo_role_r",joinColumns={@JoinColumn(name="userinfoId")}
            ,inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<Role> roles;

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
