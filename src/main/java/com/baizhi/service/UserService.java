package com.baizhi.service;

import java.util.List;
import java.util.Map;

//业务层  用户
public interface UserService {
    //查询最近七天的数据
    public Map<Integer, List<Object>> selectBySeven();

    //查询最近12个月的注册人数
    public Map<Integer, List<Object>> selectMonth();

    //查询用户分布图数据
    public List<Map<String, Object>> selectUser();
}
