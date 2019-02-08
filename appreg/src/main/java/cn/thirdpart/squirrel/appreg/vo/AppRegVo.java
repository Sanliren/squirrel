package cn.thirdpart.squirrel.appreg.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class AppRegVo {

    /**
     * app_key
     */
    private String appKey;

    /**
     * app_secret
     */
    private String appSecret;

    /**
     * app_name 应用图标
     */
    private String appName;

    /**
     * app_icon
     */
    private String appIcon;

    /**
     * app_desc 应用描述
     */
    private String appDesc;

    /**
     * 哪个用户的申请的应用
     */
    private String userinfoId;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者，存储的是创建人的id（t_loginuser)
     */
    private String creator;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    private String modifier;


    public AppRegVo(){}

    public AppRegVo(String appKey, String appSecret, String appName, String appIcon, String appDesc, String userinfoId, Date createTime, String creator, Date modifyTime, String modifier) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.appName = appName;
        this.appIcon = appIcon;
        this.appDesc = appDesc;
        this.userinfoId = userinfoId;
        this.createTime = createTime;
        this.creator = creator;
        this.modifyTime = modifyTime;
        this.modifier = modifier;
    }
}
