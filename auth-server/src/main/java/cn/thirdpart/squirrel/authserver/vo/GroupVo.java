package cn.thirdpart.squirrel.authserver.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
/**
 * 组
 */
public class GroupVo implements Serializable {

    /**
     * id
     */

    private String id;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 父级组id
     */
    private String groupParentId;

    /**
     * 组分类<br/>1: 用户组；2角色组；3：权限组
     */
    private int groupClass;

    /**
     * 组描述
     */
    private String groupDesc;

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


    /**
     * 群组对应的用户信息
     */
    @JSONField(serialize = false)
    private List<UserinfoVo> userinfos;

    /**
     *  组的角色
     */
    @JSONField(serialize = false)
    private List<RoleVo> roles;

}
