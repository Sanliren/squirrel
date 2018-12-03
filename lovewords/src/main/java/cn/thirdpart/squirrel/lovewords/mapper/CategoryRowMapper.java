package cn.thirdpart.squirrel.lovewords.mapper;

import cn.thirdpart.squirrel.lovewords.entity.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setCategoryName(resultSet.getString("category_name"));
        category.setCreateTime(resultSet.getTimestamp("create_time"));
        category.setCreator(resultSet.getString("creator"));
        category.setDescription(resultSet.getString("description"));
        category.setModifier(resultSet.getString("modifier"));
        category.setModifyTime(resultSet.getTimestamp("modify_time"));
        category.setCategoryType(resultSet.getString("category_type"));
        return category;
    }
}
