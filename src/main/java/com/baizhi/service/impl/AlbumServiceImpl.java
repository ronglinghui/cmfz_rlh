package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//实现专辑的接口
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired//自动注入
    private AlbumDao albumDao;

    //添加
    @Override
    public Map<String, String> insertAlbum(Album album) {
        //创建map集合
        Map<String, String> map = new HashMap<>();
        String img = album.getImg();
        //拆分图片路径
        String[] split = img.split("\\\\");
        //获取最后一个值
        String s = split[split.length - 1];
        //重新赋值
        album.setImg(s);
        //创建id
        album.setId(UUID.randomUUID().toString());
        //添加创建时间
        album.setCreate_Date(new Date());
        //调用方法
        albumDao.insertAlbum(album);
        //添加状态和id名
        map.put("status", "200");
        map.put("bannerId", album.getId());
        return map;
    }

    //分页查询
    @Override
    //查询事物
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> sybaseAlbum(Integer page, Integer row) {
        //获取总条数
        Integer integer = albumDao.selectCountAlbum();
        //计算开头数
        Integer start = (page - 1) * row;
        //查询数据
        List<Album> albums = albumDao.sybaseAlbum(start, row);
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
        map.put("rows", albums);
        map.put("total", totalPage);
        map.put("records", integer);
        return map;
    }

    //修改
    @Override
    public Map<String, String> updateAlbum(Album album) {
        System.out.println("service=====" + album.getId());
        //创建map集合
        Map<String, String> map = new HashMap<>();
        String img = album.getImg();
        //拆分图片路径
        String[] split = img.split("\\\\");
        //获取最后一个值
        String s = split[split.length - 1];
        //重新赋值
        if (album.getImg().equals("")) {
            album.setCreate_Date(null);
            album.setImg(null);
        } else {
            album.setCreate_Date(new Date());
            album.setImg(s);
        }
        albumDao.updateAlbum(album);
        map.put("bannerId", album.getId());
        map.put("status", "200");
        return map;
    }

    //删除
    @Override
    public void deleteAlbum(String[] id) {
        albumDao.deleteAlbum(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album selectById(String id) {
        return albumDao.selectById(id);
    }
}
