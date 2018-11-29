package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<Permission, String> {

}
