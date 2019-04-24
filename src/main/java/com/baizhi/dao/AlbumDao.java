package com.baizhi.dao;

import com.baizhi.entity.Album;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumDao extends Mapper<Album> {
    //查询全部的专辑和其下的章节
    List<Album> queryAll();
    //查找最新的前六个专辑
    List<Album> selectNew();


}
