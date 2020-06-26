package com.tt.work.starter.impoetfile.model;

import java.util.List;
import java.util.Map;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
public class PageQuery {

    List<ExeclModel> todayAll;

    List<Map<String, String>> mapList;

    public List<ExeclModel> getTodayAll() {
        return todayAll;
    }

    public void setTodayAll(List<ExeclModel> todayAll) {
        this.todayAll = todayAll;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }
}
