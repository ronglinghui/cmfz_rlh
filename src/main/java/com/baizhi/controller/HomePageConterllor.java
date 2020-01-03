package com.baizhi.controller;

import com.baizhi.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("home")
public class HomePageConterllor {
    @Autowired
    private HomePageService homePageService;

    @RequestMapping("first_page")
    @ResponseBody
    public Map<String, List<Map<String, String>>> homePage(HttpSession session, HttpServletRequest request, String type) {
        Map<String, List<Map<String, String>>> map = null;
        try {
            map = homePageService.selectHomePage(session, request, type);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("wen")
    @ResponseBody
    public Map<String, Object> wen(HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = null;
        try {
            map = homePageService.selectWenPage(session, request);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }
}
