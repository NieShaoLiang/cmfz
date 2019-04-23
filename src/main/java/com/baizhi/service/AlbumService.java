package com.baizhi.service;

import com.baizhi.entity.Album;
import java.util.List;
import java.util.Map;

public interface AlbumService {

    List<Album> queryAll();

    Map selectAll(int page,int rows);

    void insert(Album album);

    void delete(Integer id);

    void update(Album album);

    Album selectOne(Integer id);



}
