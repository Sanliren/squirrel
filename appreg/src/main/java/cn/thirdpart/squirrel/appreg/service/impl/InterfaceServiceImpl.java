package cn.thirdpart.squirrel.appreg.service.impl;

import cn.thirdpart.squirrel.appreg.dao.AppRegJdbcDao;
import cn.thirdpart.squirrel.appreg.dao.InterfaceJdbcDao;
import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import cn.thirdpart.squirrel.appreg.entity.AppReg;
import cn.thirdpart.squirrel.appreg.entity.Interface;
import cn.thirdpart.squirrel.appreg.service.InterfaceService;
import cn.thirdpart.squirrel.appreg.vo.AppInterfaceRelationVo;
import cn.thirdpart.squirrel.appreg.vo.InterfaceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    InterfaceJdbcDao interfaceJdbcDao;
    @Autowired
    AppRegJdbcDao appRegJdbcDao;

    @Override
    public AppInterfaceRelation findRelation(String app_id, long interface_id){
        return interfaceJdbcDao.find(app_id, interface_id);
    }

    @Override
    public AppInterfaceRelation findRelation(String id){
        return interfaceJdbcDao.findAppInterface(id);
    }

    @Override
    public boolean interfaceReg(InterfaceVo interfaceVo){
        return interfaceJdbcDao.interfaceReg(interfaceVo);
    }


    public List<Interface> findAppInterface(String appkey){

        return interfaceJdbcDao.findInterfacesByAppkey(appkey);
    }

    public List<Interface> findInterfaces() {
        return interfaceJdbcDao.findInterfaces();
    }


    public boolean relationReg(AppInterfaceRelationVo relationVo){
        AppReg appReg = appRegJdbcDao.getAppByAppkey(relationVo.getAppId());

        if(appReg != null){
            AppInterfaceRelation relation = interfaceJdbcDao.find(relationVo.getAppId(), relationVo.getInterfaceId());
            if(relation == null){
                return interfaceJdbcDao.relationReg(relationVo);
            } else {
                log.error("app_id: "+relationVo.getAppId() + ", interface_id: "+relationVo.getInterfaceId() + "; 应用已经申请了该接口，无需重新申请");
                return  false;
            }
        } else {
            log.error("应用id："+relationVo.getAppId()+" , 还没有接入平台");
            return false;
        }

    }

}
