package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

//文章业务层接口
public interface ArticleService {

    //分页查询
    public Map<String, Object> sybaseArticle(Integer start, Integer rows);

    //批量删除
    public void deleteArticle(String[] id);

    //修改
    public Map<String, String> updateArticle(Article article);

    //添加
    public Map<String, String> insertArticle(Article article);

    //通过id查询对象
    public Article selectById(String id);
}
