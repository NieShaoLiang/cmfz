package com.baizhi.dao;

import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<User> {


    //根据用户名查找用户
    User selectByName(String name);

    //根据用户名和密码查找用户
    User selectByNamePwd(String name,String password);

}
