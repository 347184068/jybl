/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.dao;

import com.weichai.common.persistence.CrudDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.weixin.entity.WxGroup;

/**
 * 微信用户分组管理DAO接口
 * @author wwhui
 * @version 2016-04-14
 */
@MyBatisDao
public interface WxGroupDao extends CrudDao<WxGroup> {
	
}