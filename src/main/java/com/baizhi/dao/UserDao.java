package com.baizhi.dao;

import com.baizhi.entity.Month;

import java.util.List;

//数据持久化成
public interface UserDao {
    //查询最近七天的数据
    public List<Month> selectBySeven();

    //查询最近12个月的注册人数
    public List<Month> selectMonth();

    //查询用户分布图数据
    public List<Month> selectUser();
}
