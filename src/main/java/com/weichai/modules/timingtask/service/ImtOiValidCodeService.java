/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.timingtask.service;

import com.google.common.collect.Lists;
import com.weichai.modules.timingtask.dao.ImtOiValidCodeDao;
import com.weichai.modules.timingtask.entity.ImtOiValidCode;
import com.weichai.modules.validatecode.entity.InOutProduct;
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
public class ImtOiValidCodeService {
    private static final Logger LOG = Logger.getLogger(ImtOiValidCodeService.class);
    @Autowired
    private ImtOiValidCodeDao imtOiValidCodeDao;


    public void batchAddImtOiValidCode(List<ImtOiValidCode> imtOiValidCodeList){
        int batchCount =2000;
        int batchLastIndex = batchCount-1;

        for(int index = 0;index < imtOiValidCodeList.size();){
            if(batchLastIndex > imtOiValidCodeList.size()-1){
                List<ImtOiValidCode> list = Lists.newArrayList();
                batchLastIndex = imtOiValidCodeList.size()-1;
                list.addAll(imtOiValidCodeList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                imtOiValidCodeDao.batchInsertImtOiValidCode(list);
            }else {
                List<ImtOiValidCode> list1 = Lists.newArrayList();
                list1.addAll(imtOiValidCodeList.subList(index, batchLastIndex + 1));
                imtOiValidCodeDao.batchInsertImtOiValidCode(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
    }
}


