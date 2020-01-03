package com.baizhi.service.advice;

import com.baizhi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goeasy.GoEasy;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component//放进工厂中
public class UserAdvice {
    @Autowired
    private UserService userService;

    //切入点表达式
    @Pointcut(value = "execution(public * com.baizhi.service.UserService.*Userq(..))")
    public void advice() {
    }

    @After("advice()")
    public void goEasy() {
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
