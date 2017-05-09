/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichai.common.persistence.Page;
import com.weichai.common.service.CrudService;
import com.weichai.common.utils.StringUtils;
import com.weichai.modules.sys.entity.BookPublisher;
import com.weichai.modules.sys.dao.BookPublisherDao;
import com.weichai.modules.sys.entity.Book;
import com.weichai.modules.sys.dao.BookDao;

import static oracle.net.aso.C01.i;

/**
 * 出版社管理Service
 * @author 徐韵轩
 * @version 2017-05-08
 */
@Service
@Transactional(readOnly = true)
public class BookPublisherService extends CrudService<BookPublisherDao, BookPublisher> {

	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookPublisherDao bookPublisherDao;
	
	public BookPublisher get(String id) {
		BookPublisher bookPublisher = super.get(id);
		bookPublisher.setBookList(bookDao.findList(new Book(bookPublisher.getPublisherId())));
		return bookPublisher;
	}
	
	public List<BookPublisher> findList(BookPublisher bookPublisher) {
		return super.findList(bookPublisher);
	}

	public List<BookPublisher> findAllPublisher(){
		Map<String,String> publisher = new HashMap<String, String>();
		List<BookPublisher> list = super.findAllList(new BookPublisher());
		return list;
	}


	public Page<BookPublisher> findPage(Page<BookPublisher> page, BookPublisher bookPublisher) {
		return super.findPage(page, bookPublisher);
	}
	
	@Transactional(readOnly = false)
	public void save(BookPublisher bookPublisher) {
		super.save(bookPublisher);
		for (Book book : bookPublisher.getBookList()){
			if (book.getId() == null){
				continue;
			}
			if (Book.DEL_FLAG_NORMAL.equals(book.getDelFlag())){
				if (StringUtils.isBlank(book.getId())){
					book.setBookPublisherid(bookPublisher);
					book.preInsert();
					bookDao.insert(book);
				}else{
					book.preUpdate();
					bookDao.update(book);
				}
			}else{
				bookDao.delete(book);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BookPublisher bookPublisher) {
		super.delete(bookPublisher);
		//将所有此分类下的书籍逻辑删除
		Book book = bookDao.selectBookByPublisherId(bookPublisher.getId());
		bookDao.delete(book);
	}

	public BookPublisher findPublisherById(String publisherId) {
		return bookPublisherDao.selectBookPublisherById(publisherId);
	}

	public void updateInfo(BookPublisher bookPublisher) {
		bookPublisherDao.update(bookPublisher);
	}
}