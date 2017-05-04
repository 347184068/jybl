/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.material.service;

import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutUsedDao;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.material.dao.MaterialDao;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.entity.InOutProduct;
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
public class MaterialService {
    private static final Logger LOG = Logger.getLogger(MaterialService.class);
    @Autowired
    private MaterialDao materialDao;


    public void batchAddMaterial(List<ImtOiScan> materialList){

        //System.out.println("-------start-batchAddMaterial--"+new Date());
        int batchCount =500;
        int batchLastIndex = batchCount-1;

        for(int index = 0;index < materialList.size();){
            if(batchLastIndex > materialList.size()-1){
                List<ImtOiScan> list = Lists.newArrayList();
                batchLastIndex = materialList.size()-1;
                list.addAll(materialList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                materialDao.insertMaterial(list);
            }else {
                List<ImtOiScan> list1 = Lists.newArrayList();
                list1.addAll(materialList.subList(index, batchLastIndex + 1));
                materialDao.insertMaterial(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
       // System.out.println("-------end--batchAddMaterial--"+new Date());
    }

    public Material getMaterial(Material material){
        Material materialResult = new Material();
        materialResult = materialDao.selectMaterial(material);
        return materialResult;
    }


}