package cn.thirdpart.squirrel.appreg.service;


import cn.thirdpart.squirrel.appreg.entity.AppReg;

import java.util.List;

public interface AppRegService {

    /**
     * app申请接入
     * @param appReg
     * @return
     */
    public AppReg appReg(AppReg appReg);


    /**
     * 根据appkey获取app应用
     * @param appKey
     * @return
     */
    public AppReg getAppByAppkey(String appKey);


    /**
     * 根据appkey获取app访问的token
     * @param appKey
     * @return
     */
    public String getAppToken(String appKey);


    public String getAppToken2(String id);


    /**
     * 根据用户登录id获取用户申请接入的应用
     * @param userid
     * @return
     */
    public List<AppReg> getMyApps(String userid);

    /**
     * 根据传递的数量获取相应数量的app
     * @param num
     * @return
     */
    public List<AppReg> getHotApp(int num);

}
