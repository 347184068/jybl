package com.weichai.modules.statistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weichai.common.persistence.Page;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/17.
 */
public class CodeQueryInfo implements Serializable {
    private String codeType; //码类型
    private String receiveName;  //收货单位
    private String supplierName; //供应商名称
    private String baoCaiType;//包材厂商

    private String code; //条码
    private int queryNum; //查询频次
    private int querySum; //查询总条数
    private Date shippingTime; //发货时间
    private Date startTime;
    private Date endTime;

    private Page<CodeQueryInfo> page;


    public int getQuerySum() {
        return querySum;
    }

    public void setQuerySum(int querySum) {
        this.querySum = querySum;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBaoCaiType() {
        return baoCaiType;
    }

    public void setBaoCaiType(String baoCaiType) {
        this.baoCaiType = baoCaiType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQueryNum() {
        return queryNum;
    }

    public void setQueryNum(int queryNum) {
        this.queryNum = queryNum;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
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

    @JsonIgnore
    @XmlTransient
    public Page<CodeQueryInfo> getPage() {
        if (page == null){
            page = new Page<CodeQueryInfo>();
        }
        return page;
    }

    public Page<CodeQueryInfo> setPage(Page<CodeQueryInfo> page) {
        this.page = page;
        return page;
    }
}
