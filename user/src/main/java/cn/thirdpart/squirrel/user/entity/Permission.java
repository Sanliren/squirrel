package cn.thirdpart.squirrel.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_permission")
/**
 * 权限表
 *
 */
public class Permission {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 权限名称
     */
    @Column(name = "pName", unique = false, nullable = true, length = 20)
    private String pName;

    /**
     * 权限名称
     */
    @Column(name = "parentId", unique = false, nullable = true, length = 64)
    private String parentId;

    /**
     * 权限描述
     */
    @Column(name = "pDesc", unique = false, nullable = true, length = 100)
    private String pDesc;

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
     * 权限对应的角色
     */
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "permissions", fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<Role> roles;

}
