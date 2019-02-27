package cn.thirdpart.squirrel.appreg.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data

/**
 * 应用与接口的关系表
 *
 */
public class AppInterfaceRelationVo {

    /**
     * id
     */
    private String id;

    /**
     * app应用表中的id
     */
    private String appId;

    /**
     * 接口表中id long类型
     */
    private Long interfaceId;


    /**
     * 应用与接口的状态，可以访问，禁止访问，……
     */
    private int status;


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

    public AppInterfaceRelationVo(String id, String appId, Long interfaceId, int status, Date createTime, String creator, Date modifyTime, String modifier) {
        this.id = id;
        this.appId = appId;
        this.interfaceId = interfaceId;
        this.status = status;
        this.createTime = createTime;
        this.creator = creator;
        this.modifyTime = modifyTime;
        this.modifier = modifier;
    }

    public AppInterfaceRelationVo() { }
}
