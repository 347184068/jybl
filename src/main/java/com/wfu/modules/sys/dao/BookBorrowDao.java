/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.sys.entity.BookBorrow;

/**
 * 借书信息管理DAO接口
 * @author 徐韵轩
 * @version 2017-06-03
 */
@MyBatisDao
public interface BookBorrowDao extends CrudDao<BookBorrow> {

}