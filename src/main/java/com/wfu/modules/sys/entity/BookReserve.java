/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 图书预定管理Entity
 * @author 徐韵轩
 * @version 2017-06-07
 */
public class BookReserve extends DataEntity<BookReserve> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String bookId;		// 图书ID
	private String reserveTime;		// 预定时间
	private String pickTime;		// 取书时间
	private String isPick;		// 是否取书
	private String isOvertime;		// 是否过期


	private String userName;

	private String bookName;

	private String bookCollections;
	
	public BookReserve() {
		super();
	}

	public BookReserve(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户ID长度必须介于 0 和 255 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="图书ID长度必须介于 0 和 255 之间")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Length(min=0, max=255, message="预定时间长度必须介于 0 和 255 之间")
	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	
	@Length(min=0, max=255, message="取书时间长度必须介于 0 和 255 之间")
	public String getPickTime() {
		return pickTime;
	}

	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
	
	@Length(min=0, max=1, message="是否取书长度必须介于 0 和 1 之间")
	public String getIsPick() {
		return isPick;
	}

	public void setIsPick(String isPick) {
		this.isPick = isPick;
	}
	
	@Length(min=0, max=1, message="是否过期长度必须介于 0 和 1 之间")
	public String getIsOvertime() {
		return isOvertime;
	}

	public void setIsOvertime(String isOvertime) {
		this.isOvertime = isOvertime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookCollections() {
		return bookCollections;
	}

	public void setBookCollections(String bookCollections) {
		this.bookCollections = bookCollections;
	}
}