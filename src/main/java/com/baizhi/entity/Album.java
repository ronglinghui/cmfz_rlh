package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//专辑相关的实体类 实现序列化
@Data//get set toString hash
@AllArgsConstructor//有参
@NoArgsConstructor//无参
public class Album implements Serializable {

    private String id;//主键
    private String title;//名字
    private String img;//图片
    private String score;//分数
    private String author;//作者
    private String broadcaster;//播音员
    private Integer count;//集数
    private String brief;//简介
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_Date;//创建时间
    private String status;//状态
    private String other;//预留字段


}
