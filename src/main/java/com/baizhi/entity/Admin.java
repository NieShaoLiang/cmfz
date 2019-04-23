package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;
    private String password;

}
