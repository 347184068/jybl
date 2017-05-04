package com.weichai.modules.validatecode.service;

import com.weichai.modules.validatecode.entity.InOutProduct;

import java.io.Serializable;

/**
 * Created by tepusoft on 2017/3/23.
 */
public class ValidateResult implements Serializable {
    private String count;
    private InOutProduct inOutProduct;
    private boolean flag;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public InOutProduct getInOutProduct() {
        return inOutProduct;
    }

    public void setInOutProduct(InOutProduct inOutProduct) {
        this.inOutProduct = inOutProduct;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
