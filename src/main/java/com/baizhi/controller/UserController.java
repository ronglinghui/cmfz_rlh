package com.baizhi.controller;

import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

//控制层
@Controller//有工厂创建
@RequestMapping("user")//包名
public class UserController {
    @Autowired
    private UserService userService;

    //最近七天
    @RequestMapping("selectBySeven")
    @ResponseBody
    public Map<Integer, List<Object>> selectBySeven() {
        System.out.println("=====-0=0=0=0=0");
        Map<Integer, List<Object>> integerListMap = userService.selectBySeven();
        return integerListMap;
    }

    //最近12个月
    @RequestMapping("selectMonth")
    @ResponseBody
    public Map<Integer, List<Object>> selectMonth() {
        Map<Integer, List<Object>> integerListMap = userService.selectMonth();
        return integerListMap;
    }

    //全部
    @RequestMapping("selectUser")
    @ResponseBody
    public List<Map<String, Object>> selectUser() {
        List<Map<String, Object>> list = userService.selectUser();
        return list;
    }
}
