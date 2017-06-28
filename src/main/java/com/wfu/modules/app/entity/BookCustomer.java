package com.wfu.modules.app.entity;

/**
 * Created by coco on 2017/6/26.
 */
public class BookCustomer {
    private String bookIsbn;		// ISBN
    private String bookName;		// 书名
    private String bookImage;		// 封面
    private String bookAuthor;		// 作者
    private String bookPublisherid;

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisherid() {
        return bookPublisherid;
    }

    public void setBookPublisherid(String bookPublisherid) {
        this.bookPublisherid = bookPublisherid;
    }


    @Override
    public String toString() {
        return "BookCustomer{" +
                "bookIsbn='" + bookIsbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisherid='" + bookPublisherid + '\'' +
                '}';
    }
}
