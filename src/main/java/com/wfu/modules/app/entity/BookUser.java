/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.app.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 管理员信息管理Entity
 * @author 徐韵轩
 * @version 2017-06-25
 */
public class BookUser extends DataEntity<BookUser> {
	
	private static final long serialVersionUID = 1L;
	private String wechatId;		// 微信号
	private String userName;		// 用户名
	private String password;		// 密码
	private String userType;		// 用户类型
	
	public BookUser() {
		super();
	}

	public BookUser(String id){
		super(id);
	}

	@Length(min=0, max=255, message="微信号长度必须介于 0 和 255 之间")
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="密码长度必须介于 0 和 255 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=255, message="用户类型长度必须介于 0 和 255 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}