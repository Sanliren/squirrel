package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.UserRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRelationDao extends JpaRepository<UserRoleRelation, String> {

}
