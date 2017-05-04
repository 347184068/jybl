/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.adsync.dao;

import com.weichai.common.persistence.CrudDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.adsync.entity.AdUpdate;

/**
 * AD更新时间DAO接口
 * @author Panbb
 * @version 2017-02-23
 */
@MyBatisDao
public interface AdUpdateDao extends CrudDao<AdUpdate> {
	
}