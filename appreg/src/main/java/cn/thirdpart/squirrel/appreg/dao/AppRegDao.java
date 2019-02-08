package cn.thirdpart.squirrel.appreg.dao;

import cn.thirdpart.squirrel.appreg.entity.AppReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AppRegDao extends JpaRepository<AppReg, String>, JpaSpecificationExecutor<AppReg> {

}
