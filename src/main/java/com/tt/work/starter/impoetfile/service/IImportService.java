package com.tt.work.starter.impoetfile.service;

import com.tt.work.starter.impoetfile.model.ExeclModel;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
public interface IImportService {

    /**
     * 插入ExeclModel对象
     * @param execlModel
     * @return
     */
    Integer insert(ExeclModel execlModel);
}
