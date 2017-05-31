/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.adsync.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wfu.common.persistence.DataEntity;

/**
 * AD更新时间Entity
 * @author Panbb
 * @version 2017-02-23
 */
public class AdUpdate extends DataEntity<AdUpdate> {
	
	private static final long serialVersionUID = 1L;
	private Date upDate;		// up_date
	
	public AdUpdate() {
		super();
	}

	public AdUpdate(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	
}