package com.baizhi.dao;


import com.baizhi.entity.Chapter;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter> {

    List<Chapter> selectByAlbumId(Integer albumId);

}
