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
import com.wfu.modules.sys.entity.BookPublisher;
import com.wfu.modules.sys.service.BookPublisherService;

import java.util.UUID;

/**
 * 出版社管理Controller
 * @author 徐韵轩
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookPublisher")
public class BookPublisherController extends BaseController {

	@Autowired
	private BookPublisherService bookPublisherService;
	
	@ModelAttribute
	public BookPublisher get(@RequestParam(required=false) String id) {
		BookPublisher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookPublisherService.get(id);
		}
		if (entity == null){
			entity = new BookPublisher();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bookPublisher:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookPublisher bookPublisher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookPublisher> page = bookPublisherService.findPage(new Page<BookPublisher>(request, response), bookPublisher); 
		model.addAttribute("page", page);
		return "modules/sys/bookPublisherList";
	}

	@RequiresPermissions("sys:bookPublisher:view")
	@RequestMapping(value = "form")
	public String form(BookPublisher bookPublisher, Model model) {
		model.addAttribute("bookPublisher", bookPublisher);
		return "modules/sys/bookPublisherForm";
	}

	@RequiresPermissions("sys:bookPublisher:edit")
	@RequestMapping(value = "save")
	public String save(BookPublisher bookPublisher, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookPublisher)){
			return form(bookPublisher, model);
		}
		initBookPublisherParam(bookPublisher);
		bookPublisherService.save(bookPublisher);
		addMessage(redirectAttributes, "保存出版社成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookPublisher/?repage";
	}

	private void initBookPublisherParam(BookPublisher bookPublisher) {
		String id = bookPublisher.getPublisherId();
		if(id==null||id==""){
			bookPublisher.setPublisherId(UUID.randomUUID().toString());
		}
	}

	@RequiresPermissions("sys:bookPublisher:edit")
	@RequestMapping(value = "delete")
	public String delete(BookPublisher bookPublisher, RedirectAttributes redirectAttributes) {
		bookPublisherService.delete(bookPublisher);
		addMessage(redirectAttributes, "删除出版社成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookPublisher/?repage";
	}

}