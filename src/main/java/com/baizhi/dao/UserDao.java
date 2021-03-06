package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<User> {


    //根据用户名查找用户
    User selectByName(String name);

    //根据用户名和密码查找用户
    User selectByNamePwd(@Param("name") String name,@Param("password") String password);

    //查询当前用户的上师id
    Integer selectMasterId(String id);

    //根据手机号查找用户
    User selectByPhone(String phone);

}
