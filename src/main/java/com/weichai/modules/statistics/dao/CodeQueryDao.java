/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.statistics.entity.CodeMaterialInfo;
import com.weichai.modules.statistics.entity.CodeQueryInfo;

import java.util.List;

/**
 * 防伪码查询情况DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface CodeQueryDao {

    /**
     * 汇总
     * 查询合格证
     * @param codeQueryInfo
     * @return
     */
    public List<CodeQueryInfo> selectProductIdQuery(CodeQueryInfo codeQueryInfo);

    /**
     * 汇总
     * 查询防伪码
     * @param codeQueryInfo
     * @return
     */
    public List<CodeQueryInfo> selectSecurityQuery(CodeQueryInfo codeQueryInfo);

    /**
     * 汇总
     * 查询外码
     * @param codeQueryInfo
     * @return
     */
    public List<CodeQueryInfo> selectWIdQuery(CodeQueryInfo codeQueryInfo);

    /**
     * 汇总
     * 查询内码
     * @param codeQueryInfo
     * @return
     */
    public List<CodeQueryInfo> selectNIdQuery(CodeQueryInfo codeQueryInfo);

    /**
     * 明细
     * 查询合格证
     * @param codeQueryInfo
     * @return
     */
    public List<CodeQueryInfo> selectProductIdDetail(CodeQueryInfo codeQueryInfo);

    public List<CodeQueryInfo> selectSecurityDetail(CodeQueryInfo codeQueryInfo);

    public List<CodeQueryInfo> selectWIdDetail(CodeQueryInfo codeQueryInfo);

    public List<CodeQueryInfo> selectNIdDetail(CodeQueryInfo codeQueryInfo);




}