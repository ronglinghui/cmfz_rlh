package com.baizhi.cmfz_rlh;

import com.baizhi.App;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//在此容器下进行
@RunWith(SpringRunner.class)
//加载入口类
@SpringBootTest(classes = App.class)
public class AlbumTest {
    @Autowired
    private AlbumDao albumDao;

    @Test
    public void contextLoads() {
        Album album = new Album("1", "zhangs", "haha", "32", "zhnags", "好好啊", 1, "zhang", new Date(), "显示", null);

        albumDao.insertAlbum(album);
    }

    @Test
    public void contextLoa() {
        Album album = new Album();
        album.setId("1");
        album.setStatus("不显示");
        albumDao.updateAlbum(album);
    }

    @Test
    public void contextLods() {
        Integer integer = albumDao.selectCountAlbum();
        System.out.println(integer);
    }

    @Test
    public void contextLo() {
        List<Album> albums = albumDao.sybaseAlbum(0, 1);
        System.out.println(albums);
    }

    @Test
    public void contetLo() {
        String[] id = {"2", "3", "4", "5"};
        albumDao.deleteAlbum(id);
        System.out.println("ok");
    }
}
