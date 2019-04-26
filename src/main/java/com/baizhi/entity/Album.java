package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Excel(name = "专辑名称",needMerge = true)
    private String title;
    private Integer amount;
    @Excel(name = "图片",type = 2,width = 50,height = 35,needMerge = true)
    private String imgPath;
    private Double score;
    private String author;
    private String boardcast;

    @Excel(name = "创建日期",needMerge = true,format = "yyyy年MM月dd日 HH时mm分ss秒",width = 30)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JSONField(format = "yyyy-MM-dd HH-mm-ss")
    private Date publishDate;
    private String brief;
    @ExcelCollection(name = "专辑")
    @Transient
    private List<Chapter> children;


}
