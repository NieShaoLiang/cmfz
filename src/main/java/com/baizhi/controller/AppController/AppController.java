package com.baizhi.controller.AppController;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.User;
import com.baizhi.service.appService.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
public class AppController {
    @Autowired
    private AppService appService;

    @RequestMapping("queryAll")
    public String queryAll(String uid,String type,String sub_type){
        Object o = appService.queryAll(uid, type, sub_type);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("articleDetail")
    public String articleDetail(Integer id,Integer uid){
        Object o = appService.articleDetail(id, uid);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("albumDetail")
    public String albumDetail(Integer id,Integer uid){
        Object o = appService.albumDetail(id, uid);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("login")
    public String login(String phone,String pwd,String code){
        Object o = appService.login(phone,pwd,code);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("regist")
    public String regist(String phone,String pwd){
        Object o = appService.regist(phone,pwd);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("changeUser")
    public String changeUser(User user){
        Object o = appService.changeUser(user);
        String s = JSON.toJSONString(o);
        return s;
    }
    @RequestMapping("queryUser")
    public String queryUser(Integer uid){
        Object o = appService.queryUser(uid);
        String s = JSON.toJSONString(o);
        return s;
    }



}
