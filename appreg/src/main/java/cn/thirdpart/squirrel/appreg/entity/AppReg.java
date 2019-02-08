package cn.thirdpart.squirrel.appreg.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_app")
/**
 * //申请的应用表
 */
public class AppReg {

    /**
     * app_key
     */
    @Id
    @Column(name = "app_key", unique = true, nullable = false, length = 128)
    private String appKey;

    /**
     * app_secret
     */
    @Column(name = "app_secret", unique = false, nullable = true, length = 256)
    private String appSecret;

    /**
     * app_name 应用图标
     */
    @Column(name = "app_name", unique = false, nullable = true, length = 100)
    private String appName;

    /**
     * app_icon
     */
    @Column(name = "app_icon", unique = false, nullable = true, length = 255)
    private String appIcon;

    /**
     * app_desc 应用描述
     */
    @Column(name = "app_desc", unique = false, nullable = true, length = 255)
    private String appDesc;

    /**
     * 哪个用户的申请的应用
     */
    @Column(name = "loginuser_id", unique = false, nullable = true, length = 64)
    private String loginuserId;

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


    public AppReg(){}

    public AppReg(String appKey, String appSecret, String appName, String appIcon, String appDesc, String loginuserId, Date createTime, String creator, Date modifyTime, String modifier) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.appName = appName;
        this.appIcon = appIcon;
        this.appDesc = appDesc;
        this.loginuserId = loginuserId;
        this.createTime = createTime;
        this.creator = creator;
        this.modifyTime = modifyTime;
        this.modifier = modifier;
    }

    public AppReg(String appName, String appIcon, String appDesc, String loginuserId) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.appDesc = appDesc;
        this.loginuserId = loginuserId;
    }
}
