package com.weichai.modules.statistics.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/4/13.
 */
public class CodeStatisticInfo implements Serializable {
    private String baoCaiType;
    private int grantNum;//申请次数
    private int grantSum;//申请频次
    private int uploadNum;//打印回传频次
    private int uploadScrapSum;//回传报废数量
    private int uploadQualifiedSum;//回传合格数量
    private int noPrintSum;//尚未打印量
    private String printPercentage;//打印比率
    private String qualifyPrintPercentage;//打印合格比率

    private Date startTime;
    private Date endTime;

    public String getBaoCaiType() {
        return baoCaiType;
    }

    public void setBaoCaiType(String baoCaiType) {
        this.baoCaiType = baoCaiType;
    }

    public int getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(int grantNum) {
        this.grantNum = grantNum;
    }

    public int getGrantSum() {
        return grantSum;
    }

    public void setGrantSum(int grantSum) {
        this.grantSum = grantSum;
    }

    public int getUploadNum() {
        return uploadNum;
    }

    public void setUploadNum(int uploadNum) {
        this.uploadNum = uploadNum;
    }

    public int getUploadScrapSum() {
        return uploadScrapSum;
    }

    public void setUploadScrapSum(int uploadScrapSum) {
        this.uploadScrapSum = uploadScrapSum;
    }

    public int getUploadQualifiedSum() {
        return uploadQualifiedSum;
    }

    public void setUploadQualifiedSum(int uploadQualifiedSum) {
        this.uploadQualifiedSum = uploadQualifiedSum;
    }

    public int getNoPrintSum() {
        return noPrintSum;
    }

    public void setNoPrintSum(int noPrintSum) {
        this.noPrintSum = noPrintSum;
    }

    public String getPrintPercentage() {
        return printPercentage;
    }

    public void setPrintPercentage(String printPercentage) {
        this.printPercentage = printPercentage;
    }

    public String getQualifyPrintPercentage() {
        return qualifyPrintPercentage;
    }

    public void setQualifyPrintPercentage(String qualifyPrintPercentage) {
        this.qualifyPrintPercentage = qualifyPrintPercentage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
