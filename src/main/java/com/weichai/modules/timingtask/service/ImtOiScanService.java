/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.timingtask.service;


import com.google.common.collect.Lists;
import com.weichai.modules.timingtask.dao.ImtOiScanDao;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class ImtOiScanService {
    private static final Logger LOG = Logger.getLogger(ImtOiScanService.class);
    @Autowired
    private ImtOiScanDao imtOiScanDao;


    public List<ImtOiScan> getInOutProductListUnion(){
        List<ImtOiScan> imtOiScanList = Lists.newArrayList();
        imtOiScanList = imtOiScanDao.selectImtOiScanList();
        return imtOiScanList;
    }

    public void batchUpdateImtScanStatus(List<ImtOiScan> imtOiScanList){
        int batchCount =1000;
        int batchLastIndex = batchCount-1;
        for(int index = 0;index < imtOiScanList.size();){
            if(batchLastIndex > imtOiScanList.size()-1){
                List<ImtOiScan> list = Lists.newArrayList();
                batchLastIndex = imtOiScanList.size()-1;
                list.addAll(imtOiScanList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                imtOiScanDao.updateImtOiScanImtStatus(list);

            }else {
                List<ImtOiScan> list1 = Lists.newArrayList();
                list1.addAll(imtOiScanList.subList(index, batchLastIndex + 1));
                imtOiScanDao.updateImtOiScanImtStatus(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
    }





}


