/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.dao;

import java.util.List;

import com.weichai.common.persistence.CrudDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.weixin.entity.WxAutoKeyword;

/**
 * 关键字恢复管理DAO接口
 * @author wwhui
 * @version 2016-04-07
 */
@MyBatisDao
public interface WxAutoKeywordDao extends CrudDao<WxAutoKeyword> {

    public List<WxAutoKeyword> findByKey(String key);
}