package com.baizhi.service;

import com.baizhi.entity.Banner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface BannerService {
    List<Banner> queryAll();

    void insert(Banner banner);

    void delete(Integer id);

    void update(Banner banner);

    Map selectAll(int page,int rows);

    HSSFWorkbook downloadExcel();

    Workbook downloadExcel2();
}
