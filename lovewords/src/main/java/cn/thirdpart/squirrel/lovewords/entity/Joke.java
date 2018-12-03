package cn.thirdpart.squirrel.lovewords.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_joke")
/**
 * 笑话表
 */
public class Joke {
    /**
     * 笑话表主键, Long类型，自动增长
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 笑话内容
     */
    @Column(name = "content", length = 500, nullable = false)
    private String content;


    /**
     * 笑话配图
     */
    @Column(name = "imgurl", length = 250, nullable = false)
    private String imgurl;

    /**
     * 笑话分类 主要有：情话/毒鸡汤/……
     */
    @JoinColumn(name = "category_id")
    @OneToOne
    private Category category;

    @Transient
    private String categoryName;


    /**
     * 该笑话是否经过修订审核<br/>
     * 0:未审核；1：初步修订；2：二次修订
     */
    @Column(name = "verify", nullable = false)
    private int verify = 0;


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
