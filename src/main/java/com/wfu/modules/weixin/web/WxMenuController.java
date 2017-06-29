/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.weixin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.company.api.QYMenuAPI;
import com.github.sd4324530.fastweixin.company.api.QYOauthAPI;
import com.github.sd4324530.fastweixin.company.api.entity.QYMenu;
import com.github.sd4324530.fastweixin.company.api.entity.QYMenuButton;
import com.github.sd4324530.fastweixin.company.api.enums.QYMenuType;
import com.github.sd4324530.fastweixin.company.api.enums.QYResultType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.google.common.collect.Lists;
import com.wfu.common.config.Global;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.weixin.entity.WxMenu;
import com.wfu.modules.weixin.service.WxMenuService;
import com.wfu.modules.weixin.util.WebAPI;

/**
 * 微信菜单管理Controller
 * @author wwhui
 * @version 2016-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMenu")
public class WxMenuController extends BaseController {

	@Autowired
	private WxMenuService wxMenuService;
	
	@ModelAttribute
	public WxMenu get(@RequestParam(required=false) String id) {
		WxMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMenuService.get(id);
		}
		if (entity == null){
			entity = new WxMenu();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WxMenu wxMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<WxMenu> list = wxMenuService.findList(wxMenu); 
		model.addAttribute("list", list);
		if(wxMenu.getMenuType().equals("2")){
			return "modules/weixin/wxCropMenuList";
		}
		return "modules/weixin/wxMenuList";
	}

	@RequiresPermissions("weixin:wxMenu:view")
	@RequestMapping(value = "form")
	public String form(WxMenu wxMenu, Model model,String parentid) {
		if (wxMenu.getParent()!=null && StringUtils.isNotBlank(wxMenu.getParent().getId())){
			wxMenu.setParent(wxMenuService.get(wxMenu.getParent().getId()));
		}else{
			if(StringUtils.isNotBlank(parentid)){
				wxMenu.setParent(new WxMenu(parentid));
			}
		}
		List<WxMenu> list = wxMenuService.findAllList(wxMenu);
		model.addAttribute("list", list);
		model.addAttribute("wxMenu", wxMenu);
		if(wxMenu.getMenuType().equals("2")){
			return "modules/weixin/wxCropMenuForm";
		}
		return "modules/weixin/wxMenuForm";

	}

	@RequestMapping(value = "save")
	public String save(WxMenu wxMenu, Model model,String parentid, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMenu)){
			return form(wxMenu, model ,parentid);
		}
		wxMenuService.save(wxMenu);
		addMessage(redirectAttributes, "保存微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMenu/?menuType="+wxMenu.getMenuType();
	}
	
	@RequiresPermissions("weixin:wxMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMenu wxMenu, RedirectAttributes redirectAttributes) {
		wxMenuService.delete(wxMenu);
		addMessage(redirectAttributes, "删除微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/wxMenu/?menuType="+wxMenu.getMenuType();
	}

	@RequestMapping(value = "sync")
	@ResponseBody
	public String sync(WxMenu wxMenu, RedirectAttributes redirectAttributes) {
		List<WxMenu> wxMenuList= wxMenuService.findList(wxMenu);
		OauthAPI oauthAPI=new OauthAPI(WebAPI.getConfig());
		/// 构建微信菜单
		Menu menu=new Menu();
		List<MenuButton>  menuButtonList=Lists.newArrayList();
		for (WxMenu wx:wxMenuList){
			if("1".equals(wx.getLevel())){
				MenuButton parentMenu=new MenuButton();
				List<MenuButton> childList= Lists.newArrayList();
				//构建子菜单
				for (WxMenu child:wxMenuList){
					if(child.getParent()!=null&&wx.getId().
							equals(child.getParent().getId())){
						MenuButton menuButton=new MenuButton();
						menuButton.setName(child.getName());
						menuButton.setType(MenuType.VIEW);
						if(StringUtils.isNotBlank(child.getUrl())){
							String url=oauthAPI.getOauthPageUrl(child.getUrl(), OauthScope.SNSAPI_BASE,"1");
							menuButton.setUrl(url);
							//menuButton.setUrl(child.getUrl());
						}
						if(StringUtils.isNotBlank(child.getKeyValue())){
							menuButton.setKey(child.getKeyValue());
						}
						childList.add(menuButton);
					}
				}
				parentMenu.setSubButton(childList);
				parentMenu.setName(wx.getName());
				menuButtonList.add(parentMenu);
			}
			continue;
		}
		menu.setButton(menuButtonList);
		MenuAPI menuAPI=new MenuAPI(WebAPI.getConfig());
		ResultType  resultType= menuAPI.createMenu(menu);
		return  resultType.getDescription();
	}


	@RequestMapping(value = "syncCrop")
	@ResponseBody
	public String syncCrop(WxMenu wxMenu, RedirectAttributes redirectAttributes) {
		String agentId = Global.getConfig("CorpAgentId");
		System.out.println(agentId+"----------");
		List<WxMenu> wxMenuList= wxMenuService.findList(wxMenu);
		QYOauthAPI oauthAPI=new QYOauthAPI(WebAPI.getQYConfig());
		/// 构建微信菜单
		QYMenu menu=new QYMenu();
		List<QYMenuButton>  menuButtonList=Lists.newArrayList();
		for (WxMenu wx:wxMenuList){
			if("1".equals(wx.getLevel())){
				QYMenuButton parentMenu=new QYMenuButton();
				List<QYMenuButton> childList= Lists.newArrayList();
				//构建子菜单
				for (WxMenu child:wxMenuList){
					if(child.getParent()!=null&&wx.getId().
							equals(child.getParent().getId())){
						QYMenuButton menuButton=new QYMenuButton();
						menuButton.setName(child.getName());
						menuButton.setType(QYMenuType.VIEW);
						if(StringUtils.isNotBlank(child.getUrl())){
							String url=oauthAPI.getOauthPageUrl(child.getUrl(), OauthScope.SNSAPI_BASE,"1");
							menuButton.setUrl(url);
							//menuButton.setUrl(child.getUrl());
						}
						if(StringUtils.isNotBlank(child.getKeyValue())){
							menuButton.setKey(child.getKeyValue());
						}
						childList.add(menuButton);
					}
				}
				parentMenu.setSubButton(childList);
				parentMenu.setName(wx.getName());
				menuButtonList.add(parentMenu);
			}
			continue;
		}
		menu.setButton(menuButtonList);
		QYMenuAPI menuAPI=new QYMenuAPI(WebAPI.getQYConfig());
		QYResultType resultType= menuAPI.create(menu, agentId);
		System.out.println(resultType.getDescription());
		return  resultType.getDescription();
	}


}