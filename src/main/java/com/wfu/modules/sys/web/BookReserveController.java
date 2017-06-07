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
import com.wfu.modules.sys.entity.BookReserve;
import com.wfu.modules.sys.service.BookReserveService;

/**
 * 图书预定管理Controller
 * @author 徐韵轩
 * @version 2017-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookReserve")
public class BookReserveController extends BaseController {

	@Autowired
	private BookReserveService bookReserveService;
	
	@ModelAttribute
	public BookReserve get(@RequestParam(required=false) String id) {
		BookReserve entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookReserveService.get(id);
		}
		if (entity == null){
			entity = new BookReserve();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bookReserve:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookReserve bookReserve, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookReserve> page = bookReserveService.findPage(new Page<BookReserve>(request, response), bookReserve); 
		model.addAttribute("page", page);
		return "modules/sys/bookReserveList";
	}

	@RequiresPermissions("sys:bookReserve:view")
	@RequestMapping(value = "form")
	public String form(BookReserve bookReserve, Model model) {
		model.addAttribute("bookReserve", bookReserve);
		return "modules/sys/bookReserveForm";
	}

	@RequiresPermissions("sys:bookReserve:edit")
	@RequestMapping(value = "save")
	public String save(BookReserve bookReserve, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookReserve)){
			return form(bookReserve, model);
		}
		bookReserveService.save(bookReserve);
		addMessage(redirectAttributes, "保存预定信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookReserve/?repage";
	}
	
	@RequiresPermissions("sys:bookReserve:edit")
	@RequestMapping(value = "delete")
	public String delete(BookReserve bookReserve, RedirectAttributes redirectAttributes) {
		bookReserveService.delete(bookReserve);
		addMessage(redirectAttributes, "删除预定信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookReserve/?repage";
	}

}