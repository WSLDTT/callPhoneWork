package com.tt.work.starter.impoetfile.dao;

import com.tt.work.starter.impoetfile.model.ExeclModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Repository
public interface IExeclModelDao {

    /**
     * 插入一条execlModel
     * @param execlModel
     * @return
     */
    Integer insert(ExeclModel execlModel);


    /**
     * 查询今天所有状态得ExeclModel
     * @param insertDate
     * @return
     */
    List<ExeclModel> queryTodayAll(@Param("insertDate") String insertDate);

    /**
     * 查询今天拍单成功得ExeclModel
     * @param insertDate
     * @return
     */
    List<ExeclModel> queryTodaySuccess(@Param("insertDate") String insertDate);

    /**
     * 根据id更新 拨打备注、拨打电话、拨打电话2字段
     * @param execlModel
     * @return
     */
    Integer updateExeclModel(ExeclModel execlModel);

    /**
     * 把成功状态更新为1 置为成功
     * @param id
     * @return
     */
    Integer updatesuccess(@Param("id")String id);


    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    ExeclModel queryById(@Param("id")String id);


    /**
     * 查询今天每个电话拨出去的次数
     * @param insertDate
     * @return
     */
    List<Map<String, Object>> queryTodayCallPhoneNum(@Param("insertDate")String insertDate);

    /**
     * 根据id把数据ExplainState置为无效
     * @param id
     * @return
     */
    Integer updateExplainStateToWX(@Param("id")String id);
}
