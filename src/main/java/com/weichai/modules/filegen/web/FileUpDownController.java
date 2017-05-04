package com.weichai.modules.filegen.web;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.QRCodeUtil;
import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.filegen.service.InOutDetailUsedService;
import com.weichai.modules.filegen.service.InOutGrantService;
import com.weichai.modules.filegen.service.InOutUsedService;
import com.weichai.modules.filegen.utils.CreateFileUtil;
import com.weichai.modules.filegen.utils.JsonResult;
import com.weichai.modules.filegen.utils.ScanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 构建多图文消息列表Controller
 * @author wwhui
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "file/fileUpDown")
public class FileUpDownController extends BaseController {

	@RequestMapping(value = {"uploadFile/{supplierType}"})
	public String uploadFile(@PathVariable String supplierType,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		 model.addAttribute("msg","");
		if(!supplierType.equals("01") && !supplierType.equals("02")){
			return "";
		}
		model.addAttribute("supplierType",supplierType);
		return "modules/file/new";
	}



	}