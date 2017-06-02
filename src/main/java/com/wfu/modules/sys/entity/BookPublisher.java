/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 出版社管理Entity
 * @author 徐韵轩
 * @version 2017-06-02
 */
public class BookPublisher extends DataEntity<BookPublisher> {
	
	private static final long serialVersionUID = 1L;
	private String publisherId;		// ID
	private String publisherName;		// 出版社名称
	
	public BookPublisher() {
		super();
	}

	public BookPublisher(String id){
		super(id);
	}

	@Length(min=1, max=255, message="ID长度必须介于 1 和 255 之间")
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	
	@Length(min=0, max=255, message="出版社名称长度必须介于 0 和 255 之间")
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
}