package cn.thirdpart.squirrel.appreg.controller;


import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import cn.thirdpart.squirrel.appreg.entity.Interface;
import cn.thirdpart.squirrel.appreg.service.InterfaceService;
import cn.thirdpart.squirrel.appreg.util.CommonUtil;
import cn.thirdpart.squirrel.appreg.vo.AppInterfaceRelationVo;
import cn.thirdpart.squirrel.appreg.vo.InterfaceVo;
import cn.thirdpart.squirrel.appreg.vo.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Api(value = "InterfaceController相关的api", tags = "接入应接口")
public class InterfaceController {

    @Autowired
    InterfaceService interfaceService;

    @ApiOperation(value = "interface", notes = "添加接口")
    @RequestMapping(value = "/interface", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String interfaceReg(@RequestBody @ApiParam(name="interface对象",value="传入json string 格式",required=true)InterfaceVo interfaceVo,
                              @RequestParam(value = "jwt_claims", required = true) String jwt_claims){

        ResponseResult result = null;
//        //通过jwt_claims获取当前用户id，
        JSONObject jsonObject = JSON.parseObject(jwt_claims);
        String currentUserId = jsonObject.getString("currentUserId");

        log.info("传递的interface对象是："+interfaceVo.toString());

        if(interfaceVo != null){
            interfaceVo.setCreator(currentUserId);
            interfaceVo.setModifier(currentUserId);
            //以下校验params_explain和result_explain是否是json格式
            if(StringUtils.isNotBlank(interfaceVo.getParamsExplain())){
                //如果不是json字符串
                if( ! isJsonStr(interfaceVo.getParamsExplain())){
                    result = new ResponseResult(0, "参数：params_explain不是Json格式", null);
                    return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                            SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                }
            }
            if(StringUtils.isNotBlank(interfaceVo.getResultExplain())){
                //如果不是json字符串
                if( ! isJsonStr(interfaceVo.getResultExplain())){
                    result = new ResponseResult(0, "参数：result_explain不是Json格式", null);
                    return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                            SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                }
            }

            boolean flag = interfaceService.interfaceReg(interfaceVo);
            if(flag){
                //插入成功
                result = new ResponseResult(1, "成功插入接口数据", null);

            } else {
                result = new ResponseResult(0, "插入接口数据失败！！", null);
            }

            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);


        }

        return null;
    }


    /**
     * 根据传递应用id获取对应应用已经申请了的接口
     * @param appkey
     * @return
     */
    @ApiOperation(value = "getAppInterface", notes = "根据传递应用id获取对应应用已经申请了的接口")
    @RequestMapping(value = "/interface", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getAppInterface(@RequestParam(value = "appkey", required = true) String appkey) {
        ResponseResult result = null;
        List<Interface> interfaceList = interfaceService.findAppInterface(appkey);
        result = new ResponseResult(1, "成功获取应用已经申请了的接口数据！！", interfaceList);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


    /**
     * 根据传递应用id获取对应应用已经申请了的接口
     * @return
     */
    @ApiOperation(value = "getInterfaces", notes = "获取所有接口")
    @RequestMapping(value = "/interfaces", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getInterfaces() {
        ResponseResult result = null;
        List<Interface> interfaceList = interfaceService.findInterfaces();
        result = new ResponseResult(1, "成功获取所有接口数据！！", interfaceList);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


    @ApiOperation(value = "air", notes = "查看应用和接口的记录，从而拿到openid")
    @RequestMapping(value = "/air", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String findAir(@RequestParam(value="app_id", required = true) String app_id,
                              @RequestParam(value="interface_id", required = true) long interface_id,
                              @RequestParam(value = "jwt_claims", required = true) String jwt_claims) {
        ResponseResult result = null;
//        //通过jwt_claims获取当前用户id，
        JSONObject jsonObject = JSON.parseObject(jwt_claims);
        String currentUserId = jsonObject.getString("currentUserId");

        AppInterfaceRelation relations = interfaceService.findRelation(app_id, interface_id);

        result = new ResponseResult(1, "成功获取应用和接口的记录！！其中id就是openid", relations);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


        @ApiOperation(value = "air", notes = "应用申请开通某个接口")
    @RequestMapping(value = "/air", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String relationReg(@RequestParam(value="app_id", required = true) String app_id,
                              @RequestParam(value="interface_id", required = true) long interface_id,
                               @RequestParam(value = "jwt_claims", required = true) String jwt_claims) {

        ResponseResult result = null;
//        //通过jwt_claims获取当前用户id，
        JSONObject jsonObject = JSON.parseObject(jwt_claims);
        String currentUserId = jsonObject.getString("currentUserId");

        AppInterfaceRelationVo relationVo = new AppInterfaceRelationVo(CommonUtil.uuid(), app_id, interface_id, 1, new Date(), currentUserId, new Date(), currentUserId);

        boolean res = interfaceService.relationReg(relationVo);

        if(res){
            result = new ResponseResult(1, "应用成功申请接口！！", null);
        } else {
            result = new ResponseResult(0, "应用申请接口失败！！", null);
        }

        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


        /**
         * 判断传递的字符串是否是json格式
         * @param str
         * @return
         */
    private boolean isJsonStr(String str){
        try {
            JSONObject jsonObject = JSONObject.parseObject(str);
            return true;
        } catch (Exception e){
            log.info(str+", 不是json格式字符串");
            return false;
        }
    }


}
