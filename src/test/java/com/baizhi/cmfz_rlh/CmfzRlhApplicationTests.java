package com.baizhi.cmfz_rlh;

import com.baizhi.App;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//在此容器下进行
@RunWith(SpringRunner.class)
//加载入口类
@SpringBootTest(classes = App.class)
public class CmfzRlhApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void contextLoads() {
        Admin zhangsan = adminDao.loginAdmin("zhangsan");
        System.out.println(zhangsan);
    }

}
