package cn.thirdpart.squirrel.user.service.impl;

import cn.thirdpart.squirrel.user.authfeignclient.AuthServiceClient;
import cn.thirdpart.squirrel.user.config.redis.RedisUtil;
import cn.thirdpart.squirrel.user.dao.LoginUserDao;
import cn.thirdpart.squirrel.user.dao.RoleDao;
import cn.thirdpart.squirrel.user.dao.UserinfoDao;
import cn.thirdpart.squirrel.user.entity.LoginUser;
import cn.thirdpart.squirrel.user.entity.Permission;
import cn.thirdpart.squirrel.user.entity.Role;
import cn.thirdpart.squirrel.user.entity.Userinfo;
import cn.thirdpart.squirrel.user.exception.UserException;
import cn.thirdpart.squirrel.user.service.UserService;
import cn.thirdpart.squirrel.user.util.BPwdEncoderUtil;
import cn.thirdpart.squirrel.user.util.CommonUtil;
import cn.thirdpart.squirrel.user.util.EncryptUtil;
import cn.thirdpart.squirrel.user.vo.JWT;
import cn.thirdpart.squirrel.user.vo.LoginUserVo;
import cn.thirdpart.squirrel.user.vo.UserLoginDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class UserServiceImpl implements UserService {

    @Autowired
    AuthServiceClient authServiceClient;
    @Autowired
    LoginUserDao loginUserDao;
    @Autowired
    UserinfoDao userinfoDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public LoginUser register(LoginUserVo formUser) throws UserException{

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(formUser, loginUser);
        LoginUser dbUser = loginUserDao.findByIdentifierAndCredentialAndIdentityType(loginUser.getIdentifier(), loginUser.getCredential(), loginUser.getIdentityType());
        if(dbUser == null){
            //用户不存在，可以注册
            //注册前先填充创建时间，创建人……
            String uid = CommonUtil.uuid();
            loginUser.setId(uid);
            //进行密码加密
//            loginUser.setCredential(EncryptUtil.encryptSHA(loginUser.getCredential()));
            loginUser.setCredential(BPwdEncoderUtil.BCryptPassword(loginUser.getCredential()));
            loginUser.setCreateTime(new Date());
            loginUser.setCreator(uid);
            loginUser.setModifyTime(new Date());
            loginUser.setModifier(uid);
            loginUser.setStatus(1);//

            LoginUser result = loginUserDao.saveAndFlush(loginUser);
            return result;
        } else {
            //用户已存在
            throw new UserException("用户已存在，无须注册");
        }
    }


    @Override
    public UserLoginDTO login(String identifier, String credential) throws UserException{

        LoginUser loginUser = loginUserDao.getUserByIdentifier(identifier);
        if(loginUser == null){
            throw new UserException("用户不存在");
        }
        if(!BPwdEncoderUtil.matches(credential, loginUser.getCredential())){
            throw new UserException("密码或者用户名错误");
        }
        String jwtJson = null;
        //userJwtKey由identifier+id组成
        String userJwtKey = loginUser.getIdentifier()+"_"+loginUser.getId();
        String jwt_redis = (String)redisUtil.get(userJwtKey);
//        log.info("jwt_redis : "+jwt_redis);
        if(StringUtils.isBlank(jwt_redis)){
            //请求认证服务获取到jwt
            jwtJson = authServiceClient.getToken("Basic YXV0aF9zZXJ2ZXI6MTIzNDU2", "password", identifier, credential);
            //过期时间
            JSONObject jsonObject = JSON.parseObject(jwtJson);
            long deadline = jsonObject.getLongValue("expires_in");//过期时间
            //将jwt保存到redis
            redisUtil.set(userJwtKey, jwtJson, deadline);
        } else {
            //jwt_redis 不为空, 证明redis缓存中有，则取缓存中的值
            jwtJson = jwt_redis;
        }

        //dXNlci1zZXJ2aWNlOjEyMzQ1Ng== 是 user-service:123456的 base64编码
        // dXNlcjoxMjM0NTY= 是 user:123456的 base64编码
//        JWT jwt = authServiceClient.getToken("Basic dXNlcjoxMjM0NTY=", "password", identifier, credential);
//        JWT jwt = authServiceClient.getToken( "password", identifier, credential);
        //将jwtJson的json字符串转换成JWT对象
        JWT jwt = JSON.parseObject(jwtJson, new TypeReference<JWT>(){});
        if(jwt.getAccess_token()==null || "".equals(jwt.getAccess_token())){
            throw new RuntimeException("用户Token存在异常");
        }

        System.out.println("jwtJson String is : "+jwtJson);
        System.out.println(loginUser.getIdentifier()+"'s access_token is : "+jwt.getAccess_token());
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setLoginUser(loginUser);
        return userLoginDTO;
    }


    @Override
    public Page<LoginUser> getUsers(Pageable pageable, String username, String nickname){

        return loginUserDao.findAll(new Specification<LoginUser>() {

            @Override
            public Predicate toPredicate(Root<LoginUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<LoginUser, Userinfo> userinfoJoin = root.join("userinfo", JoinType.LEFT);
                //userinfoJoin表示join的表，直接用类属性名，而不是表字段名
                userinfoJoin.on(cb.equal(userinfoJoin.get("id"), root.get("userinfo").get("id")));
                if(StringUtils.isNotBlank(username)){
                    predicateList.add(cb.like(root.get("identifier"), "%"+username+"%"));

                }
                if(StringUtils.isNotBlank(nickname)){
                    predicateList.add(cb.like(root.get("nickname"), "%"+nickname+"%"));

                }

                Predicate[] predicates = new Predicate[predicateList.size()];
                predicates = predicateList.toArray(predicates);

                return criteriaQuery.distinct(true).where(cb.and(predicates)).getRestriction();
            }
        }, pageable);
    }


    @Override
    public LoginUser getUser(String id){
        LoginUser dbUser = loginUserDao.getUserById(id);

        return dbUser;
    }


    @Override
    public Userinfo editUser(String id, Userinfo userinfo){
        //先按id查找出用户信息
        LoginUser dbOldUser = loginUserDao.getUserById(id);
        Userinfo dbOldUserinfo = dbOldUser.getUserinfo();
        if( dbOldUserinfo == null){
            dbOldUserinfo = new Userinfo();
            userinfo.setCreateTime(new Date());
            userinfo.setCreator(dbOldUser.getId());
            userinfo.setId(CommonUtil.uuid());//新建需要设置id
//            userinfo.setModifier(dbOldUser.getId());
//            userinfo.setModifyTime(new Date());
        }

        //更新旧修改时间/人
        dbOldUserinfo.setModifyTime(new Date());
        dbOldUserinfo.setModifier(dbOldUser.getId());

        BeanUtils.copyProperties(userinfo, dbOldUserinfo, "id", "org", "createTime", "creator", "modifier", "modifyTime", "groups", "roles");

        dbOldUser.setUserinfo(dbOldUserinfo);
        LoginUser newUser = loginUserDao.save(dbOldUser);

        return newUser.getUserinfo();
    }


    @Override
    public LoginUser getUserByIdentifier(String identifier){
        LoginUser dbUser = loginUserDao.getUserByIdentifier(identifier);

        return dbUser;
    }

    @Override
    public List<Role> getRoleByUsername(String identifier){
        //先通过identifier拿到userinfo对象，从而拿到userinfo的id，再通过user info的id查权限
        LoginUser dbUser = loginUserDao.getUserByIdentifier(identifier);
        Userinfo userinfo = dbUser.getUserinfo();

//        System.out.println("userinfoid : "+userinfo.getId());
        List<Role> userRoles = roleDao.getRoleByUserinfoId(userinfo.getId());
        return userRoles;
    }


}
