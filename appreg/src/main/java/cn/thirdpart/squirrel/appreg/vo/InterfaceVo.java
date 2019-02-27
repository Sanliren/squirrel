package cn.thirdpart.squirrel.appreg.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data

/**
 * //申请的应用表
 */
@ApiModel(value="InterfaceVo对象",description="接口对象")
public class InterfaceVo {

    /**
     * id,使用的是long类型
     */
    @ApiModelProperty(value="id",hidden=true)
    private Long id;

    /**
     * 接口名称
     */
    @ApiModelProperty(value="接口名称",name="name", required=true, example = "情话接口")
    private String name;

    /**
     * 接口地址
     */
    @ApiModelProperty(value="接口地址",name="address", required=true, example = "http://localhost:666/lovewords/joke")
    private String address;

    /**
     * 接口图标
     */
    @ApiModelProperty(value="接口图标",name="icon", required=false, example = "http://localhost:666/120.gif")
    private String icon;

    /**
     * 接口返回格式
     */
    @ApiModelProperty(value="接口返回格式",name="format", required=true, example = "json")
    private String format;

    /**
     * 接口请求方法
     */
    @ApiModelProperty(value="接口请求方法",name="method", required=true, example = "GET")
    private String method;

    /**
     * 接口请求事例地址
     */
    @ApiModelProperty(value="接口请求事例地址",name="addressExample", required=false, example = "http://localhost:666/lovewords/joke")
    private String addressExample;

    /**
     * 接口参数说明; 传递的必须是json格式
     */
    @ApiModelProperty(value="接口参数说明",name="paramsExplain", required=false, example = "[{\"name\":\"page\",\"isNeed\":\"是\",\"type\":\"int\",\"explain\":\"这个参数必须进行传递，是做分页使用的\"},{\"name\":\"pageSize\",\"isNeed\":\"是\",\"type\":\"int\",\"explain\":\"这个参数必须的，是做分页使用的\"}]")
    private String paramsExplain;

    /**
     * 接口返回案例; 必须是json格式
     */
    @ApiModelProperty(value="接口返回案例",name="resultExplain", required=false, example = "[{\"name\":\"page\",\"isNeed\":\"是\",\"type\":\"int\",\"explain\":\"这个参数必须进行传递，是做分页使用的\"},{\"name\":\"pageSize\",\"isNeed\":\"是\",\"type\":\"int\",\"explain\":\"这个参数必须的，是做分页使用的\"}]")
    private String resultExplain;


    /**
     * 接口描述
     */
    @ApiModelProperty(value="接口描述",name="description", required=false, example = "这个是情话接口的介绍说明详情")
    private String description;

    /**
     * 接口费用
     */
    @ApiModelProperty(value="接口费用",name="cost", required=true, example = "0")
    private int cost;

    /**
     * 接口可以使用次数
     */
    @ApiModelProperty(value="接口可以使用次数",name="amount", required=true, example = "500")
    private int amount;


    /**
     * 接口状态
     */
    @ApiModelProperty(value="接口可以使用次数",name="status", required=false, example = "500", hidden = true)
    private int status;


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
    private String creator;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="modifyTime",hidden=true)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 修改者，存储的是修改人的id（t_loginuser)
     */
    @ApiModelProperty(value="modifier",hidden=true)
    private String modifier;

    public InterfaceVo(Long id, String name, String address, String icon, String format, String method, String addressExample, String paramsExplain, String resultExplain, String description, int cost, int amount, Date createTime, String creator, Date modifyTime, String modifier) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.icon = icon;
        this.format = format;
        this.method = method;
        this.addressExample = addressExample;
        this.paramsExplain = paramsExplain;
        this.resultExplain = resultExplain;
        this.description = description;
        this.cost = cost;
        this.amount = amount;
        this.createTime = createTime;
        this.creator = creator;
        this.modifyTime = modifyTime;
        this.modifier = modifier;
    }

    public InterfaceVo(){}

    @Override
    public String toString() {
        return "InterfaceVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", icon='" + icon + '\'' +
                ", format='" + format + '\'' +
                ", method='" + method + '\'' +
                ", addressExample='" + addressExample + '\'' +
                ", paramsExplain='" + paramsExplain + '\'' +
                ", resultExplain='" + resultExplain + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifier='" + modifier + '\'' +
                '}';
    }
}
