/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.service;

import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.entity.InOutGrant;
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
public class InOutGrantService {
    private static final Logger LOG = Logger.getLogger(InOutGrantService.class);
    @Autowired
    private InOutGrantDao inOutGrantDao;

    public int getCountBatch(String type){
        int batch = inOutGrantDao.countBatch(type);
        return batch;
    }

    public void addInOutGrant(InOutGrant inOutGrant){
        inOutGrant.setId(IdGen.uuid());
        inOutGrant.setCreateDate(new Date());
        inOutGrantDao.insertInOutGrant(inOutGrant);
    }

    public List<InOutGrant> getGrantCount(InOutGrant inOutGrant){
        List<InOutGrant> inOutGrantList = Lists.newArrayList();
        inOutGrantList = inOutGrantDao.selectInOutGrantCount(inOutGrant);
        return inOutGrantList;
    }

    public int updateGrantUrl(InOutGrant inOutGrant){
        int n=0;
        n = inOutGrantDao.updateInOutGrantUrl(inOutGrant);
        return n;
    }
}