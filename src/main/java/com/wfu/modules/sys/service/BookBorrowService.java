/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import com.wfu.modules.sys.entity.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.BookBorrow;
import com.wfu.modules.sys.dao.BookBorrowDao;

/**
 * 借书信息管理Service
 * @author 徐韵轩
 * @version 2017-06-03
 */
@Service
@Transactional(readOnly = true)
public class BookBorrowService extends CrudService<BookBorrowDao, BookBorrow> {

	@Autowired
	private BookBorrowDao bookBorrowDao;

	public BookBorrow get(String id) {
		return super.get(id);
	}
	
	public List<BookBorrow> findList(BookBorrow bookBorrow) {
		return super.findList(bookBorrow);
	}
	
	public Page<BookBorrow> findPage(Page<BookBorrow> page, BookBorrow bookBorrow) {
		return super.findPage(page, bookBorrow);
	}
	
	@Transactional(readOnly = false)
	public void save(BookBorrow bookBorrow) {
		super.save(bookBorrow);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookBorrow bookBorrow) {
		super.delete(bookBorrow);
	}

	@Transactional(readOnly = false)
	public void update(BookBorrow bookBorrow){
		dao.update(bookBorrow);
	}
}