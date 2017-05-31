/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.weixin.dao.WxArticleCollectDao;
import com.wfu.modules.weixin.entity.WxArticleCollect;

/**
 * 构建多图文消息列表Service
 * @author wwhui
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class WxArticleCollectService extends CrudService<WxArticleCollectDao, WxArticleCollect> {

	public WxArticleCollect get(String id) {
		return super.get(id);
	}
	
	public List<WxArticleCollect> findList(WxArticleCollect wxArticleCollect) {
		return super.findList(wxArticleCollect);
	}
	
	public Page<WxArticleCollect> findPage(Page<WxArticleCollect> page, WxArticleCollect wxArticleCollect) {
		return super.findPage(page, wxArticleCollect);
	}
	
	@Transactional(readOnly = false)
	public void save(WxArticleCollect wxArticleCollect) {
		super.save(wxArticleCollect);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxArticleCollect wxArticleCollect) {
		super.delete(wxArticleCollect);
	}
	
}