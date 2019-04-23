package com.baizhi.dao;


import com.baizhi.entity.UserDTO;

import java.util.List;

public interface UserDTODao {

    //展示最近用户注册数量
    Integer selectActiveNumber(int type);

    //展示各个省市的用户量 (男)
    List<UserDTO> selectByProvince1();

    //展示各个省市的用户量 （女）
    List<UserDTO> selectByProvince0();

}
