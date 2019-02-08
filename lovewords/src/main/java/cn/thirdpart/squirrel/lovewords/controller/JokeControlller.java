package cn.thirdpart.squirrel.lovewords.controller;

import cn.thirdpart.squirrel.lovewords.config.redis.RedisUtil;
import cn.thirdpart.squirrel.lovewords.entity.Joke;
import cn.thirdpart.squirrel.lovewords.service.JokeService;
import cn.thirdpart.squirrel.lovewords.vo.JokeVo;
import cn.thirdpart.squirrel.lovewords.vo.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@RequestMapping(value = "/lovewords")
@Api(value = "JokeControlller相关的api", tags = "笑话操作接口")
public class JokeControlller {

    @Autowired
    JokeService jokeService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "getRandomJoke", notes = "根据类型随机返回一条笑话")
    @RequestMapping(value = "/joke", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getRandomJoke(){
        //获取笑话总共数目，先看缓存中有没有theCountOfJoke
        ResponseResult result = null;
        String key = "theCountOfJoke";
        int count = 0;
        boolean haskey = redisUtil.hasKey(key);
        if(haskey){
            //缓存中存在这个笑话总数目
            count = (int)redisUtil.get(key);
        } else {
            //缓存中不存在就查找数据库并set进缓存中
            count = jokeService.getJokeCount();
            redisUtil.set(key, count);
        }
        //通过总数生成一个1~cont的随机数
        int random = 1 + (int)(Math.random()*67);
        Long id = (long)random;
        Joke joke = jokeService.getJokeById(id);
        if(joke != null){

            JokeVo jokeVo = new JokeVo();
            BeanUtils.copyProperties(joke, jokeVo);
            jokeVo.setCategoryId(joke.getCategory().getId());
            jokeVo.setContent(JSON.parseObject(joke.getContent(), new TypeReference<String[]>(){}));
            result = new ResponseResult(1, "成功获取", jokeVo);

//            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
//                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        } else {
            result = new ResponseResult(0, "获取笑话失败", null);
//            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
//                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }

        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);


    }


    }
