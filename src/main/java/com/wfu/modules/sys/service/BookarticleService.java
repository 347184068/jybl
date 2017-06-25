/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.Bookarticle;
import com.wfu.modules.sys.dao.BookarticleDao;

/**
 * 图书文章管理Service
 * @author 徐韵轩
 * @version 2017-06-23
 */
@Service
@Transactional(readOnly = true)
public class BookarticleService extends CrudService<BookarticleDao, Bookarticle> {

	public Bookarticle get(String id) {
		return super.get(id);
	}
	
	public List<Bookarticle> findList(Bookarticle bookarticle) {
		return super.findList(bookarticle);
	}
	
	public Page<Bookarticle> findPage(Page<Bookarticle> page, Bookarticle bookarticle) {
		return super.findPage(page, bookarticle);
	}
	
	@Transactional(readOnly = false)
	public void save(Bookarticle bookarticle) {
		super.save(bookarticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(Bookarticle bookarticle) {
		super.delete(bookarticle);
	}
	
}