/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 微信文章管理Entity
 * @author wwhui
 * @version 2016-03-28
 */
public class WxArticle extends DataEntity<WxArticle> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// title
	private String thumbMediaId;		// 资源id
	private String author;		// 作者
	private String digest;		// 摘要
	private String showCoverPic;		// 显示封面
	private String content;		// 内容
	private String contentSourceUrl;		// 原文链接
	private String token;		// 未来作为区分类别
	private String sort;		// 排序
	
	public WxArticle() {
		super();
	}

	public WxArticle(String id){
		super(id);
	}

	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="资源id长度必须介于 0 和 64 之间")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Length(min=0, max=255, message="摘要长度必须介于 0 和 255 之间")
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@Length(min=0, max=255, message="原文链接长度必须介于 0 和 255 之间")
	public String getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="原文链接长度必须介于 0 和 255 之间")
	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}
	
	@Length(min=0, max=64, message="未来作为区分类别长度必须介于 0 和 64 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}