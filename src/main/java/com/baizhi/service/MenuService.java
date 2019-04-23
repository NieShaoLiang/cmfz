package com.baizhi.service;

import com.baizhi.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuService{

    //查看全部的一级导航
    List<Menu> selectAllNav();

}
