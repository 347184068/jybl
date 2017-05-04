package com.weichai.modules.validatecode.web;

import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.StringUtils;
import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.utils.ScanResult;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.material.service.MaterialService;
import com.weichai.modules.register.entity.ScanDetail;
import com.weichai.modules.register.service.ScanDetailService;
import com.weichai.modules.validatecode.service.InOutProductEnterpriseService;
import com.weichai.modules.validatecode.service.InOutProductService;
import com.weichai.modules.validatecode.service.ScanReturnResult;
import com.weichai.modules.validatecode.service.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 构建多图文消息列表Controller
 * @author wwhui
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "validateEnterprise/scanQrCode")
public class ScanQrCodeEnterpriseController extends BaseController {

	@Autowired
	private InOutProductEnterpriseService inOutProductEnterpriseService;

	@Autowired
	private ScanDetailService scanDetailService;
	@Autowired
	private MaterialService materialService;

	public final static String VALIDINFO = "查询有效";
	public final static String INVALIDINFO = "查询无效";
	public final static String NOINFO = "暂无信息";


	@RequestMapping(value = {"validateCode"})
	@ResponseBody
	public ScanReturnResult validateCode(HttpServletRequest request,String qrCode,String openId) throws IOException {
		Pattern pt = Pattern.compile("^[0-9a-zA-Z]{13}$");
		Pattern pt_security = Pattern.compile("^\\d{12}$");
		Pattern pt_productId = Pattern.compile("^\\d{23}$");

		ScanReturnResult result = new ScanReturnResult();
		result.setScanCode(qrCode);
		result.setFlag(false);
		result.setMsg(INVALIDINFO);

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
			validateResult =inOutProductEnterpriseService.queryCountQRCode(qrCode,codeFlag);
			boolean flag = validateResult.isFlag();
			if(flag){
				result.setMsg(VALIDINFO);
			}else {
				result.setFlag(false);
				type="I";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setMsg(INVALIDINFO);
				return result;
			}
			String  outCode = validateResult.getInOutProduct().getOutCode();
			materialParam.setOutCode(outCode);
			materialParam.setProductId(validateResult.getInOutProduct().getProductId());
		}
		else if(mt_security.matches()){
			codeFlag = "S";
			validateResult =inOutProductEnterpriseService.queryCountQRCode(qrCode,codeFlag);
			if(!validateResult.isFlag()){
				type="S";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(INVALIDINFO);
				return result;
			}else {
				result.setMsg(VALIDINFO);
			}
			materialParam.setProductId(validateResult.getInOutProduct().getProductId());
			materialParam.setSecurity(qrCode);
		}
		else if(mt_productId.matches()){
			codeFlag = "P";
			validateResult = inOutProductEnterpriseService.queryCountQRCode(qrCode, codeFlag);
			if(!validateResult.isFlag()){
				type="P";
				scanDetail.setType(type);
				scanDetailService.addScanDetail(scanDetail);
				result.setFlag(false);
				result.setMsg(INVALIDINFO);
				return result;
			}else {
				result.setMsg(VALIDINFO);
			}
			materialParam.setProductId(qrCode);
		}

		Material material = new Material();
		material = materialService.getMaterial(materialParam);

		if(material!=null){
			result.setFlag(true);
			//产品信息
			if(StringUtils.isBlank(material.getMaterialCode())){
				result.setMaterialCode(NOINFO);
			}else {
				result.setMaterialCode(material.getMaterialCode());
			}
			if(StringUtils.isBlank(material.getMaterialName())){
				result.setMaterialName(NOINFO);
			}else{
				result.setMaterialName(material.getMaterialName());
			}
			//订单信息
			if(StringUtils.isBlank(material.getPurchaseOrder())){
				result.setPurchaseOrder(NOINFO);
			}else {
				result.setPurchaseOrder(material.getPurchaseOrder());
			}
			if (StringUtils.isBlank(material.getShippingOrder())){
				result.setShippingOrder(NOINFO);
			}else {
				result.setShippingOrder(material.getShippingOrder());
			}
			if(null == material.getShippingTime()){
				result.setShippingTime(NOINFO);
			}else {
				result.setShippingTime(DateUtils.formatDateTime(material.getShippingTime()));
			}
			//供应商信息
			if(StringUtils.isBlank(material.getSupplierName())){
				result.setSupplierName(NOINFO);
			}else {
				result.setSupplierName(material.getSupplierName());
			}
			//扫描码
			result.setScanCode(qrCode);
		}

		type = codeFlag.toUpperCase();
		scanDetail.setType(type);
		scanDetailService.addScanDetail(scanDetail);
		return result;
	}
}