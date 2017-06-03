/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.dao.BookDao;

/**
 * 书籍管理Service
 * @author 徐韵轩
 * @version 2017-06-02
 */
@Service
@Transactional(readOnly = true)
public class BookService extends CrudService<BookDao, Book> {

	@Autowired
	private BookDao bookDao;

	public Book get(String id) {
		return super.get(id);
	}
	
	public List<Book> findList(Book book) {
		return super.findList(book);
	}
	
	public Page<Book> findPage(Page<Book> page, Book book) {
		return super.findPage(page, book);
	}
	
	@Transactional(readOnly = false)
	public void save(Book book) {
		super.save(book);
	}
	
	@Transactional(readOnly = false)
	public void delete(Book book) {
		super.delete(book);
	}


	public Book selectBookByIsbn(String isbn){
		return bookDao.selectBookByIsbn(isbn);
	}
	
}