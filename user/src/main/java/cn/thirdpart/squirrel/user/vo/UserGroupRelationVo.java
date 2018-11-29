package cn.thirdpart.squirrel.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
/**
 * 用户与组的关系表
 *
 */
public class UserGroupRelationVo {

    /**
     * id
     */
    private String id;

    /**
     * 用户信息表中的id
     */
    private String userinfoId;

    /**
     * 组表中id
     */
    private String groupId;


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
