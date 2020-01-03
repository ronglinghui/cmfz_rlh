package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//章节的数据持久层
public interface ChapterDao {
    //查询总条数
    public Integer selectCountChapter(String id);

    //分页查询
    public List<Chapter> sybaseChapter(@Param("start") Integer start, @Param("rows") Integer rows, @Param("id") String id);

    //批量删除
    public void deleteChapter(String[] id);

    //修改
    public void updateChapter(Chapter chapter);

    //添加
    public void insertChapter(Chapter chapter);

    //查询全部的方法
    public List<Chapter> selectChapter();
}
