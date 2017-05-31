/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.adsync.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.adsync.entity.AdUpdate;

/**
 * AD更新时间DAO接口
 * @author Panbb
 * @version 2017-02-23
 */
@MyBatisDao
public interface AdUpdateDao extends CrudDao<AdUpdate> {
	
}