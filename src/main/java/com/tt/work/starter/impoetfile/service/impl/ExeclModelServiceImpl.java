package com.tt.work.starter.impoetfile.service.impl;

import com.tt.work.starter.impoetfile.dao.IExeclModelDao;
import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.model.PageQuery;
import com.tt.work.starter.impoetfile.service.IExeclModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Service
public class ExeclModelServiceImpl implements IExeclModelService {

    @Autowired
    private IExeclModelDao execlModelDao;


    @Override
    public PageQuery queryTodayAll() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = calendar.get(Calendar.YEAR)+"";
        // 获取当前月
        String month = calendar.get(Calendar.MONTH) + 1+"";
        // 获取当前日
        String day = calendar.get(Calendar.DATE) +"";
        //获取当天所有客户拍单数据
        List<ExeclModel> todayAll = execlModelDao.queryTodayAll(year + month + day);
        List<Map<String, String>> list = queryTodayCallPhoneNum();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setMapList(list);
        pageQuery.setTodayAll(todayAll);
        return pageQuery;
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

    @Override
    public List<Map<String, String>> queryTodayCallPhoneNum() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = calendar.get(Calendar.YEAR)+"";
        // 获取当前月
        String month = calendar.get(Calendar.MONTH) + 1+"";
        // 获取当前日
        String day = calendar.get(Calendar.DATE) +"";

        List<Map<String, Object>> mapList = execlModelDao.queryTodayCallPhoneNum(year + month + day);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (mapList!=null){
            for (Map<String, Object> map:mapList){
                Map<String, String> mapRes = new HashMap<String, String>();
                String phone = map.get("phone").toString();
                String num = Long.toString((Long) map.get("num"));
                mapRes.put("phone",phone);
                mapRes.put("num",num);
                list.add(mapRes);
            }
        }

        return list;
    }

    @Override
    public Integer updateExplainStateToWX(String id) {
        return execlModelDao.updateExplainStateToWX(id);
    }
}
