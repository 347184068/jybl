/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.BookPublisher;
import com.wfu.modules.sys.dao.BookPublisherDao;

/**
 * 出版社管理Service
 * @author 徐韵轩
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class BookPublisherService extends CrudService<BookPublisherDao, BookPublisher> {

	public BookPublisher get(String id) {
		return super.get(id);
	}
	
	public List<BookPublisher> findList(BookPublisher bookPublisher) {
		return super.findList(bookPublisher);
	}
	
	public Page<BookPublisher> findPage(Page<BookPublisher> page, BookPublisher bookPublisher) {
		return super.findPage(page, bookPublisher);
	}
	
	@Transactional(readOnly = false)
	public void save(BookPublisher bookPublisher) {
		super.save(bookPublisher);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookPublisher bookPublisher) {
		super.delete(bookPublisher);
	}
	
}