package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//轮播图持久化层接口
public interface BannerDao {
    //添加
    public void insertBanner(Banner banner);

    //查询总条数
    public Integer selectBannerCount();

    //分页查询
    public List<Banner> sybaseBanner(@Param("page") Integer page, @Param("row") Integer row);

    //修改
    public void updateBanner(Banner banner);

    //删除
    public void deleteBanner(String[] id);

    //查询全部   用于导出数据
    public List<Banner> selectBanner();

}
