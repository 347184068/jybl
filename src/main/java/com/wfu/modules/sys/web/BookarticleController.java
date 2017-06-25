/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.wfu.modules.sys.entity.Bookarticle;
import com.wfu.modules.sys.service.BookarticleService;

/**
 * 图书文章管理Controller
 * @author 徐韵轩
 * @version 2017-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookarticle")
public class BookarticleController extends BaseController {

	@Autowired
	private BookarticleService bookarticleService;
	
	@ModelAttribute
	public Bookarticle get(@RequestParam(required=false) String id) {
		Bookarticle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookarticleService.get(id);
		}
		if (entity == null){
			entity = new Bookarticle();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bookarticle:view")
	@RequestMapping(value = {"list", ""})
	public String list(Bookarticle bookarticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Bookarticle> page = bookarticleService.findPage(new Page<Bookarticle>(request, response), bookarticle); 
		model.addAttribute("page", page);
		return "modules/sys/bookarticleList";
	}

	@RequiresPermissions("sys:bookarticle:view")
	@RequestMapping(value = "form")
	public String form(Bookarticle bookarticle, Model model) {
		model.addAttribute("bookarticle", bookarticle);
		return "modules/sys/bookarticleForm";
	}

	@RequiresPermissions("sys:bookarticle:edit")
	@RequestMapping(value = "save")
	public String save(Bookarticle bookarticle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookarticle)){
			return form(bookarticle, model);
		}
		bookarticle.setContent(StringEscapeUtils.unescapeHtml4(bookarticle.getContent().trim()));
		bookarticleService.save(bookarticle);
		addMessage(redirectAttributes, "保存文章成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookarticle/?repage";
	}
	
	@RequiresPermissions("sys:bookarticle:edit")
	@RequestMapping(value = "delete")
	public String delete(Bookarticle bookarticle, RedirectAttributes redirectAttributes) {
		bookarticleService.delete(bookarticle);
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookarticle/?repage";
	}

}