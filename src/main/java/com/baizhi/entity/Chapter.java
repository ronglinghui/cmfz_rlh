package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//章节相关的实体类  实现序列化接口
@Data//get set tostring hash
@AllArgsConstructor//有参
@NoArgsConstructor//无参
public class Chapter implements Serializable {

    private String id;//主键
    private String title;//名字
    private String album_Id;//专辑的id
    private String size;//音频大小
    private String duration;//音频时长
    private String src;//图片
    private String status;//状态
    private String other;//预留字段

}
