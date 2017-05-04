/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.service;

import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class InOutDetailService {
    private static final Logger LOG = Logger.getLogger(InOutDetailService.class);
    @Autowired
    private InOutDetailDao inOutDetailDao;

    @Async
    public void batchInsertInOutDetails(List<InOutDetail> detailList){

        System.out.println("-------start---"+new Date());
        int batchCount =1000;
        int batchLastIndex = batchCount-1;
        for(int index=0;index<detailList.size();){
            if(batchLastIndex>detailList.size()-1){
                List<InOutDetail> list = Lists.newArrayList();
                batchLastIndex = detailList.size()-1;
                list.addAll(detailList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                inOutDetailDao.batchSave(list);
            }else {
                List<InOutDetail> list1 = Lists.newArrayList();
                list1.addAll(detailList.subList(index, batchLastIndex + 1));
                inOutDetailDao.batchSave(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
        System.out.println("-------end---"+new Date());
    }

    }


