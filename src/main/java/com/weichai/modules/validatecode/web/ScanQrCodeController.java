package com.weichai.modules.validatecode.web;

import com.google.common.collect.Lists;
import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.QRCodeUtil;
import com.weichai.common.utils.StringUtils;
import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.filegen.service.InOutGrantService;
import com.weichai.modules.filegen.utils.CreateFileUtil;
import com.weichai.modules.filegen.utils.JsonResult;
import com.weichai.modules.filegen.utils.ScanResult;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.material.service.MaterialService;
import com.weichai.modules.register.entity.ScanDetail;
import com.weichai.modules.register.service.ScanDetailService;
import com.weichai.modules.validatecode.entity.InOutProduct;
import com.weichai.modules.validatecode.service.InOutProductService;
import com.weichai.modules.validatecode.service.ScanReturnResult;
import com.weichai.modules.validatecode.service.ValidateResult;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 二维码扫描验证Controller
 * @author zhagnli
 * @version 2016-03-23
 */
@Controller
@RequestMapping(value = "validate/scanQrCode")
public class ScanQrCodeController extends BaseController {

	@Autowired
	private InOutProductService inOutProductService;

	@Autowired
	private ScanDetailService scanDetailService;

	@Autowired
	private MaterialService materialService;

	public final static String NOEXIST = "对不起，您查询的产品码不存在";
	public final static String COUNTONE = "您扫描的产品码正确有效，您查询的产品是潍柴动力的正牌产品，感谢您的使用，敬请放心。";
	public final static String INCODEOVER ="该产品已被使用，谨防假冒,如有疑问请与厂家联系。";
	public final static String OUTCODEOVER = "该产品在此之前已查询过，谨防假冒，如有疑问请与厂家联系。";

	@RequestMapping(value = {"validateScanCode"})
	@ResponseBody
	public ScanReturnResult validateScanCode(HttpServletRequest request,String qrCode,String openId) throws IOException {
/*^(W|N)[0-9a-zA-Z]{12}$*/
		Pattern pt = Pattern.compile("^(W|N)[0-9a-zA-Z]{12}$");
		Pattern pt_security = Pattern.compile("^\\d{12}$");
		Pattern pt_productId = Pattern.compile("^\\d{23}$");

		ScanReturnResult result = new ScanReturnResult();
		result.setScanCode(qrCode);
		result.setFlag(false);
		result.setMsg(NOEXIST);
		if(StringUtils.isBlank(openId)){
			result.setMsg("");
			return result;
		}
		//存储扫描记录
		ScanDetail scanDetail = new ScanDetail();
		scanDetail.setId(IdGen.uuid());
		scanDetail.setOpenId(openId);
		scanDetail.setScanNo(qrCode);
		String type="";

		//1.使用Matcher来对比目标字符串
		Matcher mt = pt.matcher(qrCode);
		Matcher mt_security = pt_security.matcher(qrCode);
		Matcher mt_productId = pt_productId.matcher(qrCode);
		if(!mt.matches() && !mt_security.matches() && !mt_productId.matches()){
			type = "I";
		}
		if(type.equals("I")){
			scanDetail.setType(type);

			if(qrCode.length()>32){
				return result;
			}
			scanDetailService.addScanDetail(scanDetail);
			return result;
		}
		String codeFlag="";
		ValidateResult validateResult = new ValidateResult();

		Material materialParam = new Material();

		//2.判断内外码 、18位普通码、23位产品码
		/**
		 * if 内外码
		 */
		if(mt.matches()){
			//2.1.判断内外码
			String w_n_code = qrCode.substring(0,1).toUpperCase();
			if(w_n_code.equals("W")){
				codeFlag = "W";
			}else if(w_n_code.equals("N")){
				codeFlag = "N" ;
			}else{
				return result;
			}
			// 2.2查询内码外码次数 分情况判断
			validateResult =inOutProductService.queryCountQRCode(qrCode,codeFlag);
			String count = validateResult.getCount();
			if(count.equals("1")){
				result.setMsg(COUNTONE);
			}else if(count.equals("3")){
				if(codeFlag.equals("N")){
					result.setMsg(INCODEOVER);
				}else if(codeFlag.equals("W")){
					result.setMsg(OUTCODEOVER);
				}
			}else {
				result.setFlag(false);
				type="I";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setMsg(NOEXIST);
				return result;
			}

			String  outCode = validateResult.getInOutProduct().getOutCode();
			String productId = validateResult.getInOutProduct().getProductId();
			materialParam.setOutCode(outCode);
			materialParam.setProductId(productId);
		}
		//2.if 18位普通码
		else if(mt_security.matches()){
			codeFlag = "S";
			validateResult =inOutProductService.queryCountQRCode(qrCode,codeFlag);
			String count = validateResult.getCount();
			if(count.equals("1")){
				result.setMsg(COUNTONE);
			}else if(count.equals("3")){
				result.setMsg(OUTCODEOVER);
			}else {
				result.setFlag(false);
				type="I";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setMsg(NOEXIST);
				return result;
			}
			/*if(!validateResult.isFlag()){
				type="S";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(NOEXIST);
				return result;
			}else {
				result.setMsg(COUNTONE);
			}*/
			materialParam.setProductId(validateResult.getInOutProduct().getProductId());
			materialParam.setSecurity(qrCode);
		}else if(mt_productId.matches()){
			codeFlag = "P";
			validateResult = inOutProductService.queryCountQRCode(qrCode, codeFlag);
			String count = validateResult.getCount();
			if(count.equals("1")){
				result.setMsg(COUNTONE);
			}else if(count.equals("3")){
				result.setMsg(OUTCODEOVER);
			}else {
				result.setFlag(false);
				type="I";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setMsg(NOEXIST);
				return result;
			}
			/*if(!validateResult.isFlag()){
				type="P";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(NOEXIST);
				return result;
			}else {
				result.setMsg(COUNTONE);
			}*/
			materialParam.setProductId(qrCode);
		}


		Material material = new Material();
		material = materialService.getMaterial(materialParam);
		if(material!=null){
			//产品信息
			if(StringUtils.isBlank(material.getMaterialCode())){
				result.setMaterialCode("暂无信息");
			}else{
				result.setMaterialCode(material.getMaterialCode());
			}
			if(StringUtils.isBlank(material.getMaterialName())){
				result.setMaterialName("暂无信息");
			}else {
				result.setMaterialName(material.getMaterialName());
			}
			result.setFlag(true);
			//扫描码
			result.setScanCode(qrCode);
		}
		//添加扫描记录
		type = codeFlag.toUpperCase();
		scanDetail.setType(type);
		scanDetailService.addScanDetail(scanDetail);
		return result;

	}


	@RequestMapping(value = {"validateQueryCode"})
	@ResponseBody
	public ScanReturnResult validateQueryCode(HttpServletRequest request,String qrCode,String openId) throws IOException {

		Pattern pt = Pattern.compile("^[0-9a-zA-Z]{13}$");
		Pattern pt_security = Pattern.compile("^\\d{18}$");
		Pattern pt_productId = Pattern.compile("^\\d{23}$");

		ScanReturnResult result = new ScanReturnResult();
		result.setScanCode(qrCode);
		result.setFlag(false);
		result.setMsg(NOEXIST);

		//存储扫描记录
		ScanDetail scanDetail = new ScanDetail();
		scanDetail.setId(IdGen.uuid());
		scanDetail.setOpenId(openId);
		scanDetail.setScanNo(qrCode);
		String type="";

		//1.使用Matcher来对比目标字符串
		Matcher mt = pt.matcher(qrCode);
		Matcher mt_security = pt_security.matcher(qrCode);
		Matcher mt_productId = pt_productId.matcher(qrCode);
		if(!mt.matches() && !mt_security.matches() && !mt_productId.matches()){
			type = "I";
		}
		if(type.equals("I")){
			scanDetail.setType(type);

			if(qrCode.length()>32){
				return result;
			}
			scanDetailService.addScanDetail(scanDetail);
			return result;
		}
		String codeFlag="";
		ValidateResult validateResult = new ValidateResult();

		Material materialParam = new Material();

		//2.判断内外码 、18位普通码、23位产品码
		/**
		 * if 内外码
		 */
		if(mt.matches()){
			//2.1.判断内外码
			String w_n_code = qrCode.substring(0,1).toUpperCase();
			if(w_n_code.equals("W")){
				codeFlag = "W";
			}else if(w_n_code.equals("N")){
				codeFlag = "N" ;
			}
			// 2.2查询内码外码次数 分情况判断
			validateResult =inOutProductService.queryQRCodeNoUpdate(qrCode, codeFlag);
			String count = validateResult.getCount();
			if(count.equals("1")){
				result.setMsg(COUNTONE);
			}else if(count.equals("3")){
				if(codeFlag.equals("N")){
					result.setMsg(INCODEOVER);
				}else if(codeFlag.equals("W")){
					result.setMsg(OUTCODEOVER);
				}
			}else {
				result.setFlag(false);
				type="I";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setMsg(NOEXIST);
				return result;
			}
			String  outCode = validateResult.getInOutProduct().getOutCode();
			materialParam.setOutCode(outCode);
		}
		//2.if 18位普通码
		else if(mt_security.matches()){
			codeFlag = "S";
			validateResult =inOutProductService.queryQRCodeNoUpdate(qrCode, codeFlag);
			String count = validateResult.getCount();
			if(!validateResult.isFlag()){
				type="S";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(NOEXIST);
				return result;
			}else {
				result.setMsg(COUNTONE);
			}
			materialParam.setSecurity(qrCode);
		}else if(mt_productId.matches()){
			codeFlag = "P";
			validateResult = inOutProductService.queryQRCodeNoUpdate(qrCode, codeFlag);
			if(!validateResult.isFlag()){
				type="P";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(NOEXIST);
				return result;
			}else {
				//result.setFlag(true);
				result.setMsg(COUNTONE);
			}
			materialParam.setProductId(qrCode);
		}

		Material material = new Material();
		material = materialService.getMaterial(materialParam);
		if(material!=null){
			result.setFlag(true);
			//产品信息
			if(StringUtils.isBlank(material.getMaterialCode())){
				result.setMaterialCode("暂无信息");
			}else{
				result.setMaterialCode(material.getMaterialCode());
			}
			if(StringUtils.isBlank(material.getMaterialName())){
				result.setMaterialName("暂无信息");
			}else {
				result.setMaterialName(material.getMaterialName());
			}

			//扫描码
			result.setScanCode(qrCode);
		}

		//添加扫描记录
		type = codeFlag.toUpperCase();
		scanDetail.setType(type);
		scanDetailService.addScanDetail(scanDetail);
		return result;

	}



}