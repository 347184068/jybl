/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.app.entity.BookUser;
import com.wfu.modules.app.dao.BookUserDao;

/**
 * 管理员信息管理Service
 * @author 徐韵轩
 * @version 2017-06-25
 */
@Service
@Transactional(readOnly = true)
public class BookUserService extends CrudService<BookUserDao, BookUser> {

	public BookUser get(String id) {
		return super.get(id);
	}
	
	public List<BookUser> findList(BookUser bookUser) {
		return super.findList(bookUser);
	}
	
	public Page<BookUser> findPage(Page<BookUser> page, BookUser bookUser) {
		return super.findPage(page, bookUser);
	}
	
	@Transactional(readOnly = false)
	public void save(BookUser bookUser) {
		super.save(bookUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookUser bookUser) {
		super.delete(bookUser);
	}
	
}