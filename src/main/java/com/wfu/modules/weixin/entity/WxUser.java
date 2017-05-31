/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wfu.common.persistence.DataEntity;

/**
 * 微信用户关注Entity
 * @author wwui
 * @version 2016-04-14
 */
public class WxUser extends DataEntity<WxUser> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// openid
	private String nickname;		// nickname
	private Integer sex;		// sex
	private String city;		// 城市
	private String country;		// 国家
	private String province;		// 省
	private String headimgurl;		// 投降
	private Date subscribeTime;		// 关注时间
	private String unionid;		// unionid
	private String remark;		// remark
	private Integer groupid;		// 所在分组
	
	public WxUser() {
		super();
	}

	public WxUser(String id){
		super(id);
	}

	@Length(min=0, max=65, message="openid长度必须介于 0 和 65 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=255, message="nickname长度必须介于 0 和 255 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Length(min=0, max=255, message="城市长度必须介于 0 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="国家长度必须介于 0 和 255 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=255, message="省长度必须介于 0 和 255 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=255, message="投降长度必须介于 0 和 255 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	@Length(min=0, max=255, message="unionid长度必须介于 0 和 255 之间")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	@Length(min=0, max=255, message="remark长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
}