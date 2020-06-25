package com.tt.work.starter.impoetfile.service.impl;


import com.tt.work.starter.impoetfile.dao.IExeclModelDao;
import com.tt.work.starter.impoetfile.model.ExeclModel;
import com.tt.work.starter.impoetfile.service.IImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Service
public class ImportServiceImpl implements IImportService {

    @Autowired
    private IExeclModelDao execlModelDao;

    @Override
    public Integer insert(ExeclModel execlModel) {
        return execlModelDao.insert(execlModel);
    }
}
