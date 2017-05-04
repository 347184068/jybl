/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.timingtask.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.timingtask.entity.ImtOiScan;

import java.util.List;

/**
 * 文件监测DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface MonitorFileDao {
    // 监测下载
    public InOutDetail selectExistByCode(InOutDetail inOutDetail);

    public void insertInOutDetail(InOutDetail inOutDetail);

    public Integer countInOutDetail();

}