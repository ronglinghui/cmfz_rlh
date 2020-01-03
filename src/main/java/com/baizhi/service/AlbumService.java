package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

//专辑业务层接口
public interface AlbumService {
    //添加
    public Map<String, String> insertAlbum(Album album);

    //分页查询
    public Map<String, Object> sybaseAlbum(Integer page, Integer row);

    //修改
    public Map<String, String> updateAlbum(Album album);

    //删除
    public void deleteAlbum(String[] id);

    //通过id查询对象
    public Album selectById(String id);
}
