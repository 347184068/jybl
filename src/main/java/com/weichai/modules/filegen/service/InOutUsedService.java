/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.service;

import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.dao.InOutUsedDao;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.entity.InOutUsed;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class InOutUsedService {
    private static final Logger LOG = Logger.getLogger(InOutUsedService.class);
    @Autowired
    private InOutUsedDao inOutUsedDao;


    public void addInOutUsed(InOutUsed inOutUsed){
        inOutUsed.setId(IdGen.uuid());
        inOutUsed.setCreateDate(new Date());
        inOutUsedDao.insertInOutUsed(inOutUsed);
    }

    public List<InOutUsed> getUploadCount(InOutUsed inOutUsed){
        List<InOutUsed> inOutUsedList = Lists.newArrayList();
        inOutUsedList = inOutUsedDao.selectInOutUsedCount(inOutUsed);
        return inOutUsedList;
    }
}