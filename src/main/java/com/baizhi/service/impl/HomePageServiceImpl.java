package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

//首页的事物层
@Service
@Transactional
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private BannerDao bannerDao;//轮播图
    @Autowired
    private AlbumDao albumDao;//专辑
    @Autowired
    private ArticleDao articleDao;//上师文章
    @Autowired
    private ChapterDao chapterDao;

    //首页数据
    @Transactional(propagation = Propagation.SUPPORTS)//查询的事物
    @AddCache//自定义缓存注解  添加缓存
    public Map<String, List<Map<String, String>>> selectHomePage(HttpSession session, HttpServletRequest request, String type) throws UnknownHostException {
        //创建集合
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        //调用查询轮播图方法
        List<Banner> banners = bannerDao.selectBanner();
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
        String url = scheme + "://" + s + ":" + serverPort + contextPath + "/upload/";
        //调用查询文章的方法
        List<Article> articles = articleDao.selectArticle();
        //调用专辑的方法
        List<Album> albums = albumDao.selectAlbum();

        //创建添加轮播图的list集合
        List<Map<String, String>> list = new ArrayList<>();
        //遍历轮播图
        for (Banner banner : banners) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("thumbnail", url + banner.getImg());//图片路径
            map1.put("desc", banner.getTitle());//头像描述
            map1.put("id", banner.getId());//主键
            //添加到轮播图的list集合中
            list.add(map1);
        }

        //创建专辑和文章的集合
        List<Map<String, String>> list1 = new ArrayList<>();
        //遍历专辑
        for (Album album : albums) {
            //创建储存专辑的集合
            Map<String, String> map1 = new HashMap<>();
            map1.put("thumbnail", url + album.getImg());//图片路径
            map1.put("title", album.getTitle());//名字
            map1.put("author", "");//描述
            map1.put("type", "0");//类型（0：闻，1：思）
            map1.put("set_count", album.getCount().toString());//集数
            map1.put("create_date", album.getCreate_Date().toString());//时间（0：闻，1：思）
            list1.add(map1);
        }

        //遍历文章
        for (Article article : articles) {
            //创建储存文章的集合
            Map<String, String> map1 = new HashMap<>();
            map1.put("thumbnail", "");//图片路径
            map1.put("title", article.getTitle());//名字
            map1.put("author", article.getAuthor());//描述
            map1.put("type", "1");//类型（0：闻，1：思）
            map1.put("set_count", "");//集数
            map1.put("create_date", new Date().toString());//时间（0：闻，1：思）
            list1.add(map1);
        }
        //往map中添加
        if ("all".equals(type)) {
            map.put("head", list);//轮播图
        }
        map.put("body", list1);//文章和专辑
        return map;
    }

    //闻的详情
    @Override
    @AddCache//自定义缓存注解  添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectWenPage(HttpSession session, HttpServletRequest request) throws UnknownHostException {
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
        String url = scheme + "://" + s + ":" + serverPort + contextPath + "/upload/";
        //创建集合
        Map<String, Object> map = new HashMap<>();
        //调用专辑的方法
        List<Album> albums = albumDao.selectAlbum();
        //章节
        List<Chapter> chapters = chapterDao.selectChapter();
        //创建专辑的map
        Map<String, String> map1 = new HashMap<>();
        for (Album album : albums) {
            map1.put("thumbnail", url + album.getImg());
            map1.put("title", album.getTitle());
            map1.put("score", album.getScore());
            map1.put("author", album.getAuthor());
            map1.put("broadcast", album.getBroadcaster());
            map1.put("set_count", album.getCount().toString());
            map1.put("brief", album.getBrief());
            map1.put("create_date", album.getCreate_Date().toString());
        }
        map.put("introduction", map1);
        //创建章节的map
        //Map<String,String> map2 = new HashMap<>();
        //创建专辑和文章的集合
        List<Map<String, String>> list1 = new ArrayList<>();
        //拼接路径章节
        String url1 = scheme + "://" + s + ":" + serverPort + contextPath + "/albumFile/";
        for (Chapter chapter : chapters) {
            Map<String, String> map3 = new HashMap<>();
            map1.put("title", url + chapter.getTitle());
            map1.put("download_url", url1 + chapter.getSrc());
            map1.put("size", chapter.getSize());
            map1.put("duration", chapter.getDuration());
            list1.add(map1);
        }
        map.put("list", list1);
        return map;
    }


}
