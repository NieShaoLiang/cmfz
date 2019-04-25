package com.baizhi.service.appService;

import com.baizhi.dao.*;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*与前台接口对接*/
@Service("appService")
public class AppService {
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private UserService userService;


    /*app一级页面 type值为all是首页，wen是专辑模块，si是文章模块*/
    public Object queryAll(String uid,String type,String sub_type){
        if (uid==null||type==null){
            return new Error("参数不能为空");
        }else {
            if (type.equals("all")){
                Map<String,Object> map = new HashMap<>();
                map.put("banner",bannerDao.selectByStatus());//添加轮播图集合(状态是1的)
                map.put("album",albumDao.selectNew());//添加专辑集合
                map.put("article",articleDao.selectAll());//添加文章集合
                return map;
            }else if (type.equals("wen")){
                Map<String,Object> map = new HashMap<>();
                map.put("album",albumDao.selectAll());
                return map;
            }else {
                if (sub_type==null){
                    return new Error("思类型为空未查到数据");
                }else {
                    Integer masterId = userDao.selectMasterId(uid);//获取上师id
                    Map<String,Object> map = new HashMap<>();
                    if (sub_type.equals("ssyj")){
                        map.put("article",articleDao.selectByMasterId(masterId));//map添加当前用户订阅的上师发表的文章集合
                        return map;
                    }else {
                        map.put("article",articleDao.selectExceptMasterId(masterId));//排除用户订阅的上师，其他上师发表的文章
                        return map;
                    }
                }
            }
        }
    }

    /*思的详情页接口   文章查详情*/
    public Object articleDetail(Integer id,Integer uid){
        if(uid==null||id==null){
            return new Error("参数不能为空");
        }else{
            Map map = new HashMap();
            Article article = articleDao.selectByPrimaryKey(id);
            map.put("link","http://xxx/1000.html");
            map.put("id",id);
            map.put("ext",article);
            return map;
        }
    }

    /*闻的详情页接口*/
    public Object albumDetail(Integer id,Integer uid){
        if(id==null||uid==null){
            return new Error("参数不能为空");
        }else {
            Map map = new HashMap();
            Album album = albumDao.selectByPrimaryKey(id);
            map.put("introduction",album);
            map.put("list",chapterDao.selectByAlbumId(id));
            return map;
        }
    }

    /*登录接口*/
    public Object login(String phone,String pwd,String code){
        if(pwd==null&&code==null){
            return new Error("参数不能为空");
        }else {
            Map map = new HashMap();
            User user = userService.selectByNamePwd(phone, pwd);
            if(user==null){
                map.put("error","-200");
                map.put("errmsg","密码错误");
                return map;
            }else {
                return user;
            }
        }
    }

    /*注册接口*/
    public Object regist(String phone,String pwd){
        User user = userService.selectByName(phone);
        Map map = new HashMap();
        if (user != null) {
            map.put("error","-200");
            map.put("error_msg","该手机号已存在");
            return map;
        }else {
            User user1 = new User();
            user1.setPhone(phone);
            userService.insert(user1);
            User user2 = userDao.selectByPhone(phone);
            Integer uid = user2.getId();
            String password = user2.getPassword();
            map.put("uid",uid);
            map.put("phone",phone);
            map.put("password",password);
            return map;
        }
    }

    /*修改个人信息接口*/
    public Object changeUser(User user){
        Map map = new HashMap();
        if(user.getId()==null){
            map.put("error","-200");
            map.put("error_msg","该手机号已经存在");
            return map;
        }else {
            userService.update(user);
            User user1 = userDao.selectByPrimaryKey(user.getId());
            return user1;
        }
    }

    /*获取短信验证码接口*/
    /* 短信验证码校验接口*/

    /* 获取会员列表*/
    public Object queryUser(Integer uid){
        if(uid==null){
            return new Error("参数为空");
        }else{
            User user = userDao.selectByPrimaryKey(uid);
            return user;
        }
    }


}
