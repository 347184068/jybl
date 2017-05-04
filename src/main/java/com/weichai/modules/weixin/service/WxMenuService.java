/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichai.common.service.CrudService;
import com.weichai.modules.weixin.dao.WxMenuDao;
import com.weichai.modules.weixin.entity.WxMenu;

/**
 * 微信菜单管理Service
 * @author wwhui
 * @version 2016-03-27
 */
@Service
@Transactional(readOnly = true)
public class WxMenuService extends CrudService<WxMenuDao, WxMenu> {

	public WxMenu get(String id) {
		return super.get(id);
	}
	
	public List<WxMenu> findList(WxMenu wxMenu) {
		return super.findList(wxMenu);
	}

	public List<WxMenu> findAllList(WxMenu wxMenu){
		return super.findAllList(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMenu wxMenu) {
		super.save(wxMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMenu wxMenu) {
		super.delete(wxMenu);
	}
	
}