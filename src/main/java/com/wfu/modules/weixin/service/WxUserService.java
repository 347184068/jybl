/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.weixin.dao.WxUserDao;
import com.wfu.modules.weixin.entity.WxUser;

/**
 * 微信用户关注Service
 * @author wwui
 * @version 2016-04-14
 */
@Service
@Transactional(readOnly = true)
public class WxUserService extends CrudService<WxUserDao, WxUser> {
	@Autowired
	private WxUserDao wxUserDao;

	public WxUser get(String id) {
		return super.get(id);
	}
	
	public List<WxUser> findList(WxUser wxUser) {
		return super.findList(wxUser);
	}
	
	public Page<WxUser> findPage(Page<WxUser> page, WxUser wxUser) {
		return super.findPage(page, wxUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WxUser wxUser) {
		super.save(wxUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxUser wxUser) {
		super.delete(wxUser);
	}

	public WxUser getByOpenId(String  openId) {
		return wxUserDao.findByOpenId(openId);
	}
}