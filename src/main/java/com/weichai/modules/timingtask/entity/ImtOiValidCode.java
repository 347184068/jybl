package com.weichai.modules.timingtask.entity;

import java.io.Serializable;

/**
 * Created by tepusoft on 2017/3/22.
 */
public class ImtOiValidCode implements Serializable {
    private String id;
    private String outCode;
    private String inCode;
    private String security;
    private String imtTime; //同步时间
    private int imtStatus; //同步标识

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

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getImtTime() {
        return imtTime;
    }

    public void setImtTime(String imtTime) {
        this.imtTime = imtTime;
    }

    public int getImtStatus() {
        return imtStatus;
    }

    public void setImtStatus(int imtStatus) {
        this.imtStatus = imtStatus;
    }
}
