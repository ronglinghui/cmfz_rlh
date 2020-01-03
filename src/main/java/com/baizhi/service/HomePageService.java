package com.baizhi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

//首页的事物层
public interface HomePageService {
    //首页数据
    public Map<String, List<Map<String, String>>> selectHomePage(HttpSession session, HttpServletRequest request, String type) throws UnknownHostException;

    public Map<String, Object> selectWenPage(HttpSession session, HttpServletRequest request) throws UnknownHostException;
}
