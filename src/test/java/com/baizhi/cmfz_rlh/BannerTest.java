package com.baizhi.cmfz_rlh;

import com.baizhi.App;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//在此容器下进行
@RunWith(SpringRunner.class)
//加载入口类
@SpringBootTest(classes = App.class)
public class BannerTest {
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private BannerService bannerService;

    @Test
    public void contextLoads() {
        Banner banner = new Banner("1", "nhihao", "zhang", new Date(), "显示", null);
        bannerDao.insertBanner(banner);
    }

    @Test
    public void contextLoa() {
        Banner banner = new Banner();
        banner.setId("1");
        banner.setStatus("不显示");
        bannerDao.updateBanner(banner);
    }

    @Test
    public void contextLods() {
        Integer integer = bannerDao.selectBannerCount();
        System.out.println(integer);
    }

    @Test
    public void contextLo() {
        List<Banner> banners = bannerDao.sybaseBanner(0, 1);
        System.out.println(banners);
    }

    @Test
    public void contetLo() {
        String[] id = {"2", "3", "4", "5"};
        bannerDao.deleteBanner(id);
        System.out.println("ok");
    }

    @Test
    public void conteto() {
        //bannerService.inExcle();
    }
}