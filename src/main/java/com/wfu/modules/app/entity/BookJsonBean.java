package com.wfu.modules.app.entity;
import java.util.List;

/**
 * Auto-generated: 2017-06-25 20:4:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BookJsonBean {

    private String userId;
    private List<BookList> bookList;
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setBookList(List<BookList> bookList) {
        this.bookList = bookList;
    }
    public List<BookList> getBookList() {
        return bookList;
    }

}