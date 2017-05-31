/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.web;

import java.io.File;

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

import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.enums.MediaType;
import com.github.sd4324530.fastweixin.api.response.UploadMediaResponse;
import com.wfu.common.config.Global;
import com.wfu.common.persistence.Page;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.weixin.entity.WxArticle;
import com.wfu.modules.weixin.service.WxArticleService;
import com.wfu.modules.weixin.util.WebAPI;

/**
 * 微信文章管理Controller
 * @author wwhui
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxArticle")
public class WxArticleController extends BaseController {

	@Autowired
	private WxArticleService wxArticleService;
	
	@ModelAttribute
	public WxArticle get(@RequestParam(required=false) String id) {
		WxArticle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxArticleService.get(id);
		}
		if (entity == null){
			entity = new WxArticle();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxArticle:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxArticle wxArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxArticle> page = wxArticleService.findPage(new Page<WxArticle>(request, response), wxArticle); 
		model.addAttribute("page", page);
		return "modules/weixin/wxArticleList";
	}

	@RequiresPermissions("weixin:wxArticle:view")
	@RequestMapping(value ="selectList")
	public String selectList(WxArticle wxArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxArticle> page = wxArticleService.findPage(new Page<WxArticle>(request, response), wxArticle);
		model.addAttribute("page", page);
		return "modules/weixin/wxArticleSelectList";
	}
	@RequiresPermissions("weixin:wxArticle:view")
	@RequestMapping(value = "form")
	public String form(WxArticle wxArticle, Model model) {
		model.addAttribute("wxArticle", wxArticle);
		return "modules/weixin/wxArticleForm";
	}

	@RequiresPermissions("weixin:wxArticle:edit")
	@RequestMapping(value = "save")
	public String save(WxArticle wxArticle, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxArticle)){
			return form(wxArticle, model);
		}
		MediaAPI mediaAPI=new MediaAPI(WebAPI.getConfig());
		UploadMediaResponse response= mediaAPI.uploadMedia(MediaType.IMAGE,
				new File(request.getRealPath("/")+wxArticle.getShowCoverPic()));
		wxArticle.setThumbMediaId(response.getMediaId());
		wxArticleService.save(wxArticle);
		addMessage(redirectAttributes, "保存文章成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxArticle/?repage";
	}
	
	@RequiresPermissions("weixin:wxArticle:edit")
	@RequestMapping(value = "delete")
	public String delete(WxArticle wxArticle, RedirectAttributes redirectAttributes) {
		wxArticleService.delete(wxArticle);
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxArticle/?repage";
	}

}