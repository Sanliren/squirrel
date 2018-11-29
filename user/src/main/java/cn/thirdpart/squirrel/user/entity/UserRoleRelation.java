package cn.thirdpart.squirrel.user.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_userinfo_role_r")
/**
 * 用户与角色的关系表
 *
 */
public class UserRoleRelation {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 角色表中的id
     */
    @Column(name = "roleId", unique = true, nullable = false, length = 64)
    private String roleId;

    /**
     * 用户信息表中id
     */
    @Column(name = "userinfoId", unique = true, nullable = false, length = 64)
    private String userinfoId;

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
