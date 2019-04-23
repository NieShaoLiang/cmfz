package com.baizhi.dao;

import com.baizhi.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuDao extends Mapper<Menu> {

    //查看全部的导航，一级导航下有二级导航的list
    List<Menu> selectAllNav();


}
