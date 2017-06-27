package com.wfu.modules.app.entity;

import com.wfu.modules.sys.entity.Book;

import java.util.List;

/**
 * Created by coco on 2017/6/26.
 */
public class UserInfoCustomer {
    private String username;
    private List<BookCustomer> listbook;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public List<BookCustomer> getListbook() {
        return listbook;
    }
    public void setListbook(List<BookCustomer> listbook) {
        this.listbook = listbook;
    }



}
