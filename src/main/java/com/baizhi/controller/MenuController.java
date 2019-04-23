package com.baizhi.controller;

import com.baizhi.entity.Menu;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;


    @RequestMapping("queryAll")
    public String queryAll(Map map){
        List<Menu> list = menuService.selectAllNav();
        for (Menu menu : list) {
            System.out.println(menu);
        }
        map.put("list",list);
        return "/main/main";
    }

    @RequestMapping("queryAll1")
    @ResponseBody
    public Map queryAll1(){
        Map map = new HashMap();
        List<Menu> list = menuService.selectAllNav();
        map.put("list",list);
        return map;
    }

}
