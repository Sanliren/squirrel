package cn.thirdpart.squirrel.appreg.controller;


import cn.thirdpart.squirrel.appreg.AppregApplication;
import cn.thirdpart.squirrel.appreg.authfeignclient.AuthFeignClient;
import cn.thirdpart.squirrel.appreg.config.redis.RedisUtil;
import cn.thirdpart.squirrel.appreg.entity.AppReg;
import cn.thirdpart.squirrel.appreg.service.AppRegService;
import cn.thirdpart.squirrel.appreg.util.CommonUtil;
import cn.thirdpart.squirrel.appreg.vo.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@Api(value = "AppRegController相关的api", tags = "用户接入应用接口")
public class AppRegController {

    @Autowired
    AppRegService appRegService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "app", notes = "app申请接入接口")
    @RequestMapping(value = "/app", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String appRegister(@RequestParam(value = "appName", required = true) String appName,
                              @RequestParam(value = "appIcon", required = false) String appIcon,
                              @RequestParam(value = "appDesc", required = false) String appDesc,
                              @RequestParam(value = "jwt_claims", required = true) String jwt_claims) {
        ResponseResult result = null;
//        //通过jwt_claims获取当前用户id，
        JSONObject jsonObject = JSON.parseObject(jwt_claims);
        String userid = jsonObject.getString("currentUserId");

        AppReg appreg = new AppReg(appName, appIcon, appDesc, userid);
        appreg.setAppKey(CommonUtil.uuid());
        appreg.setAppSecret(CommonUtil.uuid());
        appreg.setCreateTime(new Date());
        appreg.setModifier(userid);
        appreg.setModifyTime(new Date());
        appreg.setCreator(userid);

        AppReg appreg_r = appRegService.appReg(appreg);

        if (appreg_r != null) {
            result = new ResponseResult(1, "应用申请接入成功", appreg_r);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        } else {
            result = new ResponseResult(0, "应用申请接入失败", appreg_r);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }


    }

    @ApiOperation(value = "getAppByAppkey", notes = "根据appkey判断是否存在应用")
    @RequestMapping(value = "/app", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String appRegister(@RequestParam(value = "appKey", required = true) String appKey) {

        ResponseResult result = null;
        AppReg appReg = appRegService.getAppByAppkey(appKey);
        if(appReg != null){
            result = new ResponseResult(1, "查找应用成功", appReg);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        } else {
            result = new ResponseResult(0, "查找应用失败", appReg);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }

    }


    @ApiOperation(value = "getAppToken", notes = "根据appkey获取应用的access_token")
    @RequestMapping(value = "/token", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String getAppToken(@RequestParam(value = "appKey", required = true) String appKey) {

        ResponseResult result = null;
        String jwt_app_token = appRegService.getAppToken(appKey);
        if(jwt_app_token != null){

            JSONObject jsonObject = JSON.parseObject(jwt_app_token);
            result = new ResponseResult(1, "成功获取app的token", jsonObject);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        } else {
            result = new ResponseResult(0, "appKey : "+appKey+"不存在", null);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }

    }




    }
