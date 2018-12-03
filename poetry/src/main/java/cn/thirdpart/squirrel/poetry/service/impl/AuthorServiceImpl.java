package cn.thirdpart.squirrel.poetry.service.impl;

import cn.thirdpart.squirrel.poetry.dao.PoetryJdbcDao;
import cn.thirdpart.squirrel.poetry.entity.Author;
import cn.thirdpart.squirrel.poetry.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class AuthorServiceImpl implements AuthorService {


    public void batchStoreAuthorByJsonFile(Iterable<Author> authors) throws Exception{
//        authorDao.saveAll()
    }



}
