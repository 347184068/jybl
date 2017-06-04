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
import com.wfu.modules.sys.entity.BookUser;
import com.wfu.modules.sys.service.BookUserService;

import java.util.UUID;

/**
 * 图书用户信息Controller
 *
 * @author 杨泽涛
 * @version 2017-06-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/bookUser")
public class BookUserController extends BaseController {

    @Autowired
    private BookUserService bookUserService;

    @ModelAttribute
    public BookUser get(@RequestParam(required = false) String id) {
        BookUser entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = bookUserService.get(id);
        }
        if (entity == null) {
            entity = new BookUser();
        }
        return entity;
    }

    @RequiresPermissions("sys:bookUser:view")
    @RequestMapping(value = {"list", ""})
    public String list(BookUser bookUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BookUser> page = bookUserService.findPage(new Page<BookUser>(request, response), bookUser);
        model.addAttribute("page", page);
        return "modules/sys/bookUserList";
    }

    @RequiresPermissions("sys:bookUser:view")
    @RequestMapping(value = "form")
    public String form(BookUser bookUser, Model model) {
        model.addAttribute("bookUser", bookUser);
        return "modules/sys/bookUserForm";
    }

    @RequiresPermissions("sys:bookUser:edit")
    @RequestMapping(value = "save")
    public String save(BookUser bookUser, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bookUser)) {
            return form(bookUser, model);
        }
        BookUser bu = bookUserService.getUserByWxId(bookUser.getWechatId());
        if (bu != null) {
            addMessage(redirectAttributes, "微信号已被注册！");

        } else {
            String uuId = UUID.randomUUID().toString();
            bookUser.setUserId(uuId);
            bookUserService.save(bookUser);
            addMessage(redirectAttributes, "注册成功");
        }

        return "redirect:" + Global.getAdminPath() + "/sys/bookUser/?repage";
    }

    @RequiresPermissions("sys:bookUser:edit")
    @RequestMapping(value = "delete")
    public String delete(BookUser bookUser, RedirectAttributes redirectAttributes) {
        bookUserService.delete(bookUser);
        addMessage(redirectAttributes, "删除图书用户信息成功");
        return "redirect:" + Global.getAdminPath() + "/sys/bookUser/?repage";
    }


}