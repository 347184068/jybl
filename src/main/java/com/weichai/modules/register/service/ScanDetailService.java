/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.register.service;

import com.google.common.collect.Lists;
import com.weichai.modules.register.dao.ScanDetailDao;
import com.weichai.modules.register.dao.UserRegisterDao;
import com.weichai.modules.register.entity.ScanDetail;
import com.weichai.modules.register.entity.UserRegister;
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
public class ScanDetailService {
    private static final Logger LOG = Logger.getLogger(ScanDetailService.class);
    @Autowired
    private ScanDetailDao scanDetailDao;

    public int addScanDetail(ScanDetail scanDetail){
        int n=0;
        n=scanDetailDao.insertScanDetail(scanDetail);
        return n;
    }

    public List<ScanDetail> queryScanDetailList(ScanDetail scanDetail){
        List<ScanDetail> scanDetailList = Lists.newArrayList();
        scanDetailList = scanDetailDao.selectScanDetailByCon(scanDetail);
        return scanDetailList;
    }



}


