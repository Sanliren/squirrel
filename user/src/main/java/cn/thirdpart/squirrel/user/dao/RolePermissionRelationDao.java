package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.RolePermissionRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRelationDao extends JpaRepository<RolePermissionRelation, String> {

}
