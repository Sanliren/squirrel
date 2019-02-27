package cn.thirdpart.squirrel.appreg.rowmapper;

import cn.thirdpart.squirrel.appreg.entity.Interface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterfaceRowMapper  implements RowMapper<Interface> {

    @Override
    public Interface mapRow(ResultSet resultSet, int i) throws SQLException {
        Interface result = new Interface();

        result.setId(resultSet.getLong("id"));
        result.setAddress(resultSet.getString("address"));
        result.setAmount(resultSet.getInt("amount"));
        result.setCost(resultSet.getInt("cost"));
        result.setStatus(resultSet.getInt("status"));
        result.setDescription(resultSet.getString("description"));
        result.setName(resultSet.getString("name"));
        result.setCreateTime(resultSet.getTimestamp("create_time"));
        result.setCreator(resultSet.getString("creator"));
        result.setModifier(resultSet.getString("modifier"));
        result.setModifyTime(resultSet.getTimestamp("modify_time"));


        return result;
    }

}
