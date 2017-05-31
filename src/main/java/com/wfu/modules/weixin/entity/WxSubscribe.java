/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 关注回复Entity
 * @author wwhui
 * @version 2016-04-07
 */
public class WxSubscribe extends DataEntity<WxSubscribe> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 标题
	private String msgType;		// 类型
	private String msgId;		// 消息的ID
	private String token;		// token
	
	public WxSubscribe() {
		super();
	}

	public WxSubscribe(String id){
		super(id);
	}

	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="类型长度必须介于 0 和 255 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=65, message="消息的ID长度必须介于 0 和 65 之间")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@Length(min=0, max=65, message="token长度必须介于 0 和 65 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}