package cn.thirdpart.squirrel.poetry.service;

import cn.thirdpart.squirrel.poetry.entity.Poetry;

import java.util.List;

public interface PoetryService {

    /**
     * 根据诗词的题目或作者名称分页查找诗歌
     * @param title
     * @param authorName
     * @param page
     * @param pageSize
     * @return
     */
    public List<Poetry> getPoety(String title, String authorName, int page, int pageSize);

}
