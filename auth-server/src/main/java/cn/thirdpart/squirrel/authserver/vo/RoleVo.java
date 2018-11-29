package cn.thirdpart.squirrel.authserver.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/**
 * //角色表（与用户信息表是多对多关系）
 */
public class RoleVo {

    /**
     * id
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色父级ID
     */
    private String roleParentId;

    /**
     * 角色描述
     */
    private String roleDesc;


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
     * 角色对应的组信息
     */

    private List<GroupVo> groups;

    /**
     * 角色对应的用户信息
     */
    @JSONField(serialize = false)
    private List<UserinfoVo> userinfos;

    /**
     * 角色的权限
     */
    private List<PermissionVo> permissions;

}
