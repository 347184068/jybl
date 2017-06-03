/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.sys.entity.BookRecommend;

/**
 * 图书推荐DAO接口
 * @author 徐韵轩
 * @version 2017-06-03
 */
@MyBatisDao
public interface BookRecommendDao extends CrudDao<BookRecommend> {

    public int selectCurrentRecommendCount();

}