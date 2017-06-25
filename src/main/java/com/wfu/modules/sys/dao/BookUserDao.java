/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.BookUser;

/**
 * 图书用户信息DAO接口
 * @author 杨泽涛
 * @version 2017-06-03
 */
@MyBatisDao
public interface BookUserDao extends CrudDao<BookUser> {
	public BookUser getUserByWxId(String wechatId);
}