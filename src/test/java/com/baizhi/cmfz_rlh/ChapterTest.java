package com.baizhi.cmfz_rlh;

import com.baizhi.App;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

//在此容器下进行
@RunWith(SpringRunner.class)
//加载入口类
@SpringBootTest(classes = App.class)
public class ChapterTest {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private ChapterService chapterService;

    @Test
    public void contextLoads() {
        Chapter chapter = new Chapter("1", "zhang", "1", "3", "30", "zhang", "显示", null);
        chapterDao.insertChapter(chapter);
    }

    @Test
    public void contextLoa() {
        Chapter chapter = new Chapter("03f13bf8-7b02-4c3a-8094-db3b77f8b5c2", null, "ae5e2879-ff64-425a-b0dc-fa71de3fd302", "30.775484085083008MB", "0:0", null, null, null);
        //Chapter chapter = new Chapter();
        //chapter.setId("1");
        //chapter.setStatus("不显示");
        chapterDao.updateChapter(chapter);
    }

    @Test
    public void contextLods() {
        Integer integer = chapterDao.selectCountChapter(":1");
        System.out.println(integer);
    }

    @Test
    public void contextLo() {
        // List<Chapter> chapters = chapterDao.sybaseChapter(0, 3,"e5e2879-ff64-425a-b0dc-fa71de3fd302");
        //ae5e2879-ff64-425a-b0dc-fa71de3fd302
        Map<String, Object> map = chapterService.sybaseChapter(1, 3, "ae5e2879-ff64-425a-b0dc-fa71de3fd302");
        List<Chapter> rows = (List<Chapter>) map.get("rows");
        System.out.println("rows" + rows);
    }

    @Test
    public void contetLo() {
        String[] id = {"2", "3", "4", "5"};
        chapterDao.deleteChapter(id);
        System.out.println("ok");
    }
}
