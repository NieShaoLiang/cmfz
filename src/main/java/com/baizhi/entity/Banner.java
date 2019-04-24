package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "图片",type = 2,width = 50,height = 35)
    private String imgPath;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接收前台传的字符串，转换date
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")//数据库中date类型转换为string传输给前台
    @Excel(name = "创建日期",format = "yyyy年MM月dd日 HH时mm分ss秒",width = 30)
    private Date createDate;
    @ExcelIgnore
    private Integer status;//状态是1为展示，0为默认状态，不展示

}
