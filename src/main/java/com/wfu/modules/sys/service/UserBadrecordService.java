/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.service;

import java.util.List;

import com.wfu.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.modules.sys.entity.UserBadrecord;
import com.wfu.modules.sys.dao.UserBadrecordDao;

/**
 * 用户不良记录Service
 * @author 徐韵轩
 * @version 2017-06-06
 */
@Service
@Transactional(readOnly = true)
public class UserBadrecordService extends CrudService<UserBadrecordDao, UserBadrecord> {

	public UserBadrecord get(String id) {
		return super.get(id);
	}
	
	public List<UserBadrecord> findList(UserBadrecord userBadrecord) {
		return super.findList(userBadrecord);
	}
	
	public Page<UserBadrecord> findPage(Page<UserBadrecord> page, UserBadrecord userBadrecord) {
		return super.findPage(page, userBadrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(UserBadrecord userBadrecord) {
		super.save(userBadrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserBadrecord userBadrecord) {
		super.delete(userBadrecord);
	}

	public UserBadrecord getByBorrowId(String borrowId){
		return dao.getByBorrowId(borrowId);
	}

	public int selectUserBadRecordCount(UserBadrecord userBadrecord){
		List<UserBadrecord> list = findList(userBadrecord);
		return list==null?0:list.size();
	}
	
}