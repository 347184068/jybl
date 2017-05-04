package com.weichai.modules.statistics.web;

import com.google.common.collect.Lists;
import com.weichai.common.persistence.Page;
import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.QRCodeUtil;
import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.filegen.service.InOutGrantService;
import com.weichai.modules.filegen.service.InOutUsedService;
import com.weichai.modules.filegen.utils.CreateFileUtil;
import com.weichai.modules.filegen.utils.JsonResult;
import com.weichai.modules.filegen.utils.ScanResult;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.material.service.MaterialService;
import com.weichai.modules.statistics.entity.CodeMaterialInfo;
import com.weichai.modules.statistics.entity.CodeQueryInfo;
import com.weichai.modules.statistics.entity.CodeStatisticInfo;
import com.weichai.modules.statistics.service.CodeMaterialService;
import com.weichai.modules.statistics.service.CodeQueryInfoService;
import com.weichai.modules.statistics.service.CodeStatisticService;
import com.weichai.modules.validatecode.entity.InOutProduct;
import com.weichai.modules.validatecode.service.InOutProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zhangli
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/background/manage")
public class BackGroundController extends BaseController {

	@Autowired
	private CodeStatisticService codeStatisticService;
	@Autowired
	private CodeMaterialService codeMaterialService;
	@Autowired
	private CodeQueryInfoService codeQueryInfoService;
	/**
	 * 防伪码申请及打印情况报表
	 * @param codeStatisticInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = {"codeGrantAndPrint"})
	public String codeGrantAndPrint(CodeStatisticInfo codeStatisticInfo,HttpServletRequest request, HttpServletResponse response, Model model) {

		CodeStatisticInfo info = new CodeStatisticInfo();
		info = codeStatisticService.countCodeUpDownNum(codeStatisticInfo);
		model.addAttribute("codeStatisticInfo",info);
		return "modules/background/countGrantCodeList";
	}

	@RequestMapping(value = {"codeMaterialList"})
	public String codeMaterialList(CodeMaterialInfo codeMaterialInfo,HttpServletRequest request, HttpServletResponse response, Model model){
		/*List<CodeMaterialInfo> supplierList = Lists.newArrayList();
		supplierList = codeMaterialService.getSupplierList();
		model.addAttribute("supplierList",supplierList);*/


		Page<CodeMaterialInfo> page = codeMaterialService.findPage(new Page<CodeMaterialInfo>(request,response),codeMaterialInfo);
		model.addAttribute("page",page);

		/*List<CodeMaterialInfo> codeMaterialInfoList = Lists.newArrayList();
		codeMaterialInfoList = codeMaterialService.getCodeMaterialInfoList(codeMaterialInfo);
		model.addAttribute("codeMaterialList",codeMaterialInfoList);*/
		return "modules/background/countCodeMaterialList";
	}

	@RequestMapping(value = {"codeQueryInfoList"})
	public String codeQueryInfoList(CodeQueryInfo codeQueryInfo,HttpServletRequest request, HttpServletResponse response, Model model){

		Page<CodeQueryInfo> page = codeQueryInfoService.findPage(new Page<CodeQueryInfo>(request,response),codeQueryInfo);
		model.addAttribute("page",page);

		return "modules/background/codeQueryInfoList";
	}

	@RequestMapping(value = {"codeQueryDetailList"})
	public String codeQueryDetailList(CodeQueryInfo codeQueryInfo,HttpServletRequest request, HttpServletResponse response, Model model){

		Page<CodeQueryInfo> page = codeQueryInfoService.findPageDetail(new Page<CodeQueryInfo>(request, response), codeQueryInfo);
		model.addAttribute("page",page);
		return "modules/background/codeQueryDetailList";
	}






























	/*@RequestMapping(value = {"list"})
	public String manageInOutCode(InOutProduct inOutProduct,HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Page<InOutProduct> page = inOutProductService.findPage(new Page<InOutProduct>(request,response),inOutProduct);
		model.addAttribute("page",page);
		return "modules/background/inOutProductList";
	}

	@RequestMapping(value = {"getMaterial"})
	public String getMaterial(Material material,HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		//todo  改成productId
		Material materialResult = new Material();
		materialResult = materialService.getMaterial(material);
		model.addAttribute("material",materialResult);
		model.addAttribute("materialCode",materialResult.getMaterialCode());

		model.addAttribute("materialName",materialResult.getMaterialName());
		return "modules/background/materialList";
	}*/

}