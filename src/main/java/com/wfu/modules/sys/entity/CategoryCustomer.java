package com.wfu.modules.sys.entity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 用于图书查询
 * @Author XuYunXuan
 * @Date 2017/6/2 19:56
 */
public class CategoryCustomer {

    private String categoryId;

    private String categoryName;



    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
