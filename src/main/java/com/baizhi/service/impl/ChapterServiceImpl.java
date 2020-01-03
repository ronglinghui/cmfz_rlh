package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//实现章节的接口
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired//自动注入
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;

    //添加
    @Override
    public Map<String, String> insertChapter(Chapter chapter) {
        //创建map集合
        Map<String, String> map = new HashMap<>();
        String img = chapter.getSrc();
        //拆分图片路径
        String[] split = img.split("\\\\");
        //获取最后一个值
        String s = split[split.length - 1];
        //重新赋值
        chapter.setSrc(s);
        //创建id
        chapter.setId(UUID.randomUUID().toString());
        //这里计算专辑的集数
        Album album = albumDao.selectById(chapter.getAlbum_Id());
        System.out.println("album===" + album);
        album.setCount(album.getCount() + 1);
        albumDao.updateAlbum(album);

        //调用方法
        chapterDao.insertChapter(chapter);
        //添加状态和id名
        map.put("status", "200");
        map.put("bannerId", chapter.getId());
        return map;
    }

    //分页查询
    @Override
    //查询事物
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> sybaseChapter(Integer page, Integer row, String id) {
        //获取总条数
        Integer integer = chapterDao.selectCountChapter(id);
        //计算开头数
        Integer start = (page - 1) * row;
        //查询数据
        List<Chapter> chapters = chapterDao.sybaseChapter(start, row, id);
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
        map.put("rows", chapters);
        map.put("total", totalPage);
        map.put("records", integer);
        return map;
    }

    //修改
    @Override
    public Map<String, String> updateChapter(Chapter chapter) {
        System.out.println("service=====" + chapter.getId());
        System.out.println("charpter===='" + chapter);
        //创建map集合
        Map<String, String> map = new HashMap<>();

        String img = chapter.getSrc();
        String s = null;
        if (img != null) {
            //拆分图片路径
            String[] split = img.split("\\\\");
            //获取最后一个值
            s = split[split.length - 1];
        }


        //重新赋值
        if ("".equals(chapter.getSrc())) {

            chapter.setSrc(null);
        } else {
            chapter.setSrc(s);
        }
        chapterDao.updateChapter(chapter);
        map.put("bannerId", chapter.getId());
        map.put("status", "200");
        return map;
    }

    //删除
    @Override
    public void deleteChapter(String[] id, String aid) {

        //这里计算专辑的集数
        Album album = albumDao.selectById(aid);
        System.out.println("album===" + album);
        album.setCount(album.getCount() - id.length);
        albumDao.updateAlbum(album);
        chapterDao.deleteChapter(id);
    }
}
