package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginUserDao extends JpaRepository<LoginUser, String>, JpaSpecificationExecutor<LoginUser> {

    /**
     * 通过登录标识 和密码凭证查找用户
     * @return
     */
    LoginUser findByIdentifierAndCredentialAndIdentityType(String identifier, String credential, String identity_type);


    /**
     * 通过id获取用户，并且获取他的信息
     * @param id
     * @return
     */
    @Query("select u from LoginUser u left join fetch u.userinfo where u.id = (:id)")
    LoginUser getUserById(@Param("id")String id);


    /**
     * 通过identifier获取用户，并且获取他的信息
     * @param identifier
     * @return
     */
    @Query("select u from LoginUser u left join fetch u.userinfo where u.identifier = (:identifier)")
    LoginUser getUserByIdentifier(@Param("identifier")String identifier);

}
