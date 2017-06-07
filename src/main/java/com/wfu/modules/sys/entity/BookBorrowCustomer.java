package com.wfu.modules.sys.entity;

/**
 * @Author XuYunXuan
 * @Date 2017/6/3 16:25
 */
public class BookBorrowCustomer extends BookBorrow {

    //书名
    private String bookName;
    //ISBN
    private String bookIsbn;

    //用户名
    private String bookUserName;

    //用户微信ID
    private String bookUserWechatId;

    public BookBorrowCustomer() {
        super();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookUserName() {
        return bookUserName;
    }

    public void setBookUserName(String bookUserName) {
        this.bookUserName = bookUserName;
    }

    public String getBookUserWechatId() {
        return bookUserWechatId;
    }

    public void setBookUserWechatId(String bookUserWechatId) {
        this.bookUserWechatId = bookUserWechatId;
    }
}
