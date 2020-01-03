package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data//get set toString
@NoArgsConstructor//无参
@AllArgsConstructor//有参
public class Admin implements Serializable {
    private String id;//主键
    private String username;//登录名
    private String password;//密码
    private String other;//预留字段
}
