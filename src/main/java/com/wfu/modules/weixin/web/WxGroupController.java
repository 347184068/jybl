/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.web;

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
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.weixin.entity.WxGroup;
import com.wfu.modules.weixin.service.WxGroupService;

/**
 * 微信用户分组管理Controller
 * @author wwhui
 * @version 2016-04-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxGroup")
public class WxGroupController extends BaseController {

	@Autowired
	private WxGroupService wxGroupService;
	
	@ModelAttribute
	public WxGroup get(@RequestParam(required=false) String id) {
		WxGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxGroupService.get(id);
		}
		if (entity == null){
			entity = new WxGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxGroup wxGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxGroup> page = wxGroupService.findPage(new Page<WxGroup>(request, response), wxGroup); 
		model.addAttribute("page", page);
		return "modules/weixin/wxGroupList";
	}

	@RequiresPermissions("weixin:wxGroup:view")
	@RequestMapping(value = "form")
	public String form(WxGroup wxGroup, Model model) {
		model.addAttribute("wxGroup", wxGroup);
		return "modules/weixin/wxGroupForm";
	}

	@RequiresPermissions("weixin:wxGroup:edit")
	@RequestMapping(value = "save")
	public String save(WxGroup wxGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxGroup)){
			return form(wxGroup, model);
		}
		wxGroupService.save(wxGroup);
		addMessage(redirectAttributes, "保存用户分组成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxGroup/?repage";
	}
	
	@RequiresPermissions("weixin:wxGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(WxGroup wxGroup, RedirectAttributes redirectAttributes) {
		wxGroupService.delete(wxGroup);
		addMessage(redirectAttributes, "删除用户分组成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxGroup/?repage";
	}

}