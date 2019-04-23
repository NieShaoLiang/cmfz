package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /*查所有*/
    @RequestMapping("queryAll")
    public List queryAll(){
        List<Album> list = albumService.queryAll();
        for (Album album : list) {
            System.out.println(album);
        }
        return list;
    }

    /*分页*/
    @RequestMapping("queryByPage")
    public Map queryByPage(int page,int rows){
        Map map = albumService.selectAll(page,rows);
        System.out.println(map);
        return map;
    }


    @RequestMapping("insert")
    public Map insert(Album album, MultipartFile file) throws IOException {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));

        System.out.println(oldName);
        file.transferTo(new File("E:/服务器/"+newName));
        //session.setAttribute("img","E:/服务器/"+newName);
        album.setImgPath(newName);

        System.out.println(album);

        Map map = new HashMap();
        try {
            album.setPublishDate(new Date());
            album.setScore(5.0);
            album.setAmount(0);
            albumService.insert(album);
            map.put("isOk",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("delete")
    public Map delete(Integer id){
        System.out.println("删除"+id);
        Map map = new HashMap();
        try{
            albumService.delete(id);
            map.put("isOk",true);
        }catch (Exception e){
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("selectOne")
    public Album selectOne(Integer id){
        System.out.println("查详情:"+id);
        Album album = albumService.selectOne(id);
        return album;
    }

    @RequestMapping("update")
    public Map update(Album album){
        System.out.println(album);
        Map map = new HashMap();
        try {
            albumService.update(album);
            map.put("isOk",true);
        }catch (Exception e){
            map.put("isOk",false);
        }
        return map;
    }
}
