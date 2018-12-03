package cn.thirdpart.squirrel.lovewords.dao;

import cn.thirdpart.squirrel.lovewords.config.redis.RedisUtil;
import cn.thirdpart.squirrel.lovewords.entity.Category;
import cn.thirdpart.squirrel.lovewords.entity.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Repository
public class JokeJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    /**
     * 根据id获取分类对象
     * @param id
     * @return
     */
    public Category getCategoryById(Long id){
        String sql = "select * from t_category where id = ?";
        Category category = jdbcTemplate.queryForObject(sql, new Object[]{id}, Category.class);
        return category;
    }

    /**
     * 根据id和所需要字段值获取分类
     * @param id
     * @param columnName 表的列名
     * @return
     */
    public Category getCategoryById(Long id, String[] columnName){
        String sql = "select ";
        for(int x=0; x<columnName.length; x++){
            if(x == columnName.length-1){
                sql = sql + columnName[x] + " ";
            } else {
                sql = sql + columnName[x] + ", ";
            }
        }
        Category category = jdbcTemplate.queryForObject(sql, new Object[]{id}, Category.class);
        return category;
    }

    /**
     * 根据id查询joke
     * @param id
     * @return
     */
    public Joke getJokeById(Long id){
        String sql = "select t.*, c.category_name from t_joke t join t_category c on t.category_id = c.id where t.id = ?";
//        Joke joke = jdbcTemplate.queryForObject(sql, new Object[]{id}, Joke.class);
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, new Object[]{id});
        Joke joke = new Joke();
        joke.setId((long)map.get("id"));
        joke.setContent((String)map.get("content"));
        joke.setCreator((String)map.get("creator"));
        joke.setCreateTime((Timestamp)map.get("create_time"));
        joke.setImgurl((String)map.get("imgurl"));
        joke.setModifier((String)map.get("modifier"));
        joke.setModifyTime((Timestamp)map.get("modify_time"));
        joke.setVerify((int)map.get("verify"));
        joke.setCategoryName((String)map.get("category_name"));
        Category category = new Category();
        category.setId((long)map.get("category_id"));
        joke.setCategory(category);
        return joke;
    }

    /**
     * 获取joke的数量
     * @return
     */
    public int getJokeCount(){
        String sql = "select count(id) from t_joke";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        return  total;
    }

}
