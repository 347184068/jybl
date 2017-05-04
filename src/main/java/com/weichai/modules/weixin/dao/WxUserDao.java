/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.dao;

import com.weichai.common.persistence.CrudDao;
import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.weixin.entity.WxUser;

/**
 * 微信用户关注DAO接口
 * @author wwui
 * @version 2016-04-14
 */
@MyBatisDao
public interface WxUserDao extends CrudDao<WxUser> {

    WxUser findByOpenId(String openId);
}