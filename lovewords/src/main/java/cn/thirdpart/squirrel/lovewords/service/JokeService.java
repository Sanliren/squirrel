package cn.thirdpart.squirrel.lovewords.service;

import cn.thirdpart.squirrel.lovewords.entity.Category;
import cn.thirdpart.squirrel.lovewords.entity.Joke;

import java.util.List;

public interface JokeService {

    public List<Category> getCategoryByName(String name);

    /**
     * 返回笑话表的总记录数
     * @return
     */
    public int getJokeCount();

    /**
     * 通过id获取笑话
     * @param id
     * @return
     */
    public Joke getJokeById(Long id);

}
