/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.statistics.entity.CodeStatisticInfo;

import java.util.List;

/**
 * 后台统计DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface CodeStatisticsDao {

    /**
     * 查询申请频次
     * @param codeStatisticInfo
     * @return
     */
    public Integer countGrantNum(CodeStatisticInfo codeStatisticInfo);

    /**
     * 查询打印回传频次
     * @param codeStatisticInfo
     * @return
     */
    public Integer countUploadNum(CodeStatisticInfo codeStatisticInfo);

    /**
     * 查询回传合格数量，
     * @param codeStatisticInfo
     * @return
     */
    public Integer countQualifiedSum(CodeStatisticInfo codeStatisticInfo);

    /**
     * 查询回传报废数量
     * @param codeStatisticInfo
     * @return
     */
    public Integer countScrapSum(CodeStatisticInfo codeStatisticInfo);


}