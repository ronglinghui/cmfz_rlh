package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//专辑的数据持久层接口
public interface AlbumDao {
    //查询总条数
    public Integer selectCountAlbum();

    //分页查询
    public List<Album> sybaseAlbum(@Param("start") Integer start, @Param("rows") Integer rows);

    //批量删除
    public void deleteAlbum(String[] id);

    //修改
    public void updateAlbum(Album album);

    //添加
    public void insertAlbum(Album album);

    //通过id查询对象
    public Album selectById(String id);

    //查询全部的方法
    public List<Album> selectAlbum();
}
