package com.weichai.modules.filegen.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tepusoft on 2017/3/14.
 */
public class InOutDetailUsed implements Serializable {
    private String id;
    private String inCode;//内码
    private String outCode; //外码
    private String w;
    private String n;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date   updateDate;
    private String remarks;
    private int status;

    private int imtStatus; //处理标记

    private int s;
    private String baoCaiType;//包材厂类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
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

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public int getImtStatus() {
        return imtStatus;
    }

    public void setImtStatus(int imtStatus) {
        this.imtStatus = imtStatus;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getBaoCaiType() {
        return baoCaiType;
    }

    public void setBaoCaiType(String baoCaiType) {
        this.baoCaiType = baoCaiType;
    }
}
