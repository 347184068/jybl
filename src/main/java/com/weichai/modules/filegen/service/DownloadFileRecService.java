/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.service;

import com.google.common.collect.Lists;
import com.weichai.modules.filegen.dao.DownloadFileRecDao;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.entity.DownloadFileRec;
import com.weichai.modules.filegen.entity.InOutDetail;
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
public class DownloadFileRecService {
    private static final Logger LOG = Logger.getLogger(DownloadFileRecService.class);
    @Autowired
    private DownloadFileRecDao downloadFileRecDao;

    public void addDownloadFileRec(DownloadFileRec fileRec){
        downloadFileRecDao.insertDownloadFileRec(fileRec);
    }
}


