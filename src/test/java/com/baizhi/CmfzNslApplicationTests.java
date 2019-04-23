package com.baizhi;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDTODao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzNslApplicationTests {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDTODao userDTODao;


    @Test
    public void contextLoads() {

        RowBounds rowBounds = new RowBounds(2, 2);
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), rowBounds);
        for (Banner banner : banners) {
            System.out.println(banner);
        }

    }


    @Test
    public void t2() {
        Map map = userService.selectActiveNumber();
        System.out.println(map);
        //hahahahaha
    }

}
