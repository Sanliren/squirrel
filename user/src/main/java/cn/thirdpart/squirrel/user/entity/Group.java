package cn.thirdpart.squirrel.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_group")
/**
 * 用户组织机构表（与用户登录表是多对一的关系）
 */
public class Group {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 组名
     */
    @Column(name = "groupName", unique = false, nullable = false, length = 64)
    private String groupName;

    /**
     * 父级组id
     */
    @Column(name = "groupParentId", unique = false, nullable = false, length = 64)
    private String groupParentId;

    /**
     * 组分类<br/>1: 用户组；2角色组；3：权限组
     */
    @Column(name = "groupClass", unique = false, nullable = false)
    private int groupClass;

    /**
     * 组描述
     */
    @Column(name = "groupDesc", unique = false, nullable = false, length = 100)
    private String groupDesc;

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


    /**
     * 群组对应的用户信息
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "groups", fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<Userinfo> userinfos;

    /**
     *  组的角色
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinTable(name="t_role_group_r",joinColumns={@JoinColumn(name="groupId")}
            ,inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<Role> roles;

}
