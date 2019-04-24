package com.baizhi.service;

import com.baizhi.entity.Master;

import java.util.List;
import java.util.Map;

public interface MasterService {

    List<Master> selectAll();

    Map queryByPage(int page,int rows);

    void insert(Master master);

    void delete(Integer id);

    void update(Master master);


}
