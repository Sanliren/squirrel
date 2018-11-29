package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import cn.thirdpart.squirrel.user.entity.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrgDao extends JpaRepository<Org, String>, JpaSpecificationExecutor<LoginUser> {

}
