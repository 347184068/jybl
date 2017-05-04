package com.weichai.common.utils;

/**
 * 返回结果
 */
public class ResData {

    private boolean success = false;
    private String msg = "";
    private Object data = null;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
