/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 微信用户分组管理Entity
 * @author wwhui
 * @version 2016-04-14
 */
public class WxGroup extends DataEntity<WxGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupName;		// 分组用户名
	private Integer groupId;		// 分组的ID微信 定义
	private Integer userCount;		// 组内人数
	
	public WxGroup() {
		super();
	}

	public WxGroup(String id){
		super(id);
	}

	@Length(min=0, max=255, message="分组用户名长度必须介于 0 和 255 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}