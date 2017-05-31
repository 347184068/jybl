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
import com.wfu.modules.weixin.entity.WxAutoKeyword;
import com.wfu.modules.weixin.service.WxAutoKeywordService;

/**
 * 关键字回复管理Controller
 * @author wwhui
 * @version 2016-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAutoKeyword")
public class WxAutoKeywordController extends BaseController {

	@Autowired
	private WxAutoKeywordService wxAutoKeywordService;
	
	@ModelAttribute
	public WxAutoKeyword get(@RequestParam(required=false) String id) {
		WxAutoKeyword entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxAutoKeywordService.get(id);
		}
		if (entity == null){
			entity = new WxAutoKeyword();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxAutoKeyword:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxAutoKeyword wxAutoKeyword, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxAutoKeyword> page = wxAutoKeywordService.findPage(new Page<WxAutoKeyword>(request, response), wxAutoKeyword); 
		model.addAttribute("page", page);
		return "modules/weixin/wxAutoKeywordList";
	}

	@RequiresPermissions("weixin:wxAutoKeyword:view")
	@RequestMapping(value = "form")
	public String form(WxAutoKeyword wxAutoKeyword, Model model) {
		model.addAttribute("wxAutoKeyword", wxAutoKeyword);
		return "modules/weixin/wxAutoKeywordForm";
	}

	@RequiresPermissions("weixin:wxAutoKeyword:edit")
	@RequestMapping(value = "save")
	public String save(WxAutoKeyword wxAutoKeyword, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAutoKeyword)){
			return form(wxAutoKeyword, model);
		}
		wxAutoKeywordService.save(wxAutoKeyword);
		addMessage(redirectAttributes, "保存关键字成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxAutoKeyword/?repage";
	}
	
	@RequiresPermissions("weixin:wxAutoKeyword:edit")
	@RequestMapping(value = "delete")
	public String delete(WxAutoKeyword wxAutoKeyword, RedirectAttributes redirectAttributes) {
		wxAutoKeywordService.delete(wxAutoKeyword);
		addMessage(redirectAttributes, "删除关键字成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxAutoKeyword/?repage";
	}

}