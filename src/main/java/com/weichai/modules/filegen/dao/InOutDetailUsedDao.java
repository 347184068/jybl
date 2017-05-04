/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.filegen.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.timingtask.dao.ImtOiValidCodeDao;
import com.weichai.modules.timingtask.entity.ImtOiValidCode;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface InOutDetailUsedDao {

    /**
     *批量插入内外码
     */
    public void batchInsertInOutDetail(List<InOutDetailUsed> inOutDetailUsedList);
    public void batchSave(List<InOutDetailUsed> inOutDetailUsedList);



    public List<ImtOiValidCode> selectInOutDetailUsedList();

    public void batchUpdateImtStatus(List<ImtOiValidCode> inOutDetailUsedList);

    public int update(ImtOiValidCode imtOiValidCode);

    /**
     * 废弃
     * @param inOutDetailUsedList
     */
    public void batchSaveFailCode(List<InOutDetailUsed> inOutDetailUsedList);
}