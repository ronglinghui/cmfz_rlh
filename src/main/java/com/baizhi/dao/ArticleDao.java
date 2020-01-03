package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//文章的数据持久层接口
public interface ArticleDao {
    //查询总条数
    public Integer selectCountArticle();

    //分页查询
    public List<Article> sybaseArticle(@Param("start") Integer start, @Param("rows") Integer rows);

    //批量删除
    public void deleteArticle(String[] id);

    //修改
    public void updateArticle(Article article);

    //添加
    public void insertArticle(Article article);

    //通过id查询对象
    public Article selectById(String id);

    //查询全部
    public List<Article> selectArticle();
}
