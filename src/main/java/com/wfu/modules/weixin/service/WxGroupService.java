/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.CreateGroupResponse;
import com.wfu.common.persistence.Page;
import com.wfu.common.service.CrudService;
import com.wfu.common.utils.StringUtils;
import com.wfu.modules.weixin.dao.WxGroupDao;
import com.wfu.modules.weixin.entity.WxGroup;
import com.wfu.modules.weixin.util.WebAPI;

/**
 * 微信用户分组管理Service
 * @author wwhui
 * @version 2016-04-14
 */
@Service
@Transactional(readOnly = true)
public class WxGroupService extends CrudService<WxGroupDao, WxGroup> {

	public WxGroup get(String id) {
		return super.get(id);
	}
	
	public List<WxGroup> findList(WxGroup wxGroup) {
		return super.findList(wxGroup);
	}
	
	public Page<WxGroup> findPage(Page<WxGroup> page, WxGroup wxGroup) {
		return super.findPage(page, wxGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(WxGroup wxGroup) {
		UserAPI userAPI=new UserAPI(WebAPI.getConfig());
		if(StringUtils.isBlank(wxGroup.getId())){
			CreateGroupResponse response=userAPI.createGroup(wxGroup.getGroupName());
			wxGroup.setGroupId(response.getGroup().getId());
			wxGroup.setUserCount(response.getGroup().getCount());
		}else{
			ResultType resultType= userAPI.updateGroup(wxGroup.getGroupId(),wxGroup.getGroupName());
			if(!resultType.equals(ResultType.SUCCESS)){
				return ;
			}
		}
		super.save(wxGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxGroup wxGroup) {
		UserAPI userAPI=new UserAPI(WebAPI.getConfig());
		if(wxGroup.getGroupId()!=null){
			userAPI.deleteGroup(wxGroup.getGroupId());
		}
		super.delete(wxGroup);
	}
	
}