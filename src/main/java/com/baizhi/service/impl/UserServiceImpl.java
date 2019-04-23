package com.baizhi.service.impl;

import com.baizhi.dao.UserDTODao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import com.github.pagehelper.PageHelper;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.GregorianCalendar.BC;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDTODao userDTODao;

    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap();
        List<User> users = userDao.selectAll();
        Integer total = users.size();
        //System.out.println(total);
        map.put("total",total);

        PageHelper.startPage(page,rows);
        List<User> list = userDao.selectAll();
        map.put("rows",list);
        return map;
    }

    @Override
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public User selectByNamePwd(String name, String password) {
        return userDao.selectByNamePwd(name,password);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map selectActiveNumber() {
        Map map = new HashMap();
        List list = new ArrayList();
        List list1 = new ArrayList();
        String str="";
        for (int i = 0; i <3 ; i++) {
            Integer number = userDTODao.selectActiveNumber(i+1);
            str="第"+(i+1)+"周";
            list1.add(str);
            list.add(number);
        }
        map.put("number",list);
        map.put("weeks",list1);

        return map;
    }

    @Override
    public List<UserDTO> selectByProvince1() {
        return userDTODao.selectByProvince1();
    }

    @Override
    public List<UserDTO> selectByProvince0() {
        return userDTODao.selectByProvince0();
    }
}
