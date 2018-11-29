package cn.thirdpart.squirrel.authserver.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/**
 * 权限表
 *
 */
public class PermissionVo {
    /**
     * id
     */
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限名称
     */
    private String parentId;

    /**
     * 权限描述
     */
    private String desc;


    /**
     * 权限对应的角色
     */
    @JSONField(serialize=false)
    private List<RoleVo> roles;

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
