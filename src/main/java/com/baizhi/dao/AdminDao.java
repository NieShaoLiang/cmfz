package com.baizhi.dao;

import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    //管理员只有登陆方法
    User selectByAdmin(@Param("name") String name, @Param("password") String password);

}
