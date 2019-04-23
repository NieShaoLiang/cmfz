package com.baizhi.entity;

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

    private String title;
    private Integer amount;
    private String imgPath;
    private Double score;
    private String author;
    private String boardcast;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JSONField(format = "yyyy-MM-dd HH-mm-ss")
    private Date publishDate;
    private String brief;

    @Transient
    private List<Chapter> children;


}
