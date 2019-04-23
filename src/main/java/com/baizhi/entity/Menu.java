package com.baizhi.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
public class Menu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String title;
    //@Column(name = "icon_cls")
    private String iconCls;    //图标地址
    //@Column(name = "parent_id")
    private Integer parentId;   //是否有上级导航
    //@Column(name = "jsp_url")
    private String jspUrl;  //点击二级类别跳转的页面地址

    @Transient
    private List<Menu> clist;

}
