package com.baizhi.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

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
    public Map reg(User user,String code){
        Map map = new HashMap();
        Jedis jedis = new Jedis("localhost");
        String code1 = jedis.get("user:phone:"+user.getPhone());//获取正确的验证码
        if(!code.equalsIgnoreCase(code1)){
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
    public void sendMsg(String phone) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIgMBmSXEZn92j";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "3ybLrjCrt3O2tRbRe5L60xOVgyeWuA";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("云通信");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_164100021");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        String code = "";
        Random rd = new Random();
        for (int i = 0; i < 4; i++) {
            code=code+rd.nextInt(10);
        }
        request.setTemplateParam("{\"code\":"+code+"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
        //请求成功
            /*将验证码存入redis*/
            Jedis jedis = new Jedis("localhost");//创建jedis对象
            jedis.set("user:phone:"+phone,code);//把验证码存入redis缓存
            jedis.expire("user:phone:"+phone,300);//设置失效时间为5分钟
        }
    }

}
