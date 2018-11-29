package cn.thirdpart.squirrel.user.vo;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/**
 * //用户信息表（与用户登录表是一对多关系）
 */
@ApiModel(value="UserinfoVo对象",description="用户对象UserinfoVo")
public class UserinfoVo {

    /**
     * id
     */
    @ApiModelProperty(value="id",hidden=true)
    private String id;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称",name="nickname",required=true)
    private String nickname;

    /**
     * 头像URL
     */
    @ApiModelProperty(value="头像URL地址",name="avatar",required=false)
    private String avatar;

    /**
     * 生日
     */
    @ApiModelProperty(value="生日",name="birthday",required=false, example = "1970-01-01")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 星座
     */
    @ApiModelProperty(value="星座",name="xingzuo",required=false, example = "射手座")
    private String xingzuo;

    /**
     * 个性签名
     */
    @ApiModelProperty(value="个性签名",name="gxqm",required=false, example = "你是我毕生的梦想")
    private String gxqm;

    /**
     * 组织机构表id
     */
//    @Column(name = "orgid", unique = false, nullable = true, length = 64)
//    private String orgid;
    @ApiModelProperty(value="org",hidden=true)
    private OrgVo org;

    /**
     * 用户的组
     */
    @ApiModelProperty(value="groups",hidden=true)
    private List<GroupVo> groups;


    /**
     * 用户的角色
     */
    @ApiModelProperty(value="roles",hidden=true)
    private List<RoleVo> roles;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="createTime",hidden=true)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者，存储的是创建人的id（t_loginuser)
     */
    @ApiModelProperty(value="creator",hidden=true)
    @JSONField(serialize=false)
    private String creator;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="modifyTime",hidden=true)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize=false)
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    @ApiModelProperty(value="modifier",hidden=true)
    @JSONField(serialize=false)
    private String modifier;

}
