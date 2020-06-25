package com.tt.work.starter.impoetfile.service.impl;

import com.tt.work.starter.impoetfile.dao.IExeclModelDao;
import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.service.IExeclModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Service
public class ExeclModelServiceImpl implements IExeclModelService {

    @Autowired
    private IExeclModelDao execlModelDao;


    @Override
    public List<ExeclModel> queryTodayAll() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = calendar.get(Calendar.YEAR)+"";
        // 获取当前月
        String month = calendar.get(Calendar.MONTH) + 1+"";
        // 获取当前日
        String day = calendar.get(Calendar.DATE)-1+"";
        //获取当天所有客户拍单数据
        List<ExeclModel> todayAll = execlModelDao.queryTodayAll(year + month + day);

        return todayAll;
    }

    @Override
    public Integer updateExeclModel(ExeclModel execlModel) {
        return execlModelDao.updateExeclModel(execlModel);
    }

    @Override
    public Integer updatesuccess(String id) {
        return execlModelDao.updatesuccess(id);
    }

    @Override
    public ExeclModel queryByid(String id) {
        return execlModelDao.queryById(id);
    }
}
