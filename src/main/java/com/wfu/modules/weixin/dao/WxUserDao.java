/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.weixin.entity.WxUser;

/**
 * 微信用户关注DAO接口
 * @author wwui
 * @version 2016-04-14
 */
@MyBatisDao
public interface WxUserDao extends CrudDao<WxUser> {

    WxUser findByOpenId(String openId);
}