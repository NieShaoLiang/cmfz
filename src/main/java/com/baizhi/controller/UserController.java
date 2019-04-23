package com.baizhi.controller;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("selectAll")
    public Map selectAll(int page,int rows){
        Map map = userService.selectAll(page, rows);
        System.out.println(map);
        return map;
    }

    @RequestMapping("insert")
    public Map insert(User user, MultipartFile file) throws IOException {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));

        System.out.println(oldName);
        file.transferTo(new File("E:/服务器/"+newName));
        user.setHeadImg(newName);
        user.setStatus(0);
        user.setSex(0);
        user.setProvince("新疆");
        user.setCreateDate(new Date());
        //System.out.println(user);

        Map map = new HashMap();
        try {
            userService.insert(user);
            map.put("isOk",true);
            Map content = new HashMap();
            //重新查询活跃用户
            Map map1 = userService.selectActiveNumber();
            content.put("activeNumber",map1);
            //查询各个省的注册人数
            List<UserDTO> list0 = userService.selectByProvince0();
            List<UserDTO> list1 = userService.selectByProvince1();
            content.put("famale",list0);
            content.put("male",list1);
            String s = JSON.toJSONString(content);
            //发送信息
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-375d0793129e4b7c900d1c6282061a17");
            goEasy.publish("nsl",s);
        } catch (Exception e) {
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("delete")
    public Map delete(Integer id){
        Map map = new HashMap();
        try {
            userService.delete(id);
            map.put("isOk",true);
        } catch (Exception e) {
            map.put("isOk",false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("update")
    public Map update(User user){
        Map map = new HashMap();
        try {
            userService.update(user);
            map.put("isOk",true);
        } catch (Exception e) {
            map.put("isOk",false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("selectActiveNumber")
    public Map selectActiveNumber(){
        Map map = userService.selectActiveNumber();
        System.out.println(map);
        return map;
    }

    @RequestMapping("selectByProvince1")
    public Map selectByProvince1(){
        Map map = new HashMap();
        try {
            List<UserDTO> list = userService.selectByProvince1();
            map.put("list",list);
            map.put("isOk",true);
        } catch (Exception e) {
            map.put("isOk",false);
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("selectByProvince0")
    public Map selectByProvince0(){
        Map map = new HashMap();
        try {
            List<UserDTO> list = userService.selectByProvince0();
            map.put("list",list);
            map.put("isOk",true);
        } catch (Exception e) {
            map.put("isOk",false);
            e.printStackTrace();
        }
        return map;
    }

}
