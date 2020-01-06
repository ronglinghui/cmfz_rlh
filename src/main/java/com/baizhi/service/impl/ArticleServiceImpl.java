package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//实现文章的接口
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired//自动注入
    private ArticleDao articleDao;

    //添加
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public Map<String, String> insertArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreate_Date(new Date());
        articleDao.insertArticle(article);
        return null;
    }

    //分页查询
    @Override
    //查询事物
    @AddCache//自定义缓存注解  添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> sybaseArticle(Integer page, Integer row) {
        //获取总条数
        Integer integer = articleDao.selectCountArticle();
        //计算开头数
        Integer start = (page - 1) * row;
        //查询数据
        List<Article> article = articleDao.sybaseArticle(start, row);
        //计算总页数
        Integer totalPage = integer % row == 0 ? integer / row : integer / row + 1;
        /**
         *   计算起始
         *   jqGrid要求这样返回
         *   page: 当前页
         *   rows: 数据
         *   total: 总页数
         *   records: 总条数
         *
         * */
        //创建map集合
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", article);
        map.put("total", totalPage);
        map.put("records", integer);
        return map;
    }

    //修改
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public Map<String, String> updateArticle(Article article) {
        Map<String, String> map = new HashMap<>();
        articleDao.updateArticle(article);
        System.out.println(article.getId());
        return null;
    }

    //删除
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public void deleteArticle(String[] id) {
        articleDao.deleteArticle(id);
    }

    //id查询
    @Override
    @AddCache//自定义缓存注解  添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article selectById(String id) {
        return articleDao.selectById(id);
    }
}
