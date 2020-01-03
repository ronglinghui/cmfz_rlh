package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//轮播图实体类
@Data//get set toString hash
@AllArgsConstructor//有参
@NoArgsConstructor//无参
public class Banner implements Serializable {
    //这个注解用于excle的导入和导出
    @Excel(name = "编号", isImportField = "true_st")
    private String id;//主键
    @Excel(name = "姓名", isImportField = "true_st")
    private String title;//名字
    @Excel(name = "图片", type = 2, width = 20, height = 20, isImportField = "true_st")
    private String img;//图片
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", format = "yyyy-MM-dd", isImportField = "true_st")
    private Date create_Date;//上传时间
    @Excel(name = "状态", isImportField = "true_st")
    private String status;//状态

    private String other;
}
