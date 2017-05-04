package com.weichai.modules.validatecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weichai.common.persistence.Page;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/16.
 */
public class InOutProduct implements Serializable {
    private String id;
    private String outCode; //外码
    private String inCode; //内码
    private String productId;//产品码
    private int inNum; //内码扫描次数
    private int outNum; //外码扫描次数
    private int productNum; //
    private int securityNum;


    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date   updateDate;
    private String remarks;
    private int status;
    private String security;



    private Page<InOutProduct> page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }


    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public void setSecurityNum(int securityNum) {
        this.securityNum = securityNum;
    }

    @JsonIgnore
    @XmlTransient
    public Page<InOutProduct> getPage() {
        if (page == null){
            page = new Page<InOutProduct>();
        }
        return page;
    }

    public Page<InOutProduct> setPage(Page<InOutProduct> page) {
        this.page = page;
        return page;
    }


    @Override
    public String toString() {
        return "InOutProduct{" +
                "id='" + id + '\'' +
                ", outCode='" + outCode + '\'' +
                ", inCode='" + inCode + '\'' +
                ", productId='" + productId + '\'' +
                ", inNum=" + inNum +
                ", outNum=" + outNum +
                ", productNum=" + productNum +
                ", securityNum=" + securityNum +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                ", security='" + security + '\'' +
                ", page=" + page +
                '}';
    }
}
