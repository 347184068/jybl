/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.register.service;

import com.google.common.collect.Lists;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.register.dao.UserRegisterDao;
import com.weichai.modules.register.entity.UserRegister;
import com.weichai.modules.validatecode.entity.InOutProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class UserRegisterService {
    private static final Logger LOG = Logger.getLogger(UserRegisterService.class);
    @Autowired
    private UserRegisterDao userRegisterDao;

    public int addUserRegister(UserRegister userRegister){
       int n = 0;
        userRegister.setId(IdGen.uuid());
        n = userRegisterDao.insertUserRegister(userRegister);
        return n;
    }

    public UserRegister queryUserRegister(UserRegister userRegister){
        UserRegister user = new UserRegister();
        user = userRegisterDao.selectUserRegister(userRegister);
        return user;
    }

    public boolean validateRegister(UserRegister userRegister){
        UserRegister user = new UserRegister();
        user = userRegisterDao.selectUserRegister(userRegister);
        if(user!=null){
            return true;
        }
        return false;
    }





}


