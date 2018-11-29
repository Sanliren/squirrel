package cn.thirdpart.squirrel.user.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_role")
/**
 * //角色表（与用户信息表是多对多关系）
 */
public class Role {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 角色名称
     */
    @Column(name = "roleName", unique = false, nullable = false, length = 20)
    private String roleName;

    /**
     * 角色父级ID
     */
    @Column(name = "roleParentId", unique = false, nullable = true, length = 64)
    private String roleParentId;

    /**
     * 角色描述
     */
    @Column(name = "roleDesc", unique = false, nullable = true, length = 100)
    private String roleDesc;


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
     * 角色对应的组信息
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles", fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<Group> groups;

    /**
     * 角色对应的用户信息
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles", fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<Userinfo> userinfos;

    /**
     * 角色的权限
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinTable(name="t_role_permission_r",joinColumns={@JoinColumn(name="roleId")}
            ,inverseJoinColumns={@JoinColumn(name="permissionId")})
    private List<Permission> permissions;

}
