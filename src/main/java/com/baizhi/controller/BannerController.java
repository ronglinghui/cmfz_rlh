package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ResponseBody//异步请求
    @RequestMapping("sybaseBanner")
    public Map<String, Object> sybaseBanner(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.sybaseBanner(page, rows);
        return map;
    }

    @RequestMapping("zong")
    @ResponseBody
    public Map<String, String> zong(String oper, Banner banner, String[] id) {
        //创建map集合
        Map<String, String> map = new HashMap<>();
        //判断页面的请求是
        if ("add".equals(oper)) {
            map = bannerService.insertBanner(banner);
            return map;
        }
        if ("edit".equals(oper)) {
            map = bannerService.updateBanner(banner);
            return map;
        }
        if ("del".equals(oper)) {
            bannerService.deleteBanner(id);
            return map;
        }
        return null;

    }

    //文件上传
    @ResponseBody
    @RequestMapping("upload1")//cover是input的id属性   id是数据对像的id
    public void upload(MultipartFile img, String id, HttpSession session) throws IOException {
        System.out.println("id--------" + id);
        System.out.println("img+++++========" + img);

        //判断文件是否存在
        File file1 = new File(session.getServletContext().getRealPath("/upload"), img.getOriginalFilename());
        //文件不存在
        if (!file1.exists()) {
            System.out.println("上传文件了");
            //上传文件
            img.transferTo(file1);

            //根据id修改数据库中图片的路径
            Banner banner = new Banner();
            banner.setId(id);
            //cover.getOriginalFilename() 这是获取文件的
            banner.setImg(img.getOriginalFilename());
            bannerService.updateBanner(banner);
        }
    }

    //导出excle表格
    @RequestMapping("excle")
    public void excle(HttpSession session, HttpServletResponse response) {
        //调用导入的方法
        bannerService.easyPoiOutBanner(response, session);
    }

    //导入excle表格
    @RequestMapping("inExcle")
    //@ResponseBody
    public String inExcle(MultipartFile file, HttpSession session) {
        System.out.println("====" + file);
        bannerService.inExcle(file, session);
        return "jsp/banner";
    }
}
