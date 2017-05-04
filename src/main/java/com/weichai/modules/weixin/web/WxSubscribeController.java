/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.web;

import java.util.List;

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
import com.weichai.modules.weixin.entity.WxArticleCollect;
import com.weichai.modules.weixin.entity.WxSubscribe;
import com.weichai.modules.weixin.service.WxArticleCollectService;
import com.weichai.modules.weixin.service.WxSubscribeService;

/**
 * 关注回复Controller
 * @author wwhui
 * @version 2016-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxSubscribe")
public class WxSubscribeController extends BaseController {

	@Autowired
	private WxSubscribeService wxSubscribeService;
	@Autowired
	private WxArticleCollectService wxArticleCollectService;
	
	@ModelAttribute
	public WxSubscribe get(@RequestParam(required=false) String id) {
		WxSubscribe entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxSubscribeService.get(id);
		}
		if (entity == null){
			entity = new WxSubscribe();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxSubscribe:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxSubscribe wxSubscribe, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxSubscribe> page = wxSubscribeService.findPage(new Page<WxSubscribe>(request, response), wxSubscribe); 
		model.addAttribute("page", page);
		return "modules/weixin/wxSubscribeList";
	}

	@RequiresPermissions("weixin:wxSubscribe:view")
	@RequestMapping(value = "form")
	public String form(WxSubscribe wxSubscribe, Model model) {
		model.addAttribute("wxSubscribe", wxSubscribe);
		List<WxArticleCollect> collectList=wxArticleCollectService.findList(null);
		model.addAttribute("collectList",collectList);
		return "modules/weixin/wxSubscribeForm";
	}

	@RequiresPermissions("weixin:wxSubscribe:edit")
	@RequestMapping(value = "save")
	public String save(WxSubscribe wxSubscribe, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxSubscribe)){
			return form(wxSubscribe, model);
		}
		wxSubscribeService.save(wxSubscribe);
		addMessage(redirectAttributes, "保存消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxSubscribe/?repage";
	}
	
	@RequiresPermissions("weixin:wxSubscribe:edit")
	@RequestMapping(value = "delete")
	public String delete(WxSubscribe wxSubscribe, RedirectAttributes redirectAttributes) {
		wxSubscribeService.delete(wxSubscribe);
		addMessage(redirectAttributes, "删除消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxSubscribe/?repage";
	}

}