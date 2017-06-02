/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.dao;

import com.wfu.common.persistence.TreeDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.sys.entity.Category;

/**
 * 分类管理DAO接口
 * @author 徐韵轩
 * @version 2017-06-02
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {
	
}