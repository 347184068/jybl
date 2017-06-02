/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfu.modules.sys.entity.BookPublisher;
import com.wfu.modules.sys.service.BookPublisherService;
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
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.service.BookService;

import java.util.UUID;

/**
 * 书籍管理Controller
 * @author 徐韵轩
 * @version 2017-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/book")
public class BookController extends BaseController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookPublisherService bookPublisherService;
	
	@ModelAttribute
	public Book get(@RequestParam(required=false) String id) {
		Book entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookService.get(id);
		}
		if (entity == null){
			entity = new Book();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:book:view")
	@RequestMapping(value = {"list", ""})
	public String list(Book book, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Book> page = bookService.findPage(new Page<Book>(request, response), book); 
		model.addAttribute("page", page);
		model.addAttribute("publisherList",bookPublisherService.finAllPublisher());
		return "modules/sys/bookList";
	}

	@RequiresPermissions("sys:book:view")
	@RequestMapping(value = "form")
	public String form(Book book, Model model) {
		model.addAttribute("book", book);
		model.addAttribute("publisherList",bookPublisherService.finAllPublisher());
		return "modules/sys/bookForm";
	}

	@RequiresPermissions("sys:book:edit")
	@RequestMapping(value = "save")
	public String save(Book book, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, book)){
			return form(book, model);
		}
		initBookParam(book);
		bookService.save(book);
		addMessage(redirectAttributes, "保存书籍成功");
		return "redirect:"+Global.getAdminPath()+"/sys/book/?repage";
	}

	private void initBookParam(Book book) {
		String id = book.getBookId();
		if(id==null||id==""){
			book.setBookId(UUID.randomUUID().toString());
		}
		book.setBookDirectory(StringEscapeUtils.unescapeHtml4(book.getBookDirectory().trim()));
		book.setBookContents(StringEscapeUtils.unescapeHtml4(book.getBookContents().trim()));
		book.setBookGuide(StringEscapeUtils.unescapeHtml4(book.getBookGuide().trim()));
		book.setBookIntroduction(StringEscapeUtils.unescapeHtml4(book.getBookIntroduction().trim()));
	}

	@RequiresPermissions("sys:book:edit")
	@RequestMapping(value = "delete")
	public String delete(Book book, RedirectAttributes redirectAttributes) {
		bookService.delete(book);
		addMessage(redirectAttributes, "删除书籍成功");
		return "redirect:"+Global.getAdminPath()+"/sys/book/?repage";
	}

}