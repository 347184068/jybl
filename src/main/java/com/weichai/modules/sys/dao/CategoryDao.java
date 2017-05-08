/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.dao;

import com.weichai.common.persistence.TreeDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.sys.entity.Category;

/**
 * 分类管理DAO接口
 * @author 徐韵轩
 * @version 2017-05-05
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {
	
}