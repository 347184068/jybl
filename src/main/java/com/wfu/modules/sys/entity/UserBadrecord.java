/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 用户不良记录Entity
 * @author 徐韵轩
 * @version 2017-06-06
 */
public class UserBadrecord extends DataEntity<UserBadrecord> {
	
	private static final long serialVersionUID = 1L;
	private String borrowId;		// borrow_id

	private BookBorrow bookBorrow;
	
	public UserBadrecord() {
		super();
	}

	public UserBadrecord(String id){
		super(id);
	}

	@Length(min=0, max=255, message="borrow_id长度必须介于 0 和 255 之间")
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public BookBorrow getBookBorrow() {
		return bookBorrow;
	}

	public void setBookBorrow(BookBorrow bookBorrow) {
		this.bookBorrow = bookBorrow;
	}
}