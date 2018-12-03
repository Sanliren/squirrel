package cn.thirdpart.squirrel.poetry.vo;

import lombok.Data;


@Data
/**
 * 诗词歌赋表
 */
public class PoetryVo {

    /**
     * 诗词表主键, Long类型，自动增长
     */
    private Long id;

    /**
     * 诗词题目
     */
    private String title;

    /**
     * 诗歌分类 主要有：唐诗/宋词/元曲/诗经/论语<br/>
     * 直接获取Category对象的name
     */
    private String category;

    /**
     * 诗词作者
     * 直接将Author的name赋值这里就好
     */

    private String author;

    /**
     * 诗词内容
     */
//    private String content;
    private String[] content;

    /**
     * 诗词内容
     */
    private String note;

    /**
     * 诗词的平仄关系
     */
//    private String strains;
    private  String[] strains;

    /**
     * 诗词的配图URL
     */
    private String img;

    /**
     * 该诗歌是否经过修订审核<br/>
     * 0:未审核；1：初步修订；2：二次修订
     */
    private int verify = 0;



}
