package cn.thirdpart.squirrel.lovewords.mapper;

import cn.thirdpart.squirrel.lovewords.entity.Category;
import cn.thirdpart.squirrel.lovewords.entity.Joke;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JokeRowMapper implements RowMapper<Joke> {
    @Override
    public Joke mapRow(ResultSet resultSet, int i) throws SQLException {

        Joke joke = new Joke();
        joke.setId(resultSet.getLong("id"));
        joke.setContent(resultSet.getString("content"));
        joke.setCreateTime(resultSet.getTimestamp("create_time"));
        joke.setCreator(resultSet.getString("creator"));
        joke.setImgurl(resultSet.getString("imgurl"));
        joke.setModifier(resultSet.getString("modifier"));
        joke.setModifyTime(resultSet.getTimestamp("modify_time"));
        joke.setVerify(resultSet.getInt("verify"));
        Category category = new Category();
        category.setId(resultSet.getLong("category_id"));
        joke.setCategory(category);
        joke.setCategoryName(resultSet.getString("category_name"));
        return joke;
    }
}
