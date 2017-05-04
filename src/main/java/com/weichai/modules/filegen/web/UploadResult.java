package com.weichai.modules.filegen.web;

import java.io.Serializable;

/**
 * Created by tepusoft on 2017/3/30.
 */
public class UploadResult implements Serializable {
    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
