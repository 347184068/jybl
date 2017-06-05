/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfu.modules.sys.utils.Constants;
import com.wfu.modules.sys.utils.DateUtils;
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
import com.wfu.modules.sys.entity.BookBorrow;
import com.wfu.modules.sys.service.BookBorrowService;

import java.text.ParseException;
import java.util.UUID;

import static oracle.net.aso.C01.r;

/**
 * 借书信息管理Controller
 * @author 徐韵轩
 * @version 2017-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookBorrow")
public class BookBorrowController extends BaseController {

	@Autowired
	private BookBorrowService bookBorrowService;
	
	@ModelAttribute
	public BookBorrow get(@RequestParam(required=false) String id) {
		BookBorrow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bookBorrowService.get(id);
		}
		if (entity == null){
			entity = new BookBorrow();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:bookBorrow:view")
	@RequestMapping(value = {"list", ""})
	public String list(BookBorrow bookBorrow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BookBorrow> page = bookBorrowService.findPage(new Page<BookBorrow>(request, response), bookBorrow); 
		model.addAttribute("page", page);
		return "modules/sys/bookBorrowList";
	}

	@RequiresPermissions("sys:bookBorrow:view")
	@RequestMapping(value = "form")
	public String form(BookBorrow bookBorrow, Model model) {
		model.addAttribute("bookBorrow", bookBorrow);
		return "modules/sys/bookBorrowForm";
	}

	@RequiresPermissions("sys:bookBorrow:edit")
	@RequestMapping(value = "save")
	public String save(BookBorrow bookBorrow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bookBorrow)){
			return form(bookBorrow, model);
		}
		bookBorrow.setBorrowId(UUID.randomUUID().toString());
		bookBorrowService.save(bookBorrow);
		addMessage(redirectAttributes, "保存借阅信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookBorrow/?repage";
	}
	
	@RequiresPermissions("sys:bookBorrow:edit")
	@RequestMapping(value = "delete")
	public String delete(BookBorrow bookBorrow, RedirectAttributes redirectAttributes) {
		bookBorrowService.delete(bookBorrow);
		addMessage(redirectAttributes, "删除借阅信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookBorrow/?repage";
	}


	/**
	 * 图书续借
	 */
	@RequestMapping(value = "renew")
	public String bookRenew(String borrowId,RedirectAttributes redirectAttributes) throws ParseException {
		BookBorrow bookBorrow = bookBorrowService.get(borrowId);
		if(!bookBorrow.getIsOvertime().equals(Constants.BOOK_OVERTIME)){
			//TODO 需要加入不良借阅记录条数判断
			bookBorrow.setIsRenew(Constants.BOOK_RENEW);
			String newReturnTime = DateUtils.addMonth(bookBorrow.getReturnTime());
			bookBorrow.setReturnTime(newReturnTime);
			bookBorrowService.update(bookBorrow);
			addMessage(redirectAttributes, "续借成功");
		}else{
			addMessage(redirectAttributes, "图书已经超期,无法续借");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/bookBorrow/?repage";
	}

	/**
	 * 管理员确认还书是否有误
	 */
	@RequestMapping(value = "confirm")
	public String bookConfirm(String borrowId,RedirectAttributes redirectAttributes){
		BookBorrow bookBorrow = bookBorrowService.get(borrowId);
		bookBorrow.setIsConfirm(Constants.BOOK_CONFIRM);
		bookBorrowService.update(bookBorrow);
		addMessage(redirectAttributes, "确认成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookBorrow/?repage";
	}

	/**
	 *
	 * 超期列表显示
	 */
	@RequestMapping(value = "overTimeList")
	public String overTimeList(BookBorrow bookBorrow, HttpServletRequest request, HttpServletResponse response, Model model){
		bookBorrow.setIsOvertime(Constants.BOOK_OVERTIME);
		Page<BookBorrow> page = bookBorrowService.findPage(new Page<BookBorrow>(request, response), bookBorrow);
		model.addAttribute("page", page);
		return "modules/sys/bookBorrowOvertime";
	}

	/**
	 *  超期处理
	 */
	@RequestMapping(value = "overTimeProess")
	public String overTimeProess(String borrowId,RedirectAttributes redirectAttributes){
		BookBorrow bookBorrow = bookBorrowService.get(borrowId);
		bookBorrow.setIsConfirm(Constants.BOOK_CONFIRM);
		bookBorrow.setIsReturn(Constants.BOOK_RETURN);
		bookBorrowService.update(bookBorrow);
		addMessage(redirectAttributes, "处理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/bookBorrow/overTimeList/?repage";
	}
}