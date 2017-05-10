/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.dao;

import com.weichai.common.persistence.CrudDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.sys.entity.Book;

import java.util.List;

/**
 * 出版社管理DAO接口
 * @author 徐韵轩
 * @version 2017-05-08
 */
@MyBatisDao
public interface BookDao extends CrudDao<Book> {
	public Book selectBookByPublisherId(String publisherId);
	public List<Book> selectBookByCategoryId(String CategoryId);
}