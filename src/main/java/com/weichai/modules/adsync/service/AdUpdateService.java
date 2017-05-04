/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.adsync.service;

import java.util.Date;
import java.util.List;

import com.weichai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichai.common.persistence.Page;
import com.weichai.common.service.CrudService;
import com.weichai.modules.adsync.entity.AdUpdate;
import com.weichai.modules.adsync.dao.AdUpdateDao;

/**
 * AD更新时间Service
 * @author Panbb
 * @version 2017-02-23
 */
@Service
@Transactional(readOnly = true)
public class AdUpdateService extends CrudService<AdUpdateDao, AdUpdate> {
	@Autowired
	private AdUpdateDao adUpdateDao;

	public AdUpdate get(String id) {
		return super.get(id);
	}
	
	public List<AdUpdate> findList(AdUpdate adUpdate) {
		return super.findList(adUpdate);
	}
	
	public Page<AdUpdate> findPage(Page<AdUpdate> page, AdUpdate adUpdate) {
		return super.findPage(page, adUpdate);
	}
	
	@Transactional(readOnly = false)
	public void save(AdUpdate adUpdate) {
		super.save(adUpdate);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdUpdate adUpdate) {
		super.delete(adUpdate);
	}

	/**
	 * 获取更新事件
	 * @return
	 */
	public String getUpdate(){
		String updateTime = "";
		List<AdUpdate> list = findList(new AdUpdate());
		if(list != null && list.size() > 0 && list.get(0)!=null && list.get(0).getUpDate()!=null){
			updateTime = DateUtils.formatDate(list.get(0).getUpDate(), "yyyyMMddHHmmss");
		}
		return updateTime;
	}

	@Transactional(readOnly = false)
	public void updateInfo(Date nowDate) {
		AdUpdate adUpdate = new AdUpdate();
		adUpdate.setUpDate(nowDate);
		save(adUpdate);
	}
}