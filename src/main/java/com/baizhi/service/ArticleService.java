package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {

    void insert(Article article);

    void delete(Integer id);

    void update(Article article);

    Map selectAll(int page, int rows);

}
