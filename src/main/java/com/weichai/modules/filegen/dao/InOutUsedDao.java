/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.entity.InOutUsed;

import java.util.List;

/**
 * 内外码发放DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface InOutUsedDao {

    /**
     *添加一条内外码发放记录
     */
    public int insertInOutUsed(InOutUsed inOutUsed);


    public List<InOutUsed> selectInOutUsedCount(InOutUsed inOutUsed);
}