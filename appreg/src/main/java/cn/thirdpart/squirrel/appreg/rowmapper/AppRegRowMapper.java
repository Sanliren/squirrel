package cn.thirdpart.squirrel.appreg.rowmapper;

import cn.thirdpart.squirrel.appreg.entity.AppReg;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppRegRowMapper  implements RowMapper<AppReg> {
    @Override
    public AppReg mapRow(ResultSet resultSet, int i) throws SQLException {
        AppReg appReg = new AppReg();

        appReg.setAppKey(resultSet.getString("app_key"));
        appReg.setAppSecret(resultSet.getString("app_secret"));
        appReg.setAppName(resultSet.getString("app_name"));
        appReg.setAppDesc(resultSet.getString("app_desc"));
        appReg.setAppIcon(resultSet.getString("app_icon"));
        appReg.setCreateTime(resultSet.getTimestamp("create_time"));
        appReg.setCreator(resultSet.getString("creator"));
        appReg.setModifier(resultSet.getString("modifier"));
        appReg.setModifyTime(resultSet.getTimestamp("modify_time"));
        appReg.setLoginuserId(resultSet.getString("loginuser_id"));

        return appReg;
    }
}
