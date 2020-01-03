package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//实现业务层接口
@Service//利用工厂创建对象
@Transactional//事物相关的注解
public class AdminServiceImpl implements AdminService {
    //注入持久层的对象
    @Autowired
    private AdminDao adminDao;

    //查询事物相关的注解
    @Transactional(propagation = Propagation.SUPPORTS)
    public String loginAdmin(String username, String password) {
        //通过用户查询出用户
        Admin admin = adminDao.loginAdmin(username);
        //判断用户是否为空
        if (admin == null) {
            return "用户不存在";
        }
        //判断密码是否正确
        if (!admin.getPassword().equals(password)) {
            return "密码错误";
        }
        return null;
    }
}
