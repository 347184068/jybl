/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.weichai.common.persistence.DataEntity;

/**
 * 关键字恢复管理Entity
 * @author wwhui
 * @version 2016-04-07
 */
public class WxAutoKeyword extends DataEntity<WxAutoKeyword> {
	
	private static final long serialVersionUID = 1L;
	private String keyWord;		// 关键字
	private String name;		// 标题
	private String msgId;		// msg_id
	private String msgType;		// 类型
	private String token;		// token
	
	public WxAutoKeyword() {
		super();
	}

	public WxAutoKeyword(String id){
		super(id);
	}

	@Length(min=0, max=65, message="关键字长度必须介于 0 和 65 之间")
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=65, message="msg_id长度必须介于 0 和 65 之间")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@Length(min=0, max=255, message="类型长度必须介于 0 和 255 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@Length(min=0, max=255, message="token长度必须介于 0 和 255 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}