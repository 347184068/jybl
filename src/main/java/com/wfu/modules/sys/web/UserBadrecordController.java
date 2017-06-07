/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wfu.common.config.Global;
import com.wfu.common.persistence.Page;
import com.wfu.common.web.BaseController;
import com.wfu.common.utils.StringUtils;
import com.wfu.modules.sys.entity.UserBadrecord;
import com.wfu.modules.sys.service.UserBadrecordService;

/**
 * 用户不良记录Controller
 * @author 徐韵轩
 * @version 2017-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userBadrecord")
public class UserBadrecordController extends BaseController {

	@Autowired
	private UserBadrecordService userBadrecordService;
	
	@ModelAttribute
	public UserBadrecord get(@RequestParam(required=false) String id) {
		UserBadrecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userBadrecordService.get(id);
		}
		if (entity == null){
			entity = new UserBadrecord();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:userBadrecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserBadrecord userBadrecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserBadrecord> page = userBadrecordService.findPage(new Page<UserBadrecord>(request, response), userBadrecord); 
		model.addAttribute("page", page);
		return "modules/sys/userBadrecordList";
	}

	@RequiresPermissions("sys:userBadrecord:view")
	@RequestMapping(value = "form")
	public String form(UserBadrecord userBadrecord, Model model) {
		model.addAttribute("userBadrecord", userBadrecord);
		return "modules/sys/userBadrecordForm";
	}

	@RequiresPermissions("sys:userBadrecord:edit")
	@RequestMapping(value = "save")
	public String save(UserBadrecord userBadrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userBadrecord)){
			return form(userBadrecord, model);
		}
		userBadrecordService.save(userBadrecord);
		addMessage(redirectAttributes, "保存不良记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userBadrecord/?repage";
	}
	
	@RequiresPermissions("sys:userBadrecord:edit")
	@RequestMapping(value = "delete")
	public String delete(UserBadrecord userBadrecord, RedirectAttributes redirectAttributes) {
		userBadrecordService.delete(userBadrecord);
		addMessage(redirectAttributes, "删除不良记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userBadrecord/?repage";
	}

}