package com.baizhi.service.impl;

import com.baizhi.dao.MasterDao;
import com.baizhi.entity.Master;
import com.baizhi.service.MasterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("masterService")
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterDao masterDao;

    @Override
    public List<Master> selectAll() {
        return masterDao.selectAll();
    }

    @Override
    public Map queryByPage(int page, int rows) {
        Map map = new HashMap();
        RowBounds rowBounds = new RowBounds(page*(rows-1),rows);
        List<Master> list = masterDao.selectByRowBounds(new Master(), rowBounds);

        map.put("rows",list);
        map.put("total",masterDao.selectAll().size());

        return map;
    }

    @Override
    public void insert(Master master) {
        masterDao.insert(master);
    }

    @Override
    public void delete(Integer id) {
        masterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Master master) {
        masterDao.updateByPrimaryKeySelective(master);
    }
}
