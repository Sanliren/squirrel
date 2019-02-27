package cn.thirdpart.squirrel.appreg.rowmapper;

import cn.thirdpart.squirrel.appreg.entity.AppInterfaceRelation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppInterfaceRelationRowMapper implements RowMapper<AppInterfaceRelation> {
    @Override
    public AppInterfaceRelation mapRow(ResultSet resultSet, int i) throws SQLException {
        AppInterfaceRelation result = new AppInterfaceRelation();

        result.setId(resultSet.getString("id"));
        result.setAppId(resultSet.getString("app_id"));
        result.setInterfaceId(resultSet.getLong("interface_id"));
        result.setStatus(resultSet.getInt("status"));
        result.setCreateTime(resultSet.getTimestamp("create_time"));
        result.setCreator(resultSet.getString("creator"));
        result.setModifier(resultSet.getString("modifier"));
        result.setModifyTime(resultSet.getTimestamp("modify_time"));


        return result;
    }
}
