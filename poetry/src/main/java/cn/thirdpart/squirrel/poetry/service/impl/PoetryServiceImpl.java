package cn.thirdpart.squirrel.poetry.service.impl;

import cn.thirdpart.squirrel.poetry.dao.PoetryJdbcDao;
import cn.thirdpart.squirrel.poetry.entity.Poetry;
import cn.thirdpart.squirrel.poetry.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class PoetryServiceImpl implements PoetryService {

    @Autowired
    PoetryJdbcDao poetryJdbcDao;

    @Override
    public List<Poetry> getPoety(String title, String authorName, int page, int pageSize){

        return poetryJdbcDao.getPoetry(title, authorName, page, pageSize);
    }

}
