package cn.thirdpart.squirrel.poetry.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_author")
/**
 * 诗词歌赋作者表
 */
public class Author {

    /**
     * 诗词表主键, Long类型，自动增长
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 作者名称
     */
    @Column(name = "author_name", length = 20, nullable = false)
    private String authorName;


    /**
     * 哪个朝代
     */
    @Column(name = "dynasty", length = 20, nullable = false)
    private String dynasty;

    /**
     * 作者配图
     */
    @Column(name = "author_img", length = 250)
    private String authorImg;

    /**
     * 作者生平短简介
     */
    @Column(name = "short_description", length = 1000)
    private String short_description;

    /**
     * 作者生平长简介
     */
    @Column(name = "description", length = 2000)
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
