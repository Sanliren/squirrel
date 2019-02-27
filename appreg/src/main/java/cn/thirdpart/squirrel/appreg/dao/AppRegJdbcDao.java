package cn.thirdpart.squirrel.appreg.dao;

import cn.thirdpart.squirrel.appreg.entity.AppReg;
import cn.thirdpart.squirrel.appreg.rowmapper.AppRegRowMapper;
import cn.thirdpart.squirrel.appreg.util.BPwdEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class AppRegJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 注册一个app应用
     * @param appReg
     * @return
     */
    public AppReg appReg(AppReg appReg){
        String sql = "insert into t_app values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Date now = new Date();
        appReg.setModifyTime(now);
        appReg.setCreateTime(now);
        Object[] params = new Object[]{appReg.getAppKey(), appReg.getAppSecret(), appReg.getAppName(),
                                    appReg.getAppDesc(), appReg.getAppIcon(), appReg.getCreateTime(), appReg.getCreator(),
                                    appReg.getModifier(), appReg.getModifyTime(), appReg.getLoginuserId()};
        int num = jdbcTemplate.update(sql, params);
        if(num > 0){
//            BPwdEncoderUtil.BCryptPassword(loginUser.getCredential())
            String sql_client = "insert into oauth_client_details values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params_client = new Object[]{
                    appReg.getAppKey(),
                    "",
                    BPwdEncoderUtil.BCryptPassword(appReg.getAppSecret()),
                    "app_other",
                    "client_credentials,refresh_token",
                    "",
                    "read",
                    604800,//7天过期
                    null,
                    "{}",
                    ""
            };

            int s = jdbcTemplate.update(sql_client, params_client);
            if(s > 0){
                return appReg;
            } else {
                log.info("插入数据到oauth_client_details表失败！！");
                return  null;
            }

        } else {
            log.info("插入数据到t_app表失败！！");
            return  null;
        }
    }

    /**
     * 根据应用appkey获取app应用
     * @param appKey
     * @return
     */
    public AppReg getAppByAppkey(String appKey){
        String sql = "select * from t_app where app_key = ?";
        Object[] params = new Object[] {
                appKey
        };
        AppReg appReg = null;
        List<AppReg> appRegs = jdbcTemplate.query(sql, params, new AppRegRowMapper());
        if(appRegs == null || appRegs.isEmpty()){
            return null;
        } else {
            appReg = appRegs.get(0);
        }
        if(appReg != null){
            String sql_client = "select client_id from oauth_client_details where client_id = ?";
            String client_id = jdbcTemplate.queryForObject(sql_client, new Object[]{appReg.getAppKey()}, String.class);
            if(StringUtils.isNotBlank(client_id)){
                return appReg;
            } else {
                log.info("oauth_client_details表中没有存在相应应用的client_id！！");
                return  null;
            }
        } else {
            log.info("t_app表中没有相应的应用记录！！");
            return  null;
        }

    }

    /**
     * 查找我的应用
     * @param myid
     * @return
     */
    public List<AppReg> getMyAppRegs(String myid){
        List<AppReg> appRegs = null;
        String sql = "select * from t_app where loginuser_id = ?";
        Object[] params = new Object[]{
                myid
        };
        appRegs = jdbcTemplate.query(sql, params, new AppRegRowMapper());
        return appRegs;
    }

    /**
     * 根据参数返回按照时间排序返回前num条数据
     * @param num
     * @return
     */
    public List<AppReg> findApps(int num){
        List<AppReg> appRegs = null;
        String sql = "SELECT * FROM t_app t  ORDER BY t.create_time LIMIT ?";
        Object[] params = new Object[]{
                num
        };
        appRegs = jdbcTemplate.query(sql, params, new AppRegRowMapper());
        return appRegs;
    }


}
