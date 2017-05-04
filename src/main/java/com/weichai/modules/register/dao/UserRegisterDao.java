/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.register.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.register.entity.UserRegister;
import com.weichai.modules.validatecode.entity.InOutProduct;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface UserRegisterDao {

    public int insertUserRegister(UserRegister userRegister);

    public UserRegister selectUserRegister(UserRegister userRegister);

    //将用户del_Flag 置为1
    public int delUser(UserRegister userRegister);
}