package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

//章节业务层接口
public interface ChapterService {
    //添加
    public Map<String, String> insertChapter(Chapter chapter);

    //分页查询
    public Map<String, Object> sybaseChapter(Integer page, Integer row, String id);

    //修改
    public Map<String, String> updateChapter(Chapter chapter);

    //删除
    public void deleteChapter(String[] id, String aid);
}
