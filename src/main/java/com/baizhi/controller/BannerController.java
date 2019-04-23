package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;


    @RequestMapping("queryAll")
    @ResponseBody
    public Map queryAll(int page,int rows){
        Map map = bannerService.selectAll(page, rows);
        //System.out.println(map);
        return map;
    }


    @RequestMapping("delete")
    @ResponseBody
    public Map delete(Integer id){
       // System.out.println(id);
        Map map = new HashMap();
        try {
            bannerService.delete(id);
            map.put("isOk",true);
        }catch (Exception e){
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map update(Banner banner){
        //System.out.println("111111111111111"+banner);
        Map map = new HashMap();
        try {
            bannerService.update(banner);
            map.put("isOk",true);
        }catch (Exception e){
            map.put("isOk",false);
        }
        return map;
    }

    @RequestMapping("insert")
    public @ResponseBody Map insert(Banner banner, MultipartFile file) throws Exception {
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));

        System.out.println(oldName);
        file.transferTo(new File("E:/服务器/"+newName));
        banner.setImgPath(newName);

        System.out.println(banner);

        Map map = new HashMap();
        try {
            banner.setCreateDate(new Date());
            banner.setStatus(0);
            bannerService.insert(banner);
            map.put("isOk",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isOk",false);
        }
        return map;
    }

    /*下载excel表*/
    @RequestMapping("downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response){
        Workbook workbook = bannerService.downloadExcel2();
        //设置响应的编码
        String encode =null;
        try {
            encode= URLEncoder.encode("轮播图.xls", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition","attachment;fileName="+encode);
        //设置响应类型
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        //OutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
