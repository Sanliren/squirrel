package cn.thirdpart.squirrel.appreg.service;

import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import cn.thirdpart.squirrel.appreg.entity.Interface;
import cn.thirdpart.squirrel.appreg.vo.AppInterfaceRelationVo;
import cn.thirdpart.squirrel.appreg.vo.InterfaceVo;

import java.util.List;

public interface InterfaceService {

    /**
     * 确定是否存在应用和接口的关系
     * @param app_id
     * @return
     */
    public AppInterfaceRelation findRelation(String app_id, long interface_id);


    /**
     * 添加一个接口数据
     * @param interfaceVo
     * @return
     */
    public boolean interfaceReg(InterfaceVo interfaceVo);


    /**
     * 根据appkey查找对应应用已经申请了的接口
     * @param appkey
     * @return
     */
    public List<Interface> findAppInterface(String appkey);

    /**
     * 查找所有的接口
     * @return
     */
    public List<Interface> findInterfaces();

    /**
     * 通过id查找应用和接口的关联数据记录
     * @param id
     * @return
     */
    public AppInterfaceRelation findRelation(String id);

    /**
     * 应用申请某个接口
     * @param relationVo
     * @return
     */
    public boolean relationReg(AppInterfaceRelationVo relationVo);

}
