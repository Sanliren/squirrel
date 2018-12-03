package cn.thirdpart.squirrel.poetry.service;


import cn.thirdpart.squirrel.poetry.entity.Author;

public interface AuthorService {

    /**
     * 通过json文件进行批量存储作者信息
     * @param authors
     */
    public void batchStoreAuthorByJsonFile(Iterable<Author> authors) throws Exception;

}
