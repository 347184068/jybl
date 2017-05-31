/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.weixin.dao.WxAutoKeywordDao;
import com.wfu.modules.weixin.entity.WxAutoKeyword;

/**
 * 关键字恢复管理Service
 * @author wwhui
 * @version 2016-04-07
 */
@Service
@Transactional(readOnly = true)
public class WxAutoKeywordService extends CrudService<WxAutoKeywordDao, WxAutoKeyword> {

	public WxAutoKeyword get(String id) {
		return super.get(id);
	}
	
	public List<WxAutoKeyword> findList(WxAutoKeyword wxAutoKeyword) {
		return super.findList(wxAutoKeyword);
	}
	
	public Page<WxAutoKeyword> findPage(Page<WxAutoKeyword> page, WxAutoKeyword wxAutoKeyword) {
		return super.findPage(page, wxAutoKeyword);
	}
	
	@Transactional(readOnly = false)
	public void save(WxAutoKeyword wxAutoKeyword) {
		super.save(wxAutoKeyword);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAutoKeyword wxAutoKeyword) {
		super.delete(wxAutoKeyword);
	}

	/**
	 * 关键词回复
	 * @param key
	 * @return
     */
	public WxAutoKeyword findByKey(String key) {
		List<WxAutoKeyword>	keyWorldList=dao.findByKey(key);
		 if(keyWorldList!=null&&keyWorldList.size()>0){
			 return keyWorldList.get(0);
		 }
		return  null;
	}
}