package cn.thirdpart.squirrel.user.service;

import cn.thirdpart.squirrel.user.entity.LoginUser;
import cn.thirdpart.squirrel.user.entity.Role;
import cn.thirdpart.squirrel.user.entity.Userinfo;
import cn.thirdpart.squirrel.user.exception.UserException;
import cn.thirdpart.squirrel.user.vo.LoginUserVo;
import cn.thirdpart.squirrel.user.vo.UserLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /**
     * 用户注册
     * @param formUser
     */
    public LoginUser register(LoginUserVo formUser)throws UserException;


    /**
     * 用户登录，由于要返回token，所以要用到UserLoginDTO类
     * @param identifier    username
     * @param credential    password
     * @return
     */
    public UserLoginDTO login(String identifier, String credential) throws UserException;

    /**
     * 根据条件查询用户
     * @param pageable 分页对象
     * @param username 用户名
     * @param nickname 昵称
     * @return
     */
    public Page<LoginUser> getUsers(Pageable pageable, String username, String nickname);


    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    public LoginUser getUser(String id);


    /**
     * 根据用户identifier查找用户
     * @param identifier
     * @return
     */
    public LoginUser getUserByIdentifier(String identifier);


    /**
     * 根据id，和userinfo编辑用户信息
     * @param id
     * @param userinfo
     * @return
     */
    public Userinfo editUser(String id, Userinfo userinfo);


    /**
     * 根据用户名获取用户的
     * @param identifier
     * @return
     */
    public List<Role> getRoleByUsername(String identifier);


}
