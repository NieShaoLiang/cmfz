package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bannerService")
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;


    public List<Banner> queryAll(){
        return bannerDao.selectAll();
    }

    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public void delete(Integer id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
    @Override
    public Map selectAll(int page, int rows) {
        Map map = new HashMap();

        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds(rows*(page-1),rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);

        map.put("rows",banners);
        int total = bannerDao.selectAll().size();
        map.put("total",total);
        return map;
    }

    @Override
    public HSSFWorkbook downloadExcel() {
        //创建 Excel 工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("轮播图");
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"标题","状态","上传日期"};
        //创建单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            //i 标示列索引
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //处理日期格式
        HSSFCellStyle cellStyle = workbook.createCellStyle(); //样式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat(); //日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy 年 MM 月 dd 日 ")); //设置日期格式


        List<Banner> banners = bannerDao.selectAll();
        //System.out.println(banners);
        //处理数据行
        for (int i = 1; i < banners.size()+1; i++) {
            row = sheet.createRow(i);
            String title1 = banners.get(i-1).getTitle();
            row.createCell(0).setCellValue(title1);
            Integer status = banners.get(i-1).getStatus();
            row.createCell(1).setCellValue(status);
            //设置出生年月格式
            cell = row.createCell(2);
            Date createDate = banners.get(i-1).getCreateDate();
            cell.setCellValue(createDate);
            System.out.println(title1+"================="+status+"========================="+createDate);
            cell.setCellStyle(cellStyle);
        }
        try {
            workbook.write(new File("e:\\服务器\\轮播图.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    @Override
    public Workbook downloadExcel2() {
        List<Banner> banners = bannerDao.selectAll();
        for (Banner banner : banners) {
            banner.setImgPath("e://服务器//"+banner.getImgPath());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图展示","轮播图"),
                Banner.class, banners);
        return workbook;
    }
}
