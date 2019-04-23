package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    protected ChapterDao chapterDao;

    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public void delete(String id) {
        chapterDao.deleteByPrimaryKey(id);
    }

    @Override
    public Chapter selectOne(String id) {
        return chapterDao.selectByPrimaryKey(id);
    }


    @Override
    public void setDuration(Chapter chapter) throws EncoderException {
        //获取时长
        Encoder encoder = new Encoder();
        String path = chapter.getPath();
        try {
            File source = new File("E://服务器//"+path);
            MultimediaInfo m = encoder.getInfo(source);
            long ls = m.getDuration();
            int hour = (int) (ls/3600000);  //时
            int minute = (int) (ls-(hour*3600000))/60000;   //分
            int second = (int) ((ls-(hour*3600000)-(minute*60000))/1000);   //秒
            String duration = hour+":"+minute+":"+second;//拼接的时长
            chapter.setDuration(duration);
        } catch (Exception e) {
            chapter.setDuration("未知");
            throw e;
        }
    }
}
