package com.baizhi.controller;

import com.baizhi.entity.Master;
import com.baizhi.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("master")
public class MasterController {
    @Autowired
    private MasterService masterService;


    @RequestMapping("queryAll")
    public List queryAll(){
        return masterService.selectAll();
    }

    @RequestMapping("queryByPage")
    public Map queryByPage(int page,int rows){
        return masterService.queryByPage(page,rows);
    }

    @RequestMapping("insert")
    public void insert(Master master, MultipartFile file) throws IOException {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File("E:/服务器/"+newName));
        master.setHeadImg(newName);
        master.setStatus(0);
        System.out.println("================================================="+master);
        masterService.insert(master);
    }

    @RequestMapping("delete")
    public void delete(Integer id){
        masterService.delete(id);
    }

    @RequestMapping("update")
    public void update(Master master){
        masterService.update(master);
    }



}
