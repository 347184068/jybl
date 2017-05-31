/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.weixin.entity.WxSubscribe;

/**
 * 关注回复DAO接口
 * @author wwhui
 * @version 2016-04-07
 */
@MyBatisDao
public interface WxSubscribeDao extends CrudDao<WxSubscribe> {
	
}