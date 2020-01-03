package com.baizhi.service;

import com.baizhi.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

//业务层
public interface BannerService {
    //添加
    public Map<String, String> insertBanner(Banner banner);

    //分页查询
    public Map<String, Object> sybaseBanner(Integer page, Integer row);

    //修改
    public Map<String, String> updateBanner(Banner banner);

    //删除
    public void deleteBanner(String[] id);

    //查询全部   用于导出数据
    public void easyPoiOutBanner(HttpServletResponse response, HttpSession session);

    //导入
    public void inExcle(MultipartFile file, HttpSession session);
}
