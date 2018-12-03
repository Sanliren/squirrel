package cn.thirdpart.squirrel.lovewords.service.impl;

import cn.thirdpart.squirrel.lovewords.dao.JokeJdbcDao;
import cn.thirdpart.squirrel.lovewords.entity.Category;
import cn.thirdpart.squirrel.lovewords.entity.Joke;
import cn.thirdpart.squirrel.lovewords.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class JokeServiceImpl implements JokeService {


    @Autowired
    JokeJdbcDao jokeJdbcDao;

    /**
     * 根据分类名进行模糊查询分类
     * @param name
     * @return
     */
    public List<Category> getCategoryByName(String name){
        List<Category> categoryList = null;

        return  null;
    }

    public int getJokeCount(){
        return jokeJdbcDao.getJokeCount();
    }

    public Joke getJokeById(Long id){
        return jokeJdbcDao.getJokeById(id);
    }

}
