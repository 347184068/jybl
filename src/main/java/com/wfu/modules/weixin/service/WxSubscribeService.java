/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.weixin.dao.WxSubscribeDao;
import com.wfu.modules.weixin.entity.WxSubscribe;

/**
 * 关注回复Service
 * @author wwhui
 * @version 2016-04-07
 */
@Service
@Transactional(readOnly = true)
public class WxSubscribeService extends CrudService<WxSubscribeDao, WxSubscribe> {

	public WxSubscribe get(String id) {
		return super.get(id);
	}
	
	public List<WxSubscribe> findList(WxSubscribe wxSubscribe) {
		return super.findList(wxSubscribe);
	}
	
	public Page<WxSubscribe> findPage(Page<WxSubscribe> page, WxSubscribe wxSubscribe) {
		return super.findPage(page, wxSubscribe);
	}
	
	@Transactional(readOnly = false)
	public void save(WxSubscribe wxSubscribe) {
		super.save(wxSubscribe);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxSubscribe wxSubscribe) {
		super.delete(wxSubscribe);
	}
	
}