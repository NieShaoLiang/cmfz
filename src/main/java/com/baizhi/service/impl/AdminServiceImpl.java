package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.User;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public User login(String name, String password) {
        return adminDao.selectByAdmin(name,password);
    }
}
