/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.service;


import com.google.common.collect.Lists;
import com.weichai.common.persistence.Page;
import com.weichai.common.utils.StringUtils;
import com.weichai.modules.statistics.dao.CodeMaterialDao;
import com.weichai.modules.statistics.dao.CodeQueryDao;
import com.weichai.modules.statistics.entity.CodeMaterialInfo;
import com.weichai.modules.statistics.entity.CodeQueryInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 防伪码查询情况Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class CodeQueryInfoService {
    private static final Logger LOG = Logger.getLogger(CodeQueryInfoService.class);
    @Autowired
    private CodeQueryDao dao;

    public Page<CodeQueryInfo> findPage(Page<CodeQueryInfo> page, CodeQueryInfo info){
        info.setPage(page);
        List<CodeQueryInfo> codeQueryInfoList = getCodeQueryInfoList(info);
        page.setList(codeQueryInfoList);
        if(codeQueryInfoList.size()>0){
            page.setCount(codeQueryInfoList.size());
        }

        return page;
    }

    public Page<CodeQueryInfo> findPageDetail(Page<CodeQueryInfo> page, CodeQueryInfo info){
        info.setPage(page);
        List<CodeQueryInfo> codeQueryInfoList = getCodeQueryDetailList(info);
        page.setList(codeQueryInfoList);
        if(codeQueryInfoList.size()>0){
            page.setCount(codeQueryInfoList.size());
        }
        return page;
    }


    //汇总记录
    public List<CodeQueryInfo> getCodeQueryInfoList(CodeQueryInfo codeQueryInfo){
        List<CodeQueryInfo> codeQueryInfoList = Lists.newArrayList();
        if(StringUtils.isBlank(codeQueryInfo.getCodeType())){
            List<CodeQueryInfo> infoList = Lists.newArrayList();
            infoList = dao.selectProductIdQuery(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectSecurityQuery(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectWIdQuery(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectNIdQuery(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);
        }
        else{
            String codeType = codeQueryInfo.getCodeType();
            if(codeType.equals("合格证")){
                codeQueryInfoList = dao.selectProductIdQuery(codeQueryInfo);
            }
            if(codeType.equals("防伪码")){
                codeQueryInfoList = dao.selectSecurityQuery(codeQueryInfo);
            }
            if(codeType.equals("二维码")){
                List<CodeQueryInfo> infoList = Lists.newArrayList();
                infoList = dao.selectWIdQuery(codeQueryInfo);
                codeQueryInfoList.addAll(infoList);

                infoList = dao.selectNIdQuery(codeQueryInfo);
                codeQueryInfoList.addAll(infoList);
            }
        }
        return codeQueryInfoList;
    }


    public List<CodeQueryInfo> getCodeQueryDetailList(CodeQueryInfo codeQueryInfo){
        List<CodeQueryInfo> codeQueryInfoList = Lists.newArrayList();
        if(StringUtils.isBlank(codeQueryInfo.getCodeType())){
            List<CodeQueryInfo> infoList = Lists.newArrayList();
            infoList = dao.selectProductIdDetail(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectSecurityDetail(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectWIdDetail(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);

            infoList = dao.selectNIdDetail(codeQueryInfo);
            codeQueryInfoList.addAll(infoList);
        }
        else{
            String codeType = codeQueryInfo.getCodeType();
            if(codeType.equals("合格证")){
                codeQueryInfoList = dao.selectProductIdDetail(codeQueryInfo);
            }
            if(codeType.equals("防伪码")){
                codeQueryInfoList = dao.selectSecurityDetail(codeQueryInfo);
            }
            if(codeType.equals("二维码")){
                List<CodeQueryInfo> infoList = Lists.newArrayList();
                infoList = dao.selectWIdDetail(codeQueryInfo);
                codeQueryInfoList.addAll(infoList);

                infoList = dao.selectNIdDetail(codeQueryInfo);
                codeQueryInfoList.addAll(infoList);
            }
        }
        return codeQueryInfoList;
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



