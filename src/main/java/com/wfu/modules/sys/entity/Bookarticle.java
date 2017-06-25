/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wfu.common.utils.DateUtils;
import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

import java.util.Date;

/**
 * 图书文章管理Entity
 * @author 徐韵轩
 * @version 2017-06-23
 */
public class Bookarticle extends DataEntity<Bookarticle> {
	
	private static final long serialVersionUID = 1L;
	private String coverimg;		// 封面图片
	private String title;		// 标题
	private String content;		// 內容
	private String author;		// 作者

	private Date createDate;

	private String date;
	
	public Bookarticle() {
		super();
	}

	public Bookarticle(String id){
		super(id);
	}

	@Length(min=0, max=255, message="封面图片长度必须介于 0 和 255 之间")
	public String getCoverimg() {
		return coverimg;
	}

	public void setCoverimg(String coverimg) {
		this.coverimg = coverimg;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		this.date = DateUtils.formatDate(createDate,"yyyy-MM-dd HH:mm:ss");
	}


	@Override
	public Date getCreateDate() {
		return createDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}