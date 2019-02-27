package cn.thirdpart.squirrel.appreg.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_interface")
/**
 * //申请的应用表
 */
public class Interface {

    /**
     * id,使用的是long类型
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 接口名称
     */
    @Column(name = "name", unique = false, nullable = false, length = 20)
    private String name;

    /**
     * 接口地址
     */
    @Column(name = "address", unique = false, nullable = false, length = 255)
    private String address;

    /**
     * 接口图标
     */
    @Column(name = "icon", unique = false, nullable = false, length = 255)
    private String icon;

    /**
     * 接口返回格式
     */
    @Column(name = "format", unique = false, nullable = false, length = 20)
    private String format;

    /**
     * 接口请求方法
     */
    @Column(name = "method", unique = false, nullable = false, length = 20)
    private String method;

    /**
     * 接口请求事例地址
     */
    @Column(name = "address_example", unique = false, nullable = false, length = 250)
    private String addressExample;

    /**
     * 接口参数说明; 传递的必须是json格式
     */
    @Column(name = "params_explain", unique = false, nullable = false, length = 250)
    private String paramsExplain;

    /**
     * 接口返回案例; 必须是json格式
     */
    @Column(name = "result_explain", unique = false, nullable = false, length = 250)
    private String resultExplain;


    /**
     * 接口描述
     */
    @Column(name = "description", unique = false, nullable = true, length = 400)
    private String description;

    /**
     * 接口费用
     */
    @Column(name = "cost", unique = false, nullable = false)
    private int cost;

    /**
     * 接口可以使用次数
     */
    @Column(name = "amount", unique = false, nullable = false)
    private int amount;

    /**
     * 接口状态
     */
    @Column(name = "status", unique = false, nullable = false)
    private int status;


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


}
