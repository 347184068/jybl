/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.entity.TemplateParam;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.UserInfo;
import com.wfu.modules.sys.service.BookService;
import com.wfu.modules.sys.service.UserInfoService;
import com.wfu.modules.weixin.util.WebAPI;
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

import java.util.HashMap;
import java.util.Map;

import static oracle.net.aso.C05.b;

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

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private BookService bookService;

	private TemplateMsgAPI templateMsgAPI = new TemplateMsgAPI(WebAPI.getConfig());
	
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


	@RequestMapping(value = "sendMsg")
	public String sendMsg(String id){
		BookReserve bookReserve = bookReserveService.get(id);
		String userId = bookReserve.getUserId();
		String bookId = bookReserve.getBookId();
		UserInfo userInfo = userInfoService.get(userId);
		Book book = bookService.get(bookId);
		TemplateMsg templateMsg = new TemplateMsg();
		templateMsg.setTouser(userInfo.getOpenid());
		templateMsg.setTemplateId("lHKK6nqTMBYD69smWPkMcf_ZIN842NSXtckpI6Dd04k");
		Map<String, TemplateParam> map = new HashMap<String, TemplateParam>();
		map.put("bookName", new TemplateParam(book.getBookName(), "#00bfff"));
		map.put("bookISBN", new TemplateParam(book.getBookIsbn(), "#00bfff"));
		map.put("pickTime", new TemplateParam(bookReserve.getPickTime(), "#00bfff"));
		templateMsg.setData(map);
		templateMsg.setTopcolor("#FF0000");
		System.out.println(templateMsgAPI.send(templateMsg).getMsgid());
		return "redirect:"+Global.getAdminPath()+"/sys/bookReserve/?repage";
	}

}