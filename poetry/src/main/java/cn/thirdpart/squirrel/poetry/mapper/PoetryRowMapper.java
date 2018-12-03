package cn.thirdpart.squirrel.poetry.mapper;

import cn.thirdpart.squirrel.poetry.entity.Author;
import cn.thirdpart.squirrel.poetry.entity.Category;
import cn.thirdpart.squirrel.poetry.entity.Poetry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PoetryRowMapper implements RowMapper<Poetry>{
    @Override
    public Poetry mapRow(ResultSet resultSet, int i) throws SQLException {
        Poetry poetry = new Poetry();
        poetry.setId(resultSet.getLong("id"));
        poetry.setAuthorName(resultSet.getString("author_name"));
        poetry.setContent(resultSet.getString("content"));
        //poetry.setCreateTime(resultSet.getTimestamp("create_time"));
        //poetry.setCreator(resultSet.getString("creator"));
        poetry.setImg(resultSet.getString("img"));
        //poetry.setModifier(resultSet.getString("modifier"));
       // poetry.setModifyTime(resultSet.getTimestamp("modify_time"));
        poetry.setNote(resultSet.getString("note"));
        poetry.setStrains(resultSet.getString("strains"));
        poetry.setTitle(resultSet.getString("title"));
        poetry.setVerify(resultSet.getInt("verify"));
        Author author = new Author();
        author.setId(resultSet.getLong("author_id"));
        author.setAuthorName(resultSet.getString("author_name"));
        poetry.setAuthor(author);
        Category category = new Category();
        category.setCategoryType("CATEGORY_POETRY");
        category.setId(resultSet.getLong("category_id"));
        category.setCategoryName(resultSet.getString("category_name"));
        poetry.setCategory(category);

        return poetry;
    }
}
