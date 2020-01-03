package com.baizhi.cmfz_rlh;

import com.baizhi.App;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

//在此容器下进行
@RunWith(SpringRunner.class)
//加载入口类
@SpringBootTest(classes = App.class)
public class UserTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    //最近12月
    @Test
    public void contextLoads() {
        List<Month> months = userDao.selectMonth();
        for (Month month : months) {
            System.out.println("月份==" + month);
        }
    }

    //最近七天
    @Test
    public void contextLoad() {
        List<Month> months = userDao.selectBySeven();
        for (Month month : months) {
            System.out.println(month);

        }
    }

    //最近总数
    @Test
    public void contextLo() {
        List<Month> months = userDao.selectUser();
        for (Month month : months) {
            System.out.println(month);

        }
    }

    //    实时刷新===========================================================
    @Test
    public void test() {
        //参数一：是goeasy传输路径      参数二：传输类型
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-b121a256bfeb4d0e874c71b5a744d092");
        Map<Integer, List<Object>> integerListMap = userService.selectBySeven();
        List<Object> count = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            count.add(new Random().nextInt(100));
            System.out.println("--=-=-");

        }
        integerListMap.put(1, count);
        //创建一个fastjson
        ObjectMapper objectMapper = new ObjectMapper();
        String s1 = null;
        try {
            //通过fastjson转换map集合成json串
            s1 = objectMapper.writeValueAsString(integerListMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        goEasy.publish("echarts", s1);
    }

}
