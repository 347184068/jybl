/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.statistics.service;


import com.google.common.collect.Lists;
import com.sun.tools.javac.jvm.Code;
import com.weichai.modules.statistics.dao.CodeStatisticsDao;
import com.weichai.modules.statistics.entity.CodeStatisticInfo;
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
public class CodeStatisticService {
    private static final Logger LOG = Logger.getLogger(CodeStatisticService.class);
    @Autowired
    private CodeStatisticsDao dao;

    public CodeStatisticInfo countCodeUpDownNum(CodeStatisticInfo codeStatisticInfo){

        //1.申请频次
        int  countGrantNum = dao.countGrantNum(codeStatisticInfo);
        //2.申请总数
        int countGrantSum = countGrantNum * 50000;
        //3.上传频次
        int uploadNum = dao.countUploadNum(codeStatisticInfo);
        //4.上传合格数量
        int uploadQualifiedSum =dao.countQualifiedSum(codeStatisticInfo);
        // 5.上传报废数量
        int uploadScrapSum = dao.countScrapSum(codeStatisticInfo);

        codeStatisticInfo.setGrantNum(countGrantNum);
        codeStatisticInfo.setGrantSum(countGrantSum);
        codeStatisticInfo.setUploadNum(uploadNum);
        codeStatisticInfo.setUploadQualifiedSum(uploadQualifiedSum);
        codeStatisticInfo.setUploadScrapSum(uploadScrapSum);

        //6.尚未打印量
        int noPrintSum = 0;
        noPrintSum = countGrantSum - uploadQualifiedSum - uploadScrapSum;
        codeStatisticInfo.setNoPrintSum(noPrintSum);

        //7.打印比率
        String printPercentage = "";
        if(countGrantSum!=0){
            printPercentage = calculatePercent((uploadQualifiedSum +uploadScrapSum),countGrantSum);
        }


        //8.打印合格比率
        String qualifyPrintPercentage ="";
        if(countGrantSum!=0){
            qualifyPrintPercentage = calculatePercent(uploadQualifiedSum,countGrantSum);
        }



        codeStatisticInfo.setNoPrintSum(noPrintSum);
        codeStatisticInfo.setPrintPercentage(printPercentage);
        codeStatisticInfo.setQualifyPrintPercentage(qualifyPrintPercentage);
        return codeStatisticInfo;



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



