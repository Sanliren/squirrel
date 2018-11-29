package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import cn.thirdpart.squirrel.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<LoginUser> {

    /**
     * 根据用户名获取该用户的所有角色
     * @param id
     * @return
     */
    @Query(value = "SELECT * FROM t_role r JOIN t_userinfo_role_r ur  ON r.id = ur.role_id JOIN t_userinfo u ON u.id = ur.userinfo_id JOIN t_role_permission_r rp ON rp.role_id = r.id JOIN t_permission p ON rp.permission_id = p.id where u.id = ?1", nativeQuery = true)
    List<Role> getRoleByUserinfoId(String userinfoId);

}
