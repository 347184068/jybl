package com.weichai.modules.filegen.web;

import java.io.Serializable;

/**
 * Created by tepusoft on 2017/3/30.
 */
public class GrantResult implements Serializable {
    private String msg;
    private int code;
    private String message;

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
