package com.weichai.modules.validatecode.service;

import com.weichai.modules.validatecode.entity.InOutProduct;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/23.
 */
public class ScanReturnResult implements Serializable {
    private String msg; //返回信息
    private String scanCode; //扫描码
    private String materialCode;//物料编码
    private String materialName; //物料名称
    private String purchaseOrder; //订单号
    private String shippingOrder;// 发运单号
    private String shippingTime; // 发货时间
    private String supplierName; // 经销商名称

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
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

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
