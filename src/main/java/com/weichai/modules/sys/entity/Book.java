/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.weichai.common.persistence.DataEntity;

/**
 * 出版社管理Entity
 * @author 徐韵轩
 * @version 2017-05-08
 */
public class Book extends DataEntity<Book> {
	
	private static final long serialVersionUID = 1L;
	private String bookId;		// book_id
	private String bookIsbn;		// ISBN
	private String bookName;		// 书名
	private String bookImage;		// 图片
	private String bookAuthor;		// 作者
	private String bookCollections;		// 藏书量
	private String bookIntroduction;		// 序
	private String bookDirectory;		// 目录
	private String bookContents;		// 内容
	private String bookGuide;		// 导读
	private String isStatus;		// 是否已删除(上架下架)
	private BookPublisher bookPublisherid;		// 出版社 父类
	private String bookCashpledge;		// 押金

	private String publisherId;
	
	public Book() {
		super();
	}

	public Book(String id){
		super(id);
	}

	public Book(BookPublisher bookPublisherid){
		this.bookPublisherid = bookPublisherid;
	}

	@Length(min=1, max=255, message="book_id长度必须介于 1 和 255 之间")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Length(min=1, max=255, message="ISBN长度必须介于 1 和 255 之间")
	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	
	@Length(min=0, max=255, message="书名长度必须介于 0 和 255 之间")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	@Length(min=0, max=255, message="藏书量长度必须介于 0 和 255 之间")
	public String getBookCollections() {
		return bookCollections;
	}

	public void setBookCollections(String bookCollections) {
		this.bookCollections = bookCollections;
	}
	
	public String getBookIntroduction() {
		return bookIntroduction;
	}

	public void setBookIntroduction(String bookIntroduction) {
		this.bookIntroduction = bookIntroduction;
	}
	
	public String getBookDirectory() {
		return bookDirectory;
	}

	public void setBookDirectory(String bookDirectory) {
		this.bookDirectory = bookDirectory;
	}
	
	public String getBookContents() {
		return bookContents;
	}

	public void setBookContents(String bookContents) {
		this.bookContents = bookContents;
	}
	
	public String getBookGuide() {
		return bookGuide;
	}

	public void setBookGuide(String bookGuide) {
		this.bookGuide = bookGuide;
	}
	
	@Length(min=0, max=255, message="是否已删除(上架下架)长度必须介于 0 和 255 之间")
	public String getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
	}
	
	public BookPublisher getBookPublisherid() {
		return bookPublisherid;
	}

	public void setBookPublisherid(BookPublisher bookPublisherid) {
		this.bookPublisherid = bookPublisherid;
	}
	
	@Length(min=0, max=255, message="押金长度必须介于 0 和 255 之间")
	public String getBookCashpledge() {
		return bookCashpledge;
	}

	public void setBookCashpledge(String bookCashpledge) {
		this.bookCashpledge = bookCashpledge;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
}