package cn.thirdpart.squirrel.user.incontroller;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import cn.thirdpart.squirrel.user.entity.Role;
import cn.thirdpart.squirrel.user.entity.Userinfo;
import cn.thirdpart.squirrel.user.exception.UserException;
import cn.thirdpart.squirrel.user.service.UserService;
import cn.thirdpart.squirrel.user.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "UserController相关的api", tags = "用户操作接口")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param identifier   用户名，账号、邮箱、手机号、第三方获取的唯一标识
     * @param credential   密码 第三方登录的token
     * @param identity_type 登录类型 站内username 邮箱email 手机mobile 或者第三方的qq weibo weixin
     * @return
     */
    @ApiOperation(value = "register", notes = "用户注册接口")
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String register(@RequestParam(value = "username", required = true) String identifier,
                           @RequestParam(value = "password", required = true) String credential,
                           @RequestParam(value = "loginType", required = true) String identity_type){

        ResponseResult responseResult = new ResponseResult();
        //应校验username和password的合法性
        if(credential.length()<6){
            responseResult.setCode(0);
            responseResult.setMsg("密码长度必须大于等于6");
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }

        if(!("username".equalsIgnoreCase(identity_type) || "QQ".equalsIgnoreCase(identity_type) || "email".equalsIgnoreCase(identity_type) || "weixin".equalsIgnoreCase(identity_type))){
            responseResult.setCode(0);
            responseResult.setMsg("注册账号的类型错误");
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }

        //再封装对象
        LoginUserVo formUser = new LoginUserVo();
        formUser.setIdentifier(identifier.trim());
        formUser.setCredential(credential.trim());
        formUser.setIdentityType(identity_type.trim());

        try {
            LoginUser dbUser = userService.register(formUser);
            formUser = new LoginUserVo();
            BeanUtils.copyProperties(dbUser, formUser, new String[]{"credential"});
            Map<String, Object> map = new HashMap<>();
            map.put("user", formUser);
            responseResult.setData(map);
        } catch (UserException e){
            responseResult.setCode(0);
            responseResult.setMsg("用户已注册");
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult.setData(map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }

        responseResult.setCode(1);
        responseResult.setMsg("注册成功");
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


    @ApiOperation(value = "login", notes = "用户登录接口")
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String login(@RequestParam(value = "username") String identifier,
                        @RequestParam(value = "password") String credential,
                        @RequestParam(value = "loginType") String identity_type){
        ResponseResult responseResult = null;
        //应校验username和password的合法性
        if(credential.length()<6){
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult = new ResponseResult(0, "密码长度必须大于等于6", map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }
        if(!("username".equalsIgnoreCase(identity_type) || "QQ".equalsIgnoreCase(identity_type) || "email".equalsIgnoreCase(identity_type) || "weixin".equalsIgnoreCase(identity_type))){
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult = new ResponseResult(0, "登录账号的类型错误", map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }
        UserLoginDTO userLoginDTO = null;
        String msg = null;
        try{
            userLoginDTO = userService.login(identifier, credential);

        } catch (UserException e){
            msg = e.getMessage();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", userLoginDTO);
        responseResult = new ResponseResult(1, "登录成功", map);
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
    }


    /**
     * 根据用户名或者昵称分页获取用户信息
     * @param page 第几页
     * @param pagesize 每页条数
     * @param username 用户名
     * @param nickname 昵称
     * @return
     */
    @ApiOperation(value = "getUsers", notes = "获取用户")
    @RequestMapping(value = "/users", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "2") Integer pagesize,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "nickname", required = false) String nickname){

        //构建排序条件
//        Sort sort = new Sort(Sort.Direction.ASC, "username");
        if (page <= 1)
            page = 0;
        else
            page = page - 1;
        //构造分页对象
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<LoginUser> userPage = userService.getUsers(pageable, username, nickname);
        Page<LoginUserVo> resultPage = null;//用于返回

        ResponseResult responseResult = new ResponseResult();
        if(userPage.getContent() != null){
            //修改content返回内容, 不能将loginuser的具体暴露出去
            List<LoginUserVo> contents = new ArrayList<>();
            LoginUserVo target = null;
            for (LoginUser sourse : userPage.getContent()) {
                target = new LoginUserVo();//这里必须每次新建一个对象，不然会出现5条数据重复
                BeanUtils.copyProperties(sourse, target, "credential");
                contents.add(target);
            }
            resultPage = new PageImpl<>(contents, userPage.getPageable(), userPage.getTotalElements());

            responseResult.setMsg("成功获取用户信息");
            responseResult.setCode(1);
        } else {
            responseResult.setMsg("获取用户信息失败");
            responseResult.setCode(0);
        }
        responseResult.setData(resultPage);
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


    @ApiOperation(value = "getUserById", notes = "根据用户id获取用户信息")
    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getUser(@PathVariable String id){
        ResponseResult responseResult = new ResponseResult();
        if(StringUtils.isBlank(id)){
            responseResult.setMsg("获取用户信息失败：缺失id值");
            responseResult.setCode(0);
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult.setData(map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }
        LoginUser dbUser = userService.getUser(id);
        if(dbUser != null){
            LoginUserVo resultUser = new LoginUserVo();
            BeanUtils.copyProperties(dbUser, resultUser, "credential");

            responseResult.setCode(1);
            responseResult.setMsg("成功获取用户 "+id+" 的信息");
            Map<String, Object> map = new HashMap<>();
            map.put("user", resultUser);
            responseResult.setData(map);

        } else {
            responseResult.setCode(0);
            responseResult.setMsg("用户id： "+id+" 不存在");
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult.setData(map);
        }
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }



    @ApiOperation(value = "getUserByUsername", notes = "根据用户username获取用户信息")
    @RequestMapping(value = "/user", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String findByUsername(@RequestParam(value = "username") String identifier){
        ResponseResult responseResult = null;
        if(StringUtils.isBlank(identifier)){
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult = new ResponseResult(0, "获取用户信息失败：缺失username值", map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }
        LoginUser dbUser = userService.getUserByIdentifier(identifier);
        if(dbUser != null){
            LoginUserVo resultUser = new LoginUserVo();
            UserinfoVo userinfoVo = new UserinfoVo();
            if(dbUser.getUserinfo() != null){
                BeanUtils.copyProperties(dbUser.getUserinfo(), userinfoVo);

            }
//            BeanUtils.copyProperties(dbUser.getUserinfo(), userinfoVo);
            BeanUtils.copyProperties(dbUser, resultUser);
            //将userinfo设置给resultUser
            resultUser.setUserinfo(userinfoVo);
            Map<String, Object> map = new HashMap<>();
            map.put("user", resultUser);
            responseResult = new ResponseResult(1, "成功获取用户 "+identifier+" 的信息", map);

        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("user", null);
            responseResult = new ResponseResult(0, "用户id： "+identifier+" 不存在", map);

        }
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


    @ApiOperation(value = "editUser", notes = "修改用户信息")
    @RequestMapping(value = "/user/{id}", method = {RequestMethod.PUT}, produces = "application/json; charset=utf-8")
    public String editUser(@PathVariable String id, @RequestBody @ApiParam(name="用户信息对象",value="传入json string 格式",required=true) UserinfoVo userinfoVo){
        Map<String, Object> errors = new HashMap<>();
        if(StringUtils.isBlank(id)){
            errors.put("id", "id值为空");
        }
        if(userinfoVo == null || "{}".equals(userinfoVo) || "".equals(userinfoVo)){
            errors.put("userinfoVo","userinfoVo对象为空");
        }
        if(StringUtils.isBlank(userinfoVo.getNickname())){
            errors.put("nickname","nickname对象为空");
        }
        if((userinfoVo.getNickname().length()<2) || (userinfoVo.getNickname().length()>=20)){
            errors.put("nickname", "nickname的长度必须在2~20之间");
        }
        if(errors.size()>0){
            Map<String, Object> map = new HashMap<>();
            map.put("errors", errors);
            ResponseResult responseResult = new ResponseResult(0, "编辑用户信息失败", map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }

        Userinfo userinfo = new Userinfo();
        BeanUtils.copyProperties(userinfoVo, userinfo, "id", "org", "createTime", "creator", "modifier", "modifyTime", "groups", "roles");

        Userinfo resUserinfo = userService.editUser(id, userinfo);
        if(resUserinfo != null){
            BeanUtils.copyProperties(resUserinfo, userinfoVo);
            ResponseResult responseResult = new ResponseResult(1, "编辑用户信息成功", userinfoVo);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }
        return null;
    }


    /**
     * 根据用户名获取用户的角色
     * @param identifier
     * @return
     */
    @ApiOperation(value = "getRolesByUsername", notes = "根据用户名获取用户的角色")
    @RequestMapping(value = "/user/role/{username}", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getRoleByUsername(@PathVariable(value = "username") String identifier){

        ResponseResult responseResult = null;
        if(StringUtils.isBlank(identifier)){
            Map<String, Object> map = new HashMap<>();
            map.put("roles", null);
            responseResult = new ResponseResult(0, "获取用户角色失败：缺失username值", map);
            return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        }
        List<Role> roleList = userService.getRoleByUsername(identifier);
        List<RoleVo> resultRoleList = new ArrayList<>();
        for(Role role : roleList){
            RoleVo rv = new RoleVo();
            BeanUtils.copyProperties(role, rv);
            resultRoleList.add(rv);
        }

//        BeanUtils.copyProperties(roleList, resultRoleList);


        Map<String, Object> map = new HashMap<>();
        map.put("roles", resultRoleList);
        responseResult = new ResponseResult(1, "成功获取用户角色", map);
        return JSON.toJSONString(responseResult, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

    }


}
