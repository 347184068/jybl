/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.weixin.entity.WxArticleCollect;

/**
 * 构建多图文消息列表DAO接口
 * @author wwhui
 * @version 2016-03-28
 */
@MyBatisDao
public interface WxArticleCollectDao extends CrudDao<WxArticleCollect> {
	
}