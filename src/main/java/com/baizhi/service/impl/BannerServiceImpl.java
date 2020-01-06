package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired//自动注入
    private BannerDao bannerDao;

    //添加
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public Map<String, String> insertBanner(Banner banner) {
        //创建map集合
        Map<String, String> map = new HashMap<>();
        String img = banner.getImg();
        //拆分图片路径
        String[] split = img.split("\\\\");
        //获取最后一个值
        String s = split[split.length - 1];
        //重新赋值
        banner.setImg(s);
        //创建id
        banner.setId(UUID.randomUUID().toString());
        //添加创建时间
        banner.setCreate_Date(new Date());
        bannerDao.insertBanner(banner);
        //添加状态和id名
        map.put("status", "200");
        map.put("bannerId", banner.getId());
        return map;
    }

    //分页查询
    @Override
    //查询事物
    @AddCache//自定义缓存注解  添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> sybaseBanner(Integer page, Integer row) {
        //获取总条数
        Integer integer = bannerDao.selectBannerCount();
        //计算开头数
        Integer start = (page - 1) * row;
        //查询数据
        List<Banner> banners = bannerDao.sybaseBanner(start, row);
        //计算总页数
        Integer totalPage = integer % row == 0 ? integer / row : integer / row + 1;
        /**
         *   计算起始
         *   jqGrid要求这样返回
         *   page: 当前页
         *   rows: 数据
         *   total: 总页数
         *   records: 总条数
         *
         * */
        //创建map集合
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", banners);
        map.put("total", totalPage);
        map.put("records", integer);
        return map;
    }

    //修改
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public Map<String, String> updateBanner(Banner banner) {
        System.out.println("service=====" + banner.getId());
        //创建map集合
        Map<String, String> map = new HashMap<>();
        String img = banner.getImg();
        //拆分图片路径
        String[] split = img.split("\\\\");
        //获取最后一个值
        String s = split[split.length - 1];
        //重新赋值
        if (banner.getImg().equals("")) {
            banner.setImg(null);
            banner.setCreate_Date(null);
        } else {
            banner.setCreate_Date(new Date());
            banner.setImg(s);
        }
        bannerDao.updateBanner(banner);
        map.put("bannerId", banner.getId());
        map.put("status", "200");
        return map;
    }

    //删除
    @Override
    @ClearCache//自定义缓存注解  删除缓存
    public void deleteBanner(String[] id) {
        bannerDao.deleteBanner(id);
    }

    //查询全部   用于导出数据
    @Override
    @AddCache//自定义缓存注解  添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public void easyPoiOutBanner(HttpServletResponse response, HttpSession session) {
        //获取数据库的数据
        List<Banner> banners = bannerDao.selectBanner();
        //获取绝对路径
        String realPath = session.getServletContext().getRealPath("/upload");
        //给数据库中的图片设置绝对路径
        for (Banner banner : banners) {
            //设置图片的路径
            banner.setImg(realPath + "/" + banner.getImg());
        }
        try {
            //查询数据  创建工作部
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图管理", "轮播图"),
                    Banner.class, banners);
            //設置响应头
            response.setHeader("content-disposition", "attachment;filename=banner.xls");
            //获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //通过流往外输出
            workbook.write(outputStream);
            outputStream.close();
            //关流
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //添加excle数据
    @ClearCache//自定义缓存注解  删除缓存
    public void inExcle(MultipartFile file, HttpSession session) {
        //导入的参数   用于设置
        ImportParams params = new ImportParams();
        params.setTitleRows(1);   //标题行
        params.setHeadRows(1);    //表头
        try {
            //参数一：获取的文件流   参数二：是实体类的类型 参数三：参数的样式
            List<Banner> list = ExcelImportUtil.importExcel(file.getInputStream(),
                    Banner.class, params);
            /*ExcelImportResult<Banner> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Banner.class, params);
            List<Banner> list = result.getList();*/
            System.out.println(list);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
