package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页
    Map selectAll(int page, int rows);

    User selectByName(String name);

    User selectByNamePwd(String name,String password);

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
