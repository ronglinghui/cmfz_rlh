package com.baizhi.dao;

import com.baizhi.entity.Admin;

//数据持久化成
public interface AdminDao {
    //登录
    public Admin loginAdmin(String username);
}
