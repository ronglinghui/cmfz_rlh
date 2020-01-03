package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data//get set toString hash
@NoArgsConstructor//无参
@AllArgsConstructor//有参
//用户表的实体类
public class User implements Serializable {
    private String id;//主键
    private String phone_Number;//手机号
    private String password;//密码
    private String name;//名字
    private String dharma;//法名
    private String head_Img;//图片
    private String sex;//性别
    private String address;//地址
    private String sign;//签名
    private String guru_Id;//上师id
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date last_Date;//最后时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_Date;//创建时间
    private String status;//状态
    private String salt;//加密字段
    private String other;//预留字段
}
