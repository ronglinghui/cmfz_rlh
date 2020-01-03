package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//定义独立的spring应用程序
@MapperScan("com.baizhi.dao")//扫描dao 利用工厂创建对象
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
