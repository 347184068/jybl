/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichai.common.persistence.Page;
import com.weichai.common.service.CrudService;
import com.weichai.modules.weixin.dao.WxArticleDao;
import com.weichai.modules.weixin.entity.WxArticle;

/**
 * 微信文章管理Service
 * @author wwhui
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class WxArticleService extends CrudService<WxArticleDao, WxArticle> {

	public WxArticle get(String id) {
		return super.get(id);
	}
	
	public List<WxArticle> findList(WxArticle wxArticle) {
		return super.findList(wxArticle);
	}
	
	public Page<WxArticle> findPage(Page<WxArticle> page, WxArticle wxArticle) {
		return super.findPage(page, wxArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(WxArticle wxArticle) {
		super.save(wxArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxArticle wxArticle) {
		super.delete(wxArticle);
	}
	
}