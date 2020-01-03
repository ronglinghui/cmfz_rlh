package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//控制层
@Controller//有工厂创建
@RequestMapping("admin")//包名
@ResponseBody//声明此方法下都是异步的
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录方法
    @RequestMapping("loginAdmin")
    public String loginAdmin(String username, String password, String code, HttpSession session) {
        //获取内部的验证码
        System.out.println(username);
        String code1 = (String) session.getAttribute("code");
        System.out.println("内部" + code1);
        System.out.println("外部" + code);
        //判断验证码是否正确
        if (code1.equals(code)) {
            System.out.println("验证码正确");
            //调用方法
            String s = adminService.loginAdmin(username, password);
            //返回信息
            return s;
        } else {
            System.out.println("验证码错误！");
            return "验证码错误";
        }
    }
}
