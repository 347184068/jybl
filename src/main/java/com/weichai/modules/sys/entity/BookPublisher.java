/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.weichai.common.persistence.DataEntity;

/**
 * 出版社管理Entity
 * @author 徐韵轩
 * @version 2017-05-08
 */
public class BookPublisher extends DataEntity<BookPublisher> {
	
	private static final long serialVersionUID = 1L;
	private String publisherId;		// publisher_id
	private String publisherName;		// 出版社
	private String isdelete;		// isdelete
	private List<Book> bookList = Lists.newArrayList();		// 子表列表
	
	public BookPublisher() {
		super();
	}

	public BookPublisher(String id){
		super(id);
	}

	@Length(min=1, max=255, message="publisher_id长度必须介于 1 和 255 之间")
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	
	@Length(min=0, max=255, message="出版社长度必须介于 0 和 255 之间")
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	@Length(min=0, max=1, message="isdelete长度必须介于 0 和 1 之间")
	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
}