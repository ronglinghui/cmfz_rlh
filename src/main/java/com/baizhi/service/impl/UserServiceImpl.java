package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//实现业务层接口
@Service//利用工厂创建对象
@Transactional//事物相关的注解
public class UserServiceImpl implements UserService {
    //自动注入
    @Autowired
    private UserDao userDao;

    //最近七天
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<Integer, List<Object>> selectBySeven() {
        Map<Integer, List<Object>> map = new HashMap<>();
        List<Month> months = userDao.selectBySeven();
        //时间集合
        List<Object> count = new ArrayList<>();
        //人数集合
        List<Object> month = new ArrayList<>();

        //赋值
        for (Month mnth : months) {
            count.add(mnth.getCount());
            month.add(mnth.getMonth());
        }
        map.put(1, count);
        map.put(2, month);
        return map;
    }

    //最近12个月
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<Integer, List<Object>> selectMonth() {
        Map<Integer, List<Object>> map = new HashMap<>();
        List<Month> monhs = userDao.selectMonth();
        //时间集合
        List<Object> coun = new ArrayList<>();
        //人数集合
        List<Object> mont = new ArrayList<>();

        //赋值
        for (Month monh : monhs) {
            coun.add(monh.getCount());
            mont.add(monh.getMonth());
        }
        map.put(0, coun);
        map.put(1, mont);
        return map;
    }

    //总数
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Map<String, Object>> selectUser() {
        //调用方法
        List<Month> months = userDao.selectUser();
        //创建list集合
        List<Map<String, Object>> list = new ArrayList<>();
        //赋值
        for (Month month : months) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", month.getAddress());
            map.put("value", month.getCount());
            list.add(map);
        }
        return list;
    }
}
