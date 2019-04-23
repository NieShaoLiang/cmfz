package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String title;

    private String imgPath;

    private String content;

    @Column(name = "publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JSONField(format = "yyyy-MM-dd HH-mm-ss")
    private Date date;

    private Integer status;

    private Integer masterId;

}
