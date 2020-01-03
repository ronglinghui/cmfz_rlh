package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//章节控制层
@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ResponseBody//异步请求
    @RequestMapping("sybaseChapter")
    public Map<String, Object> sybaseBanner(Integer page, Integer rows, String id) {
        Map<String, Object> map = chapterService.sybaseChapter(page, rows, id);
        return map;
    }

    @RequestMapping("zong")
    @ResponseBody
    public Map<String, String> zong(String oper, Chapter chapter, String[] id) {
        //创建map集合
        Map<String, String> map = new HashMap<>();
        //判断页面的请求是
        if ("add".equals(oper)) {
            System.out.println("-=-=-=-" + chapter);
            map = chapterService.insertChapter(chapter);

            return map;
        }
        if ("edit".equals(oper)) {
            map = chapterService.updateChapter(chapter);
            return map;
        }
        if ("del".equals(oper)) {
            chapterService.deleteChapter(id, chapter.getAlbum_Id());
            return map;
        }
        return null;

    }

    //文件上传
    @ResponseBody
    @RequestMapping("up")//
    public void upload(MultipartFile src, String id, String id1, HttpSession session) throws IOException {
        System.out.println("id--------" + id);
        System.out.println("img+++++========" + src);
        //判断文件是否存在
        File file1 = new File(session.getServletContext().getRealPath("/albumFile"), src.getOriginalFilename());
        //文件不存在
        if (!file1.exists()) {
            System.out.println("上传文件了");
            //上传文件
            src.transferTo(file1);
        }
        /*获取文件时长*/
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(file1);  //这里传入的是文件对象
            ls = m.getDuration() / 1000;  //得到一个long类型的时长

        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        //创建对象
        Chapter chapter = new Chapter();
        //设置id
        chapter.setId(id);
        //设置专辑id
        System.out.println("id1==" + id1);
        if (!"".equals(id1)) {
            System.out.println("fater=====");
            chapter.setAlbum_Id(id1);
        }
        //转换为分钟:秒
        chapter.setDuration(String.valueOf(ls / 60) + ":" + String.valueOf(ls % 60));
        //获取文件大小
        //file.length可以获取文件字节大小
        double size = file1.length() / 1024.0 / 1024;
        size = (double) Math.round(size * 100) / 100;
        String size1 = String.valueOf(size) + "MB";
        chapter.setSize(String.valueOf(size1));
        System.out.println("id===" + chapter.getAlbum_Id());
        chapterService.updateChapter(chapter);

    }

    @RequestMapping("downFile")//注册方法
    public String downFile(String path, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //把地址拆分成字符串数组
        String[] split = path.split("/");
        //获取文件名
        String fileName = split[split.length - 1];
        //通过相对路径获取绝对路径
        String juepath = request.getSession().getServletContext().getRealPath("/albumFile");
        //设置文件名的编码格式  通过IO输入流
        String newFileName = URLEncoder.encode(fileName, "UTF-8");
        //设置响应前的下载方式
        response.setHeader("content-disposition", "attachment;fileName=" + newFileName);
        //通过文件名获取文件后缀
        String extension = FilenameUtils.getExtension(fileName);
        System.out.println("后缀名：" + extension);
        //通过文件后缀，获取MIME类型
        String mimeType = request.getSession().getServletContext().getMimeType("." + extension);
        System.out.println("MIME类型：" + mimeType);
        //设置响应类型
        response.setContentType(mimeType);
        //输入文件
        FileInputStream is = new FileInputStream(new File(juepath, fileName));
        //通过输出流向页面输出数据
        ServletOutputStream os = response.getOutputStream();
        //文件传输数据  通过数据传输 速度比较快
        byte[] bytes = new byte[2048];
        while (true) {
            //读取服务器永久储存设备的数据到内存中
            int i = is.read(bytes, 0, bytes.length);
            //判断读取的是不是最后一位
            if (i == -1) break;
            //把内存中的数据输出到永久内存中
            os.write(bytes, 0, bytes.length);
        }
        //释放资源
        is.close();
        os.close();
        //跳转
        return null;
    }
}
