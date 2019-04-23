package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void delete(Integer id) {
        articleDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap();

        RowBounds rowBounds = new RowBounds(rows*(page-1),rows);
        List<Article> articles = articleDao.selectByRowBounds(new Article(), rowBounds);

        map.put("rows",articles);
        int total = articleDao.selectAll().size();
        map.put("total",total);
        return map;
    }

}
