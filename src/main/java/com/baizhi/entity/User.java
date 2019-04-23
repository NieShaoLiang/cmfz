package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;
    private String password;
    private String dharma;
    private Integer sex;
    private String province;
    private String city;
    private String phone;
    private String sign;

    @Column(name = "head_img")
    private String headImg;
    private Integer status;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JSONField(format = "yyyy-MM-dd HH-mm-ss")
    private Date createDate;
    private String salt;

    @Column(name = "master_id")
    private Integer masterId;


}
