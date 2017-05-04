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

import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.MessageAPI;
import com.github.sd4324530.fastweixin.api.entity.Article;
import com.github.sd4324530.fastweixin.api.response.GetSendMessageResponse;
import com.github.sd4324530.fastweixin.api.response.UploadMediaResponse;
import com.github.sd4324530.fastweixin.message.MpNewsMsg;
import com.google.common.collect.Lists;
import com.weichai.common.config.Global;
import com.weichai.common.persistence.Page;
import com.weichai.common.utils.StringUtils;
import com.weichai.common.web.BaseController;
import com.weichai.modules.weixin.entity.WxArticle;
import com.weichai.modules.weixin.entity.WxArticleCollect;
import com.weichai.modules.weixin.service.WxArticleCollectService;
import com.weichai.modules.weixin.service.WxArticleService;
import com.weichai.modules.weixin.util.WebAPI;

/**
 * 构建多图文消息列表Controller
 * @author wwhui
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxArticleCollect")
public class WxArticleCollectController extends BaseController {

	@Autowired
	private WxArticleCollectService wxArticleCollectService;
	@Autowired
	private WxArticleService wxArticleService;
	
	@ModelAttribute
	public WxArticleCollect get(@RequestParam(required=false) String id) {
		WxArticleCollect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxArticleCollectService.get(id);
		}
		if (entity == null){
			entity = new WxArticleCollect();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:wxArticleCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxArticleCollect wxArticleCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxArticleCollect> page = wxArticleCollectService.findPage(new Page<WxArticleCollect>(request, response), wxArticleCollect); 
		model.addAttribute("page", page);
		return "modules/weixin/wxArticleCollectList";
	}

	@RequiresPermissions("weixin:wxArticleCollect:view")
	@RequestMapping(value = "form")
	public String form(WxArticleCollect wxArticleCollect, Model model) {
		model.addAttribute("wxArticleCollect", wxArticleCollect);
		if(wxArticleCollect!=null){

		}
		return "modules/weixin/wxArticleCollectForm";
	}

	@RequiresPermissions("weixin:wxArticleCollect:edit")
	@RequestMapping(value = "save")
	public String save(WxArticleCollect wxArticleCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxArticleCollect)){
			return form(wxArticleCollect, model);
		}
		wxArticleCollectService.save(wxArticleCollect);
		addMessage(redirectAttributes, "保存消息列表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxArticleCollect/?repage";
	}
	
	@RequiresPermissions("weixin:wxArticleCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(WxArticleCollect wxArticleCollect, RedirectAttributes redirectAttributes) {
		wxArticleCollectService.delete(wxArticleCollect);
		addMessage(redirectAttributes, "删除消息列表成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxArticleCollect/?repage";
	}
	@RequiresPermissions("weixin:wxArticleCollect:edit")
	@RequestMapping(value = "sendWeixn")
	public String  sendWeixin(WxArticleCollect wxArticleCollect, RedirectAttributes redirectAttributes) {
		if(wxArticleCollect!=null&& StringUtils.isNotBlank(wxArticleCollect.getArticleList())){
			String[] ids=wxArticleCollect.getArticleList().split(",");
			List<Article> wxArticleList= Lists.newArrayList();
			for(int i=0;i<ids.length;i++){
				WxArticle article=wxArticleService.get(ids[i]);
				if(article!=null){
					Article  wxArtilce=new Article();
					wxArtilce.setTitle(article.getTitle());
					wxArtilce.setContent(article.getContent());
					wxArtilce.setAuthor(article.getAuthor());
					wxArtilce.setThumbMediaId(article.getThumbMediaId());
				//	wxArtilce.setContentSourceUrl(article.getContentSourceUrl());
					wxArticleList.add(wxArtilce);
				}
			}
			MediaAPI mediaAPI=new MediaAPI(WebAPI.getConfig());
			UploadMediaResponse response= mediaAPI.uploadNews(wxArticleList);
			wxArticleCollect.setMediaId(response.getMediaId());
			wxArticleCollectService.save(wxArticleCollect);
			MpNewsMsg msg=new MpNewsMsg(response.getMediaId());
			MessageAPI messageAPI=new MessageAPI(WebAPI.getConfig());
			GetSendMessageResponse messageResponse= messageAPI.sendMessageToUser(msg,true,null,null);
		}
		return "操作失败";
	}

}