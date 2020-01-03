package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ResponseBody//异步请求
    @RequestMapping("sybaseArticle")
    public Map<String, Object> sybaseBanner(Integer page, Integer rows) {
        Map<String, Object> map = articleService.sybaseArticle(page, rows);
        return map;
    }

    //添加
    @RequestMapping("insertArticle")
    @ResponseBody
    public String insertArticle(Article article) {
        System.out.println("article=====" + article);
        articleService.insertArticle(article);
        return null;

    }

    //修改
    @RequestMapping("updateArticle")
    @ResponseBody
    public String updateArticle(Article article) {
        System.out.println("article=====" + article);
        articleService.updateArticle(article);
        return null;
    }

    //删除
    @RequestMapping("delArticle")
    @ResponseBody
    public String delArticle(String oper, String[] id) {
        System.out.println("article=====" + id.length);
        articleService.deleteArticle(id);
        return null;

    }

    //文件上传
    @ResponseBody
    @RequestMapping("articleFile")//cover是input的id属性   id是数据对像的id
    public Map<String, Object> upload(MultipartFile img, HttpSession session, HttpServletRequest request) throws IOException {
        System.out.println("img+++++========" + img);
        //创建map集合用于返回数据
        Map<String, Object> map = new HashMap<>();
        //通过文件名获取绝对路径
        String realPath = session.getServletContext().getRealPath("/articleupload");
        //通过路径获取文件
        File file = new File(realPath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //创建文件夹
            file.mkdir();
        }
        //获取文件的真实名字
        String filename = img.getOriginalFilename();
        //拼接时间戳
        String newfilename = new Date().getTime() + "_" + filename;
        //上传文件
        img.transferTo(new File(realPath, newfilename));
        /**
         *  回响的样式
         * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
         * {"error":0, "url":"http://localhost:80/cmfz/upload/img/xxx.png" }
         * */
        map.put("error", 0);
        //获取协议  http
        String scheme = request.getScheme();
        //获取IP
        InetAddress localHost = InetAddress.getLocalHost();
        //获取的IP是：PC-20190718ZLAM/192.168.1.156  需要拆分
        String[] split = localHost.toString().split("/");
        String s = split[1];
        //获取端口号 port
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        //拼接路径
        String url = scheme + "://" + s + ":" + serverPort + contextPath + "/articleupload/" + newfilename;
        map.put("url", url);
        return map;
    }

    //图片获取
    @ResponseBody
    @RequestMapping("getAllImgs")
    public Map<String, Object> getAllImgs(HttpSession session, HttpServletRequest request) {
        /**
         *   响应的样式
         * {
         "moveup_dir_path": "",
         "current_dir_path": "",
         "current_url": "/ke4/php/../attached/",
         "total_count": 5,
         "file_list": [
         {
         "is_dir": false,
         "has_file": false,
         "filesize": 208736,
         "dir_path": "",
         "is_photo": true,
         "filetype": "jpg",
         "filename": "1241601537255682809.jpg",
         "datetime": "2018-06-06 00:36:39"
         }
         ]
         }
         * */
        //创建map集合用于响应值
        Map<String, Object> map = new HashMap<>();
        //通过相对路径获取绝对路径
        String realPath = session.getServletContext().getRealPath("articleupload");
        File file = new File(realPath);
        //创建list集合
        List<Object> list = new ArrayList<>();
        //获取文中的所有图片
        String[] imgs = file.list();
        //遍历图片数据
        for (String img : imgs) {
            //创建最小一层的map集合  存储数据
            Map<String, Object> hashMap = new HashMap<>();
            //是否是文件夹
            hashMap.put("is_dir", false);
            //里面是否还有文件
            hashMap.put("has_file", false);
            //获取图片的大小
            int length = img.length();
            hashMap.put("filesize", length);
            hashMap.put("dir_path", "");
            //是否是文件
            hashMap.put("is_photo", true);
            //图片的类型  MIME类型
            String extension = FilenameUtils.getExtension(img);
            hashMap.put("filetype", extension);
            //图片名
            hashMap.put("filename", img);
            //上传时间
            String[] s = img.split("_");
            //获取long
            Long aLong = Long.valueOf(s[0]);
            //通过long创建时间
            Date date = new Date(aLong);
            //转换时间格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(date);
            hashMap.put("datetime", format);
            //添加到list集合中
            list.add(hashMap);
        }
        //添加图片属性
        map.put("file_list", list);
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");

        try {
            //图片路径
            //获取协议 http
            String scheme = request.getScheme();
            //获取IP: PC-20190718ZLAM/192.168.1.156  需要拆分
            String[] split = InetAddress.getLocalHost().toString().split("/");
            String s = split[1];
            //端口号
            int port = request.getServerPort();
            //项目名
            String contextPath = request.getContextPath();
            //拼接路径
            String url = scheme + "://" + s + ":" + port + contextPath + "/articleupload/";
            map.put("current_url", url);
            //图片张数
            map.put("total_count", imgs.length);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("imgs==" + imgs.length);

        return map;
    }
}
