/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 微信菜单管理Entity
 * @author wwhui
 * @version 2016-03-27
 */
public class WxMenu extends DataEntity<WxMenu> {
	public static final String ROOT="1";
	private static final long serialVersionUID = 1L;
	private String name;		// 菜单名
	private WxMenu parent;
	private String type;		// 类型
	private String url;		// url
	private String keyValue;		// key_value
	private String sort;		// sort
	private String level;		// level
	private String media_id;
	private String menuType; //菜单类型  1 --公众号 ； 2 --企业号；
	public WxMenu() {
		super();
	}

	public WxMenu(String id){
		super(id);
	}

	public WxMenu getParent() {
		return parent;
	}

	public void setParent(WxMenu parent) {
		this.parent=parent;
	}

	@Length(min=0, max=255, message="菜单名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=64, message="key_value长度必须介于 0 和 64 之间")
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	

	
	@Length(min=0, max=11, message="level长度必须介于 0 和 11 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
}