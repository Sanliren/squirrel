package cn.thirdpart.squirrel.poetry.dao;

import cn.thirdpart.squirrel.poetry.entity.Author;
import cn.thirdpart.squirrel.poetry.entity.Category;
import cn.thirdpart.squirrel.poetry.entity.Poetry;
import cn.thirdpart.squirrel.poetry.mapper.PoetryRowMapper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用jdbc对数据库进行查询，jpa效率很低
 */
@Repository
public class PoetryJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据诗歌题目和诗歌作者查找诗歌
     * @param title
     * @param authorName
     * @param page
     * @param pageSize
     * @return
     */
    public List<Poetry> getPoetry(String title, String authorName, int page, int pageSize){
        List<Poetry> poetries = null;
//        if(StringUtils.isBlank(title) && StringUtils.isBlank(authorName)){
//            return null;
//        }
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<>();
        String basic = "select a.*, b.category_name from (select t.id, t.author_name, t.content, t.img, t.note, t.strains, t.title, t.verify, t.author_id, t.category_id from t_poetry t where 1=1 ";
        sb.append(basic);
        if(StringUtils.isNotBlank(title)){
            sb.append(" and t.title like ?");
            params.add("%"+title+"%");
        }
        if (StringUtils.isNotBlank(authorName)){
            sb.append(" and t.author_name like ?");
            params.add("%"+authorName+"%");
        }

        //最后添加分页
        sb.append(" limit "+ (page-1)*pageSize +"," + pageSize);
        sb.append(") a");
        sb.append(" join t_category b on a.category_id = b.id");
        //TODO 以后等作者表和author_id对应上的时候还要添加 join t_author...

        poetries = jdbcTemplate.query(sb.toString(), params.toArray(), new PoetryRowMapper());


        return poetries;
    }

    /**
     * 获取共有多少诗歌，即诗歌总数量
     * @return
     */
    public int getCount(){
        String sql = "select count(id) from t_poetry";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        return  total;
    }

}


