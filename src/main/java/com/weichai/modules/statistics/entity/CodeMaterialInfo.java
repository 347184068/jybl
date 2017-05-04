package com.weichai.modules.statistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weichai.common.persistence.Page;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/17.
 */
public class CodeMaterialInfo implements Serializable {
    private String shippingOrder; //发运单号
    private String supplierName; //供应商名称
    private String materialCode; //物料编码
    private String materialName; //物料名称
    private Date shippingTime; //发货时间


    private int productIdNum; //应传产品码数量
    private int outCodeNum;//应传二维码数量
    private int securityNum;//应传安全码数量

    //实际量
    private int realProductIdNum;
    private int realOutCodeNum;
    private int realSecurityNum;


    //  %
    private String productIdPercent;
    private String outCodePercent;
    private String securityPercent;

    private Date startTime;
    private Date endTime;

    private Page<CodeMaterialInfo> page;
    @JsonIgnore
    @XmlTransient
    public Page<CodeMaterialInfo> getPage() {
        if (page == null){
            page = new Page<CodeMaterialInfo>();
        }
        return page;
    }

    public Page<CodeMaterialInfo> setPage(Page<CodeMaterialInfo> page) {
        this.page = page;
        return page;
    }
    public String getShippingOrder() {
        return shippingOrder;
    }

    public void setShippingOrder(String shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public int getProductIdNum() {
        return productIdNum;
    }

    public void setProductIdNum(int productIdNum) {
        this.productIdNum = productIdNum;
    }

    public int getOutCodeNum() {
        return outCodeNum;
    }

    public void setOutCodeNum(int outCodeNum) {
        this.outCodeNum = outCodeNum;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public void setSecurityNum(int securityNum) {
        this.securityNum = securityNum;
    }

    public int getRealProductIdNum() {
        return realProductIdNum;
    }

    public void setRealProductIdNum(int realProductIdNum) {
        this.realProductIdNum = realProductIdNum;
    }

    public int getRealOutCodeNum() {
        return realOutCodeNum;
    }

    public void setRealOutCodeNum(int realOutCodeNum) {
        this.realOutCodeNum = realOutCodeNum;
    }

    public int getRealSecurityNum() {
        return realSecurityNum;
    }

    public void setRealSecurityNum(int realSecurityNum) {
        this.realSecurityNum = realSecurityNum;
    }

    public String getProductIdPercent() {
        return productIdPercent;
    }

    public void setProductIdPercent(String productIdPercent) {
        this.productIdPercent = productIdPercent;
    }

    public String getOutCodePercent() {
        return outCodePercent;
    }

    public void setOutCodePercent(String outCodePercent) {
        this.outCodePercent = outCodePercent;
    }

    public String getSecurityPercent() {
        return securityPercent;
    }

    public void setSecurityPercent(String securityPercent) {
        this.securityPercent = securityPercent;
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
