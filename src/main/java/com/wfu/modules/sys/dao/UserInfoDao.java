/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.dao;

import com.wfu.common.persistence.CrudDao;
import com.wfu.common.persistence.annotation.MyBatisDao;
import com.wfu.modules.sys.entity.UserInfo;

/**
 * 用户信息管理DAO接口
 * @author 徐韵轩
 * @version 2017-06-19
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {
    public UserInfo getUserByOpenId(String openId);
}