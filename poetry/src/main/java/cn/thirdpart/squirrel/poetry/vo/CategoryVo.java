package cn.thirdpart.squirrel.poetry.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
/**
 * 诗词歌赋分类表
 */
public class CategoryVo {

    /**
     * 诗词表主键, Long类型，自动增长
     */
    private Long id;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目描述
     */
    private String description;

    /**
     * 类目类型，可以给很多不同种类的进行类目分类
     */
    private String categoryType;


}
