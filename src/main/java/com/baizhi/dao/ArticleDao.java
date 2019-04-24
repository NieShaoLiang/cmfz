package com.baizhi.dao;

import com.baizhi.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {
    //跟据上师id查找
    List<Article> selectByMasterId(Integer masterId);
    //查排除某个上师id查找
    List<Article> selectExceptMasterId(Integer masterId);

}
