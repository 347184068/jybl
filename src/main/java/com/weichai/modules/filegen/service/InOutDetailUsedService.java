/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.service;

import com.google.common.collect.Lists;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutDetailUsedDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.timingtask.entity.ImtOiValidCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class InOutDetailUsedService {
    private static final Logger LOG = Logger.getLogger(InOutDetailUsedService.class);
    @Autowired
    private InOutDetailUsedDao inOutDetailUsedDao;

    /*@Async*/
    public void batchInsertInOutDetailUseds(List<InOutDetailUsed> detailUsedList){
        System.out.println(new Date());
        int batchCount =500;
        int batchLastIndex = batchCount-1;
        for(int index=0;index<detailUsedList.size();){
            if(batchLastIndex>detailUsedList.size()-1){
                List<InOutDetailUsed> list = Lists.newArrayList();
                batchLastIndex = detailUsedList.size()-1;
                list.addAll(detailUsedList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                inOutDetailUsedDao.batchSave(list);
            }else {
                List<InOutDetailUsed> list1 = Lists.newArrayList();
                list1.addAll(detailUsedList.subList(index, batchLastIndex + 1));
                inOutDetailUsedDao.batchSave(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
        System.out.println(new Date());
    }


    /**
     * 取数据
     */
    public List<ImtOiValidCode> getInOutDetailUsedList(){
        List<ImtOiValidCode> inOutDetailUsedList = Lists.newArrayList();
        inOutDetailUsedList = inOutDetailUsedDao.selectInOutDetailUsedList();
        return inOutDetailUsedList;
    }

    public void batchUpdateInOutDetails(List<ImtOiValidCode> imtOiValidCodeList){

        int batchCount =500;
        int batchLastIndex = batchCount-1;
        for(int index=0;index<imtOiValidCodeList.size();){
            if(batchLastIndex>imtOiValidCodeList.size()-1){
                List<ImtOiValidCode> list = Lists.newArrayList();
                batchLastIndex = imtOiValidCodeList.size()-1;
                list.addAll(imtOiValidCodeList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                inOutDetailUsedDao.batchUpdateImtStatus(list);
            }else {
                List<ImtOiValidCode> list1 = Lists.newArrayList();
                list1.addAll(imtOiValidCodeList.subList(index, batchLastIndex + 1));
                inOutDetailUsedDao.batchUpdateImtStatus(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }

    }

    public void batchInsertInOutDetailUsedFailCode(List<InOutDetailUsed> detailUsedList){
        System.out.println(new Date());
        int batchCount =500;
        int batchLastIndex = batchCount-1;
        for(int index=0;index<detailUsedList.size();){
            if(batchLastIndex>detailUsedList.size()-1){
                List<InOutDetailUsed> list = Lists.newArrayList();
                batchLastIndex = detailUsedList.size()-1;
                list.addAll(detailUsedList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                inOutDetailUsedDao.batchSaveFailCode(list);
            }else {
                List<InOutDetailUsed> list1 = Lists.newArrayList();
                list1.addAll(detailUsedList.subList(index, batchLastIndex + 1));
                inOutDetailUsedDao.batchSaveFailCode(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
        System.out.println(new Date());
    }


}


