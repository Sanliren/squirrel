package cn.thirdpart.squirrel.appreg.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_app_interface_r")
/**
 * 应用与接口的关系表
 *
 */
public class AppInterfaceRelation {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator( name = "hibernate-uuid",strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    private String id;

    /**
     * app应用表中的id
     */
    @Column(name = "app_id", unique = true, nullable = false, length = 64)
    private String appId;

    /**
     * 接口表中id long类型
     */
    @Column(name = "interface_id", unique = true, nullable = false)
    private Long interfaceId;


    /**
     * 应用与接口的状态，可以访问，禁止访问，……
     */
    @Column(name = "status", unique = true, nullable = false)
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
