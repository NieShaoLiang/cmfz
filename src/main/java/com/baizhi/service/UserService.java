package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页
    Map selectAll(int page, int rows);

    //注册前判断是否存在该用户
    User selectByName(String name);
    //登录
    User selectByNamePwd(String name,String password);
    //添加用户
    void insert(User user);

    void delete(Integer id);

    void update(User user);

    //展示近一周的用户注册数量
    Map selectActiveNumber();

    //展示各个省市的用户量
    List<UserDTO> selectByProvince1();
    //展示各个省市的用户量
    List<UserDTO> selectByProvince0();


}
