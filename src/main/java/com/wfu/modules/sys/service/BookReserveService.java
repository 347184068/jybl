/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.BookReserve;
import com.wfu.modules.sys.dao.BookReserveDao;

/**
 * 图书预定管理Service
 * @author 徐韵轩
 * @version 2017-06-07
 */
@Service
@Transactional(readOnly = true)
public class BookReserveService extends CrudService<BookReserveDao, BookReserve> {

	public BookReserve get(String id) {
		return super.get(id);
	}
	
	public List<BookReserve> findList(BookReserve bookReserve) {
		return super.findList(bookReserve);
	}
	
	public Page<BookReserve> findPage(Page<BookReserve> page, BookReserve bookReserve) {
		return super.findPage(page, bookReserve);
	}
	
	@Transactional(readOnly = false)
	public void save(BookReserve bookReserve) {
		super.save(bookReserve);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookReserve bookReserve) {
		super.delete(bookReserve);
	}

	@Transactional(readOnly = false)
	public void update(BookReserve bookReserve) {
		dao.update(bookReserve);
	}
	
}