package cn.thirdpart.squirrel.appreg.service;


import cn.thirdpart.squirrel.appreg.entity.AppReg;

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

}
