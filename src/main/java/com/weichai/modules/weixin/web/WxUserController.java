/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.web;

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

import com.weichai.common.config.Global;
import com.weichai.common.persistence.Page;
import com.weichai.common.utils.StringUtils;
import com.weichai.common.web.BaseController;
import com.weichai.modules.weixin.entity.WxUser;
import com.weichai.modules.weixin.service.WxUserService;

/**
 * 微信用户关注Controller
 * @author wwui
 * @version 2016-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxUser")
public class WxUserController extends BaseController {

	@Autowired
	private WxUserService wxUserService;
	
	@ModelAttribute
	public WxUser get(@RequestParam(required=false) String id) {
		WxUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxUserService.get(id);
		}
		if (entity == null){
			entity = new WxUser();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxUser wxUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxUser> page = wxUserService.findPage(new Page<WxUser>(request, response), wxUser); 
		model.addAttribute("page", page);
		return "modules/weixin/wxUserList";
	}

	@RequiresPermissions("weixin:wxUser:view")
	@RequestMapping(value = "form")
	public String form(WxUser wxUser, Model model) {
		model.addAttribute("wxUser", wxUser);
		return "modules/weixin/wxUserForm";
	}

	@RequiresPermissions("weixin:wxUser:edit")
	@RequestMapping(value = "save")
	public String save(WxUser wxUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxUser)){
			return form(wxUser, model);
		}
		wxUserService.save(wxUser);
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxUser/?repage";
	}
	
	@RequiresPermissions("weixin:wxUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WxUser wxUser, RedirectAttributes redirectAttributes) {
		wxUserService.delete(wxUser);
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxUser/?repage";
	}

}