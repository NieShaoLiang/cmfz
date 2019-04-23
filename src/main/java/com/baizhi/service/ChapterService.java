package com.baizhi.service;

import com.baizhi.entity.Chapter;
import it.sauronsoftware.jave.EncoderException;


public interface ChapterService {

    void insert(Chapter chapter);

    void update(Chapter chapter);

    void delete(String id);

    Chapter selectOne(String id);

    void setDuration(Chapter chapter) throws EncoderException;


}
