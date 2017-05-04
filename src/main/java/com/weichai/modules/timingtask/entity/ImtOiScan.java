package com.weichai.modules.timingtask.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/22.
 */
public class ImtOiScan implements Serializable {
    private String id;
    private String purchaseOrder; //采购订单
    private String shippingOrder; //发运单号
    private String customerCode; //采购单位协议号
    private String customerName; //采购单位名称
    private String supplierName; //供应商名称
    private String receiveName; //接收单位名称
    private String materialCode; //物料编码
    private String materialName; //物料名称
    private String productId; //产品码
    private String security;  //防伪码
    private String outCode; //外码
    private Date shippingTime; //发货时间
    private Date imtTime; //同步时间

    private int imtStatus; //同步标识
    
    private String inCode;
    private int inNum;
    private int outNum;

    private int productIdNum;
    private int wIdNum;
    private int securityNum;

    private String baoCaiType;

    public String getBaoCaiType() {
        return baoCaiType;
    }

    public void setBaoCaiType(String baoCaiType) {
        this.baoCaiType = baoCaiType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getShippingOrder() {
        return shippingOrder;
    }

    public void setShippingOrder(String shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Date getImtTime() {
        return imtTime;
    }

    public void setImtTime(Date imtTime) {
        this.imtTime = imtTime;
    }

    public int getImtStatus() {
        return imtStatus;
    }

    public void setImtStatus(int imtStatus) {
        this.imtStatus = imtStatus;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public int getInNum() {
        return inNum;
    }

    public void setInNum(int inNum) {
        this.inNum = inNum;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    public int getProductIdNum() {
        return productIdNum;
    }

    public void setProductIdNum(int productIdNum) {
        this.productIdNum = productIdNum;
    }

    public int getwIdNum() {
        return wIdNum;
    }

    public void setwIdNum(int wIdNum) {
        this.wIdNum = wIdNum;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public void setSecurityNum(int securityNum) {
        this.securityNum = securityNum;
    }
}
