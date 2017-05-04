/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.timingtask.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.entity.InOutProduct;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface ImtOiScanDao {
    public List<ImtOiScan> selectImtOiScanList();

    public void updateImtOiScanImtStatus(List<ImtOiScan> materialList);
}