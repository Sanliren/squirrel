package cn.thirdpart.squirrel.poetry.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_category")
/**
 * 分类表
 */
public class Category {

    /**
     * 分类表主键, Long类型，自动增长
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 类目类型，可以给很多不同种类的进行类目分类
     */
    @Column(name = "category_type", length = 20)
    private String categoryType;

    /**
     * 类目名称
     */
    @Column(name = "category_name", nullable = false, length = 20)
    private String categoryName;

    /**
     * 类目描述
     */
    @Column(name = "description", length = 200)
    private String description;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createTime", unique = false, nullable = true)
    private Date createTime;

    /**
     * 创建者，
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
     * 修改者，
     */
    @Column(name = "modifier", unique = false, nullable = true, length = 64)
    private String modifier;

}
