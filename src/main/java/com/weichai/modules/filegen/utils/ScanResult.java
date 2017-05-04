package com.weichai.modules.filegen.utils;

import java.io.Serializable;

/**
 * Created by tepusoft on 2017/3/16.
 */
public class ScanResult implements Serializable {
    private boolean flag;
    private String msg;
    private int code;  //1.成功  2.失败
    private String message;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
