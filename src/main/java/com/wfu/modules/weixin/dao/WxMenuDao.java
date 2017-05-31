/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.weixin.entity.WxMenu;

/**
 * 微信菜单管理DAO接口
 * @author wwhui
 * @version 2016-03-27
 */
@MyBatisDao
public interface WxMenuDao extends CrudDao<WxMenu> {
	
}