package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("albumService")
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public List<Album> queryAll() {
        List<Album> albums = albumDao.queryAll();
        for (Album album : albums) {
            album.setAmount(album.getChildren().size());//设置专辑包含多少章节
        }
        return albums;
    }

    @Override
    public Map selectAll(int page,int rows) {
        Map map = new HashMap();


        RowBounds rowBounds = new RowBounds(rows*(page-1),rows);
        List<Album> albums = albumDao.selectByRowBounds(new Album(), rowBounds);
        map.put("rows",albums);
        int total = albumDao.selectAll().size();
        map.put("total",total);
        return map;
    }

    @Override
    public void insert(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void delete(Integer id) {
        albumDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public Album selectOne(Integer id) {
        Album album = albumDao.selectByPrimaryKey(id);
        //album.setAmount(album.getChildren().size());//设置多少章节
        return album;
    }

    @Override
    public Workbook downloadExcel() {
        List<Album> albums = albumDao.queryAll();
        for (Album album : albums) {
            album.setImgPath("e://服务器//"+album.getImgPath());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑展示","专辑"),
                Album.class, albums);
        return workbook;
    }


}
