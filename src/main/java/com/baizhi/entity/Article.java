package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//上师发布的文章所对应的实体类
@Data//get set  toString hash
@AllArgsConstructor//有参
@NoArgsConstructor//无参
public class Article {
    private String id;//主键
    private String title;//名字
    private String author;//作者
    private String content;//内容
    private String guru_Id;//上师的主键
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_Date;//创建的时间
    private String status;//状态
    private String other;//预留字段
}
