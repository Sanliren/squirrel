package cn.thirdpart.squirrel.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_org")
/**
 * 用户组织机构表（与用户登录表是多对一的关系）
 */
public class Org {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * 组织机构名称
     */
    @Column(name = "orgName", unique = true, nullable = false, length = 20)
    private String orgName;

    /**
     * 父级组织机构id
     */
    @Column(name = "orgParentId", unique = true, nullable = false, length = 64)
    private String orgParentId;

    /**
     * 组织机构描述
     */
    @Column(name = "orgDesc", unique = true, nullable = false, length = 100)
    private String orgDesc;

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
