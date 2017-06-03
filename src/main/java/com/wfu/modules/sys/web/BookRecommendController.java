/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.service.BookService;
import com.wfu.modules.sys.utils.Constants;
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
import com.wfu.modules.sys.entity.BookRecommend;
import com.wfu.modules.sys.service.BookRecommendService;

import java.util.List;

/**
 * 图书推荐Controller
 * @author 徐韵轩
 * @version 2017-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookRecommend")
public class BookRecommendController extends BaseController {

	@Autowired
	private BookRecommendService bookRecommendService;

	@Autowired
	private BookService bookService;
	
	@ModelAttribute
	public BookRecommend get(@RequestParam(required=false) String id) {
		BookRecommend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookRecommendService.get(id);
		}
		if (entity == null){
			entity = new BookRecommend();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bookRecommend:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookRecommend bookRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookRecommend> page = bookRecommendService.findPage(new Page<BookRecommend>(request, response), bookRecommend); 
		model.addAttribute("page", page);
		return "modules/sys/bookRecommendList";
	}

	@RequiresPermissions("sys:bookRecommend:view")
	@RequestMapping(value = "form")
	public String form(BookRecommend bookRecommend, Model model) {
		model.addAttribute("bookRecommend", bookRecommend);
		return "modules/sys/bookRecommendForm";
	}

	@RequiresPermissions("sys:bookRecommend:edit")
	@RequestMapping(value = "save")
	public String save(BookRecommend bookRecommend, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookRecommend)){
			return form(bookRecommend, model);
		}
		Book book = bookService.selectBookByIsbn(bookRecommend.getIsbn());
		if(book==null){
			addMessage(redirectAttributes, "书籍不存在，请重新添加");
		}else{
			int count = bookRecommendService.selectCurrentRecommendCount();
			List<BookRecommend> list= bookRecommendService.findList(bookRecommend);
			if(list==null||list.size()==0){
				if(count <= Constants.MAX_RECOMMEND_COUNT){
					bookRecommend.setBookName(book.getBookName());
					bookRecommend.setBookAuthor(book.getBookAuthor());
					bookRecommendService.save(bookRecommend);
					addMessage(redirectAttributes, "保存推荐图书成功");
				}else{
					addMessage(redirectAttributes, "超出最大图书推荐量");
				}
			}else{
				addMessage(redirectAttributes, "图书已推荐");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/sys/bookRecommend/?repage";
	}
	
	@RequiresPermissions("sys:bookRecommend:edit")
	@RequestMapping(value = "delete")
	public String delete(BookRecommend bookRecommend, RedirectAttributes redirectAttributes) {
		bookRecommendService.delete(bookRecommend);
		addMessage(redirectAttributes, "删除推荐图书成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookRecommend/?repage";
	}

}