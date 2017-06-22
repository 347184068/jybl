package com.wfu.modules.weixin.entity;

/**
 * @Author XuYunXuan
 * @Date 2017/6/22 8:19
 */
public class JsonData<T> {

    private String status;

    private T entity;

    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
