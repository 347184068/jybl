/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.service;


import com.google.common.collect.Lists;
import com.weichai.common.persistence.Page;
import com.weichai.modules.statistics.dao.CodeMaterialDao;
import com.weichai.modules.statistics.dao.CodeStatisticsDao;
import com.weichai.modules.statistics.entity.CodeMaterialInfo;
import com.weichai.modules.statistics.entity.CodeStatisticInfo;
import com.weichai.modules.validatecode.entity.InOutProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 防伪码申请及打印情况Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class CodeMaterialService {
    private static final Logger LOG = Logger.getLogger(CodeMaterialService.class);
    @Autowired
    private CodeMaterialDao dao;


    public List<CodeMaterialInfo>  getSupplierList(){
        List<CodeMaterialInfo> supplierList = Lists.newArrayList();
        supplierList = dao.getSupplierList();
        return supplierList;
    }

    public Page<CodeMaterialInfo> findPage(Page<CodeMaterialInfo> page, CodeMaterialInfo info){
        info.setPage(page);
        List<CodeMaterialInfo> codeMaterialInfoList = getCodeMaterialInfoList(info);
        page.setList(codeMaterialInfoList);
        return page;
    }

    public List<CodeMaterialInfo> getCodeMaterialInfoList(CodeMaterialInfo codeMaterialInfo){
        List<CodeMaterialInfo> codeMaterialInfoList = Lists.newArrayList();
        codeMaterialInfoList = dao.queryCodeMaterialList(codeMaterialInfo);
        int len = codeMaterialInfoList.size();
        for(int i=0;i<len;i++){
            CodeMaterialInfo info = codeMaterialInfoList.get(i);
            int productIdNum = info.getProductIdNum();
            if(productIdNum!=0){
                String pIdPercent = calculatePercent(info.getRealProductIdNum(),productIdNum);
                info.setProductIdPercent(pIdPercent);
            }
            int outCodeNum = info.getOutCodeNum();
            if(outCodeNum!=0){
                String outCodePercent = calculatePercent(info.getRealOutCodeNum(),outCodeNum);
                info.setOutCodePercent(outCodePercent);
            }
            int securityNum = info.getSecurityNum();
            if(securityNum!=0){
                String securityPercent = calculatePercent(info.getSecurityNum(),outCodeNum);
                info.setSecurityPercent(securityPercent);
            }
        }
        return codeMaterialInfoList;
    }

    public String calculatePercent(int x,int y){
        String percentage="";

        double xPer = x*1.0;
        double yPer = y*1.0;
        double percent = xPer/yPer;
        DecimalFormat df = new DecimalFormat("##0.00%");
        percentage = df.format(percent);
        return percentage;
    };



}



