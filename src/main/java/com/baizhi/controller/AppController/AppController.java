package com.baizhi.controller.AppController;

import com.alibaba.fastjson.JSON;
import com.baizhi.service.appService.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
public class AppController {
    @Autowired
    private AppService appService;

    @RequestMapping("first")
    public String queryAll(String uid,String type,String sub_type){
        Object o = appService.queryAll(uid, type, sub_type);
        String s = JSON.toJSONString(o);
        return s;
    }




}
