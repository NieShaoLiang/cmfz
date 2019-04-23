package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Date date;

    private Integer status;

    private Integer masterId;

}
