package cn.thirdpart.squirrel.poetry.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_poetry")
/**
 * 诗词歌赋表
 */
public class Poetry {

    /**
     * 诗词表主键, Long类型，自动增长
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 诗词题目
     */
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    /**
     * 诗歌分类 主要有：唐诗/宋词/元曲/诗经/论语
     */
    @JoinColumn(name = "category_id")
    @OneToOne
    private Category category;

    /**
     * 诗词作者
     */
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    /**
     * 作者名称
     */
    @Column(name = "author_name", length = 200, nullable = false)
    private String authorName;

    /**
     * 诗词内容
     */
    @Column(name = "content", length = 5000)
    private String content;

    /**
     * 诗词内容
     */
    @Column(name = "note", length = 3000)
    private String note;

    /**
     * 诗词的平仄关系
     */
    @Column(name = "strains", length = 5000)
    private String strains;

    /**
     * 诗词的配图URL
     */
    @Column(name = "img", length = 250)
    private String img;

    /**
     * 该诗歌是否经过修订审核<br/>
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
