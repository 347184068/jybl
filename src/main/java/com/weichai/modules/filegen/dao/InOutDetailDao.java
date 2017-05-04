/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface InOutDetailDao {

    /**
     *批量插入内外码
     */
    public void batchInsertInOutDetail(List<InOutDetail> inOutDetail);
    public void batchSave(List<InOutDetail> inOutDetail);


    /**
     * 批量根据外码查找内码
     */

    public String getInCodeByOutCode(String OutCode);
}