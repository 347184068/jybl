/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 图书推荐Entity
 * @author 徐韵轩
 * @version 2017-06-03
 */
public class BookRecommend extends DataEntity<BookRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String isbn;		// ISBN
	private String bookName;		// 书名
	private String bookAuthor;		// 作者
	
	public BookRecommend() {
		super();
	}

	public BookRecommend(String id){
		super(id);
	}

	@Length(min=1, max=255, message="ISBN长度必须介于 1 和 255 之间")
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Length(min=0, max=255, message="书名长度必须介于 0 和 255 之间")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
}