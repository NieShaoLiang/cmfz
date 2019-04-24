package com.baizhi.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import com.baizhi.util.MsgUtils;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        //System.out.println(oldName);
        file.transferTo(new File("E:/服务器/"+newName));
        user.setHeadImg(newName);


        //使用GoEasy实现socket实时通信
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


    //登录
    @RequestMapping("login")
    public Map login(String name,String password,String code,HttpSession session){
        System.out.println(name+"======="+password+"========"+code);
        Map map = new HashMap();
        String code1 = (String) session.getAttribute("code");
        System.out.println(code1);
        if (!code.equalsIgnoreCase(code1)) {
            map.put("info","验证码错误!");
            map.put("isOk",false);
            return map;
        }
        User user = userService.selectByNamePwd(name, password);
        if(user==null){
            map.put("isOk",false);
            map.put("info","用户名或密码错误!");
            return map;
        }else {
            map.put("isOk",true);
            map.put("info","登陆成功!");
            session.setAttribute("user",user);
            return map;
        }
    }

    //获取图形验证码
    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100,4,20);
        //获取验证码存入session
        session.setAttribute("code",lineCaptcha.getCode());
        //图形验证码通过流的形式响应到页面
        ServletOutputStream os = response.getOutputStream();
        lineCaptcha.write(os);
    }

    //注册
    @RequestMapping("reg")
    public Map reg(User user,String code,HttpSession session){
        Map map = new HashMap();
        String code1 = (String) session.getAttribute("code");
        if(!code.equalsIgnoreCase(code)){
            map.put("error","验证码错误");
            return map;
        }
        if (userService.selectByName(user.getName())==null){
            map.put("error","用户名已存在");
            return map;
        }else {
            userService.insert(user);
            map.put("info","注册成功");
            return map;
        }
    }

    //发送验证码
    @RequestMapping("sendMsg")
    public void sendMsg(String phone,HttpSession session) throws ClientException {
        int i = MsgUtils.sendMsg(phone,session);
        System.out.println("===========================================发送验证码："+i);
    }

}
