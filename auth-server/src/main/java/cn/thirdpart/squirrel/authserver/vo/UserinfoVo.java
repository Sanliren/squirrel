package cn.thirdpart.squirrel.authserver.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/**
 * //用户信息表（与用户登录表是一对多关系）
 */
public class UserinfoVo {

    /**
     * id
     */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 星座
     */
    private String xingzuo;

    /**
     * 个性签名
     */
    private String gxqm;

    /**
     * 组织机构表id
     */
//    @Column(name = "orgid", unique = false, nullable = true, length = 64)
//    private String orgid;
    private OrgVo org;

    /**
     * 用户的组
     */
    private List<GroupVo> groups;


    /**
     * 用户的角色
     */
    private List<RoleVo> roles;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者，存储的是创建人的id（t_loginuser)
     */
    @JSONField(serialize=false)
    private String creator;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize=false)
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    @JSONField(serialize=false)
    private String modifier;

}
