/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weichai.common.persistence.Page;
import com.weichai.modules.sys.entity.Book;
import com.weichai.modules.sys.entity.Office;
import com.weichai.modules.sys.service.BookService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weichai.common.config.Global;
import com.weichai.common.web.BaseController;
import com.weichai.common.utils.StringUtils;
import com.weichai.modules.sys.entity.Category;
import com.weichai.modules.sys.service.CategoryService;

import static oracle.net.aso.C01.r;

/**
 * 分类管理Controller
 * @author 徐韵轩
 * @version 2017-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BookService bookService;

	@ModelAttribute
	public Category get(@RequestParam(required=false) String id) {
		Category entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = categoryService.get(id);
		}
		if (entity == null){
			entity = new Category();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Category category, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Category> list = categoryService.findList(category); 
		model.addAttribute("list", list);
		return "modules/sys/categoryList";
	}

	@RequiresPermissions("sys:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (category.getParent()!=null && StringUtils.isNotBlank(category.getParent().getId())){
			category.setParent(categoryService.get(category.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(category.getId())){
				Category categoryChild = new Category();
				categoryChild.setParent(new Category(category.getParent().getId()));
				List<Category> list = categoryService.findList(category); 
				if (list.size() > 0){
					category.setSort(list.get(list.size()-1).getSort());
					if (category.getSort() != null){
						category.setSort(category.getSort() + 30);
					}
				}
			}
		}
		if (category.getSort() == null){
			category.setSort(30);
		}
		model.addAttribute("category", category);
		return "modules/sys/categoryForm";
	}

	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		categoryService.save(category);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/category/?repage";
	}
	
	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		categoryService.delete(category);
		addMessage(redirectAttributes, "删除分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/category/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findList(new Category());
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "manage")
	public String manage(Category category, Model model,HttpServletRequest request,HttpServletResponse response) {
        model.addAttribute("list", categoryService.findList(category));
//		Page<Book> page = bookService.findPage(new Page<Book>(request, response), book);
//		model.addAttribute("page", page);
		return "modules/sys/categoryManage";
	}

	@RequiresPermissions("sys:category:edit")
	@RequestMapping(value = "booktable")
	public String booktable(Book book,Model model, String id) {
        List<Book> books = categoryService.findBookByCategoryId(id);
        model.addAttribute("list",books);
        return "modules/sys/bookTable";
	}
}