/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.statistics.entity.CodeMaterialInfo;
import com.weichai.modules.statistics.entity.CodeStatisticInfo;

import java.util.List;

/**
 * 防伪码订单关联情况DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface CodeMaterialDao {


    /**
     * 查询应传、已传
     * @param codeMaterialInfo
     * @return
     */

    public List queryCodeMaterialList(CodeMaterialInfo codeMaterialInfo);

    public List<CodeMaterialInfo> getSupplierList();
}