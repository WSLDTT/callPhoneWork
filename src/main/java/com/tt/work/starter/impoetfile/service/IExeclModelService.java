package com.tt.work.starter.impoetfile.service;

import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.model.PageQuery;

import java.util.List;
import java.util.Map;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
public interface IExeclModelService {

    /**
     * 查询今天所有状态得ExeclModel
     * @return
     */
    PageQuery queryTodayAll();

    /**
     * 根据id更新 拨打备注、拨打电话、拨打电话2字段
     * @param execlModel
     * @return
     */
    Integer updateExeclModel(ExeclModel execlModel);

    Integer updatesuccess(String id);

    ExeclModel queryByid(String id);

    List<Map<String, String>> queryTodayCallPhoneNum();

    Integer updateExplainStateToWX(String id);
}
