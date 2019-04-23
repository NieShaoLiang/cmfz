package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("insert")
    public Map insert(Article article, MultipartFile file) throws IOException {

        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File("E:/服务器/"+newName));
        article.setImgPath(newName);
        article.setStatus(0);//默认为0
        article.setDate(new Date());//日期为当前日期


        System.out.println(article);
        Map map = new HashMap();
        try {
            articleService.insert(article);
            map.put("isOk",true);
        } catch (Exception e) {
            map.put("isOk",false);

        }
        return map;
    }

    @RequestMapping("selectAll")
    public Map selectAll(int page,int rows){
        Map map = articleService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("delete")
    public void delete(Integer id){
        articleService.delete(id);
    }

    @RequestMapping("update")
    public void update(Article article){
        articleService.update(article);
    }


}
