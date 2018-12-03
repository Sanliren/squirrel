package cn.thirdpart.squirrel.lovewords.vo;

import cn.thirdpart.squirrel.lovewords.entity.Category;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data

/**
 * 笑话表
 */
public class JokeVo {
    /**
     * 笑话表主键, Long类型，自动增长
     */
    private Long id;

    /**
     * 笑话内容
     */
    private String[] content;


    /**
     * 笑话配图
     */
    private String imgurl;

    /**
     * 笑话分类 主要有：情话/毒鸡汤/……
     */
    private String categoryName;

    private long categoryId;


    /**
     * 该笑话是否经过修订审核<br/>
     * 0:未审核；1：初步修订；2：二次修订
     */
//    private int verify = 0;
    private int verify;



}
