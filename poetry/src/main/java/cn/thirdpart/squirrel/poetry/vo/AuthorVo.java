package cn.thirdpart.squirrel.poetry.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
/**
 * 诗词歌赋作者表
 */
@ApiModel(value="AuthorVo对象",description="诗词歌赋作者表对象")
public class AuthorVo {

    /**
     * 诗词表主键, Long类型，自动增长
     */
    @ApiModelProperty(value="id",hidden=true)
    private Long id;

    /**
     * 作者名称
     */
    @ApiModelProperty(value="作者名称",name="authorName", required=true, example = "李白")
    private String authorName;

    /**
     * 哪个朝代
     */
    @ApiModelProperty(value="作者朝代",name="dynasty", required=false, example = "唐朝")
    private String dynasty;

    /**
     * 作者配图
     */
    @ApiModelProperty(value="作者配图",name="authorImg", required=false, example = "https://upload-images.jianshu.io/upload_images/937049-855bfb85ec733e6b.jpg?imageMogr2/auto-orient/")
    private String authorImg;

    /**
     * 作者生平短简介
     */
    @ApiModelProperty(value="作者生平短简介",name="short_description", required=false, example = "作者生平短简介（1000字内）")
    private String short_description;

    /**
     * 作者生平长简介
     */
    @ApiModelProperty(value="作者生平长简介",name="short_description", required=false, example = "作者生平长简介（2000字内）")
    private String description;



}
