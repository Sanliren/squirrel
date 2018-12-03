package cn.thirdpart.squirrel.poetry.controller;

import cn.thirdpart.squirrel.poetry.entity.Poetry;
import cn.thirdpart.squirrel.poetry.service.PoetryService;
import cn.thirdpart.squirrel.poetry.vo.PoetryVo;
import cn.thirdpart.squirrel.poetry.vo.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 诗词接口
 */
@RestController
@Api(value = "PoetryController相关的api", tags = "诗歌操作接口")
public class PoetryController {

    @Autowired
    PoetryService poetryService;


    @ApiOperation(value = "getPoetry", notes = "获取诗歌信息")
    @RequestMapping(value = "/poetry", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getPoetry(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "authorName", required = false) String authorName,
                            @RequestParam(value = "page", required = true, defaultValue = "1") int page,
                            @RequestParam(value = "pageSize", required = true, defaultValue = "1") int pageSize){

        ResponseResult result = null;

//        if(StringUtils.isBlank(title) && StringUtils.isBlank(authorName)){
//            result = new ResponseResult(0, "title和authorName不能全部为空", null);
//            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
//                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
//
//        }

        if(pageSize > 8 || pageSize < 1){
            result = new ResponseResult(0, "pageSize必须是[1~8]之间的一个数字", null);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }

        List<Poetry> poetryList = poetryService.getPoety(title, authorName, page, pageSize);
        List<PoetryVo> poetryVoList = null;
        if(poetryList != null || poetryList.size() > 0){
            poetryVoList = new ArrayList<>();
            for(Poetry poetry : poetryList){
                PoetryVo poetryVo = new PoetryVo();
                System.out.println("content :" + poetry.getContent() );
                BeanUtils.copyProperties(poetry, poetryVo);
                poetryVo.setAuthor(poetry.getAuthorName());
                poetryVo.setCategory(poetry.getCategory().getCategoryName());

                String[] contents = JSON.parseObject(poetry.getContent(), new TypeReference<String[]>(){});
                poetryVo.setContent(contents);
                String[] strains = JSON.parseObject(poetry.getStrains(), new TypeReference<String[]>(){});
                poetryVo.setStrains(strains);

                poetryVoList.add(poetryVo);
            }

            result = new ResponseResult(1, "成功获取诗歌", poetryVoList);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        } else {
            result = new ResponseResult(1, "没有对应的诗歌", poetryVoList);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }

    }

}
