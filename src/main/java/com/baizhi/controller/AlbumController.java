package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @ResponseBody//异步请求
    @RequestMapping("sybaseAlbum")
    public Map<String, Object> sybaseBanner(Integer page, Integer rows) {
        Map<String, Object> map = albumService.sybaseAlbum(page, rows);
        return map;
    }

    @RequestMapping("zong")
    @ResponseBody
    public Map<String, String> zong(String oper, Album album, String[] id) {
        System.out.println(album.getCreate_Date());
        //创建map集合
        Map<String, String> map = new HashMap<>();
        //判断页面的请求是
        if ("add".equals(oper)) {
            map = albumService.insertAlbum(album);
            return map;
        }
        if ("edit".equals(oper)) {
            map = albumService.updateAlbum(album);
            return map;
        }
        if ("del".equals(oper)) {
            albumService.deleteAlbum(id);
            return map;
        }
        return null;

    }

    //文件上传
    @ResponseBody
    @RequestMapping("albumFile")//cover是input的id属性   id是数据对像的id
    public void upload(MultipartFile img, String id, HttpSession session) throws IOException {
        System.out.println("id--------" + id);
        System.out.println("img+++++========" + img);

        //判断文件是否存在
        File file1 = new File(session.getServletContext().getRealPath("/albumFile"), img.getOriginalFilename());
        //文件不存在
        if (!file1.exists()) {
            System.out.println("上传文件了");
            //上传文件
            img.transferTo(file1);

            /*//根据id修改数据库中图片的路径
            Banner banner = new Banner();
            banner.setId(id);
            //cover.getOriginalFilename() 这是获取文件的
            banner.setImg(img.getOriginalFilename());
            albumService.updateAlbum(albumService);*/
        }


    }
}
