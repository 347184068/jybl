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
import com.wfu.modules.sys.entity.BookRecommend;
import com.wfu.modules.sys.dao.BookRecommendDao;

/**
 * 图书推荐Service
 * @author 徐韵轩
 * @version 2017-06-03
 */
@Service
@Transactional(readOnly = true)
public class BookRecommendService extends CrudService<BookRecommendDao, BookRecommend> {

	@Autowired
	private BookRecommendDao bookRecommendDao;

	public BookRecommend get(String id) {
		return super.get(id);
	}
	
	public List<BookRecommend> findList(BookRecommend bookRecommend) {
		return super.findList(bookRecommend);
	}
	
	public Page<BookRecommend> findPage(Page<BookRecommend> page, BookRecommend bookRecommend) {
		return super.findPage(page, bookRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(BookRecommend bookRecommend) {
		super.save(bookRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(BookRecommend bookRecommend) {
		super.delete(bookRecommend);
	}

	public int selectCurrentRecommendCount(){
		return bookRecommendDao.selectCurrentRecommendCount();
	}

}