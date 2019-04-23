package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileUtil;
import it.sauronsoftware.jave.EncoderException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;


    @RequestMapping("add")
    public Map add(Chapter chapter, MultipartFile file) throws IOException, EncoderException {
        String oldName = file.getOriginalFilename();//获取文件名字
        String uuid = UUID.randomUUID().toString();//获取uuid为新文件名
        String extension = FilenameUtils.getExtension(oldName);//获取文件后缀
        String newName = uuid+"."+extension;//和后缀拼接成新名字
        file.transferTo(new File("E:/服务器/"+newName));//把文件写到本地
        chapter.setId(uuid);//设置id为uuid
        chapter.setPath(newName);//设置文件名称(地址)
        chapter.setPublishDate(new Date());//设置文件上传日期
        chapterService.setDuration(chapter);//获取并设置时长
        chapter.setSize(FileUtil.getPrintSize(file.getSize()));//设置文件大小
        System.out.println(chapter);

        Map map = new HashMap();
        try {
            chapterService.insert(chapter);
            map.put("isOk",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("remove")
    public Map remove(String id){
        Map map = new HashMap();
        try{
            chapterService.delete(id);
            map.put("isOk",true);
        }catch (Exception e){
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("download")
    public void download(String title, String path, HttpServletResponse response) throws IOException {
        //获取文件的路径
        String filePath = "E:/服务器/" + path;
        File file = new File(filePath);
        //修改下载时的名字
        String extension = FilenameUtils.getExtension(path);
        String oldName = title+"."+extension;
        //下载
        //设置响应的编码
        String encode =null;
        try {
            encode= URLEncoder.encode(oldName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition","attachment;fileName="+encode);
        //设置响应类型
        response.setContentType("audio/mpeg");

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
