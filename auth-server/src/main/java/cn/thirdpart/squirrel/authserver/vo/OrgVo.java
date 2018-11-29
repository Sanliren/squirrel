package cn.thirdpart.squirrel.authserver.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
/**
 * 用户组织机构表（与用户登录表是多对一的关系）
 */
public class OrgVo {

    /**
     * id
     */
    private String id;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 父级组织机构id
     */
    private String orgParentId;

    /**
     * 组织机构描述
     */
    private String orgDesc;

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

}
