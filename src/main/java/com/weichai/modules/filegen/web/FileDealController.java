package com.weichai.modules.filegen.web;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weichai.common.config.Global;
import com.weichai.common.utils.*;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailUsedService;
import com.weichai.modules.filegen.service.InOutUsedService;


import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.filegen.service.InOutGrantService;
import com.weichai.modules.filegen.utils.CreateFileUtil;
import com.weichai.modules.filegen.utils.JsonResult;

/**
 * 构建多图文消息列表Controller
 * @author wwhui
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "file/fileDeal")
public class FileDealController extends BaseController {
	public final static String KEY = "Wc@TpSof";
	private static Logger logger = LoggerFactory.getLogger(FileDealController.class);

	//private final static String DISK="E:\\pointto\\";
	private final static String DISK = Global.getConfig("disk");
	private final static String EmptyProjectName="/pointto";

	@Autowired
	private InOutGrantService inOutGrantService;
	@Autowired
	private InOutDetailService inOutDetailService;
	@Autowired
	private InOutUsedService inOutUsedService;
	@Autowired
	private InOutDetailUsedService inOutDetailUsedService;

	public JsonResult createFile(HttpServletRequest request) throws IOException {

		String path = request.getSession().getServletContext().getRealPath("/");
		JsonResult result = new JsonResult();
		result = CreateFileUtil.createFile(DISK);
		return result;
	}

	@RequestMapping(value = {"uploadFile"})
	@ResponseBody
	public UploadResult uploadFile(String loginName,String pwd,String type,
							 HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UploadResult uploadResult = new UploadResult();
		uploadResult.setMessage("上传失败");
		uploadResult.setCode(2);
		if(!validateLogin(loginName, pwd, type)){

			uploadResult.setMessage("您的用户名、密码、类型错误。");
			return uploadResult;
		}

		MultipartHttpServletRequest multipartRequest  = null;
		try{
			multipartRequest = 	(MultipartHttpServletRequest) request;
		}catch (Exception e){
			uploadResult.setMessage("上传失败，请上传文件。");
			return uploadResult;
		}

		//  （根据前台的name名称得到上传的文件）
		MultipartFile txtFile  =  multipartRequest.getFile("file");

		String url = request.getScheme()+"://"
				+ request.getServerName()
				+":"+ request.getServerPort()
				+ request.getContextPath();
		System.out.println(url);
		//文件绝对路径F:\IdeaProjects\weichai\trunk\target\rcs\

		//String realPath = request.getSession().getServletContext().getRealPath("/");
		String realPath = DISK;
		JsonResult jsonResult = new JsonResult();

		jsonResult = CreateFileUtil.uploadFile(txtFile, realPath,"upload");
		String returnPath = jsonResult.getPath();


		String fileName=txtFile.getOriginalFilename();//获取原文件名
		InOutUsed inOutUsed = new InOutUsed();
		inOutUsed.setSupplierType(type);
		inOutUsed.setFileName(fileName);
		//todo
		/*inOutUsed.setFilePath(url+returnPath);*/
		inOutUsed.setFilePath(returnPath);
		inOutUsedService.addInOutUsed(inOutUsed);


		//去存当天使用的数据
		File file = jsonResult.getFile();
		String str_in_outCode = CreateFileUtil.readFile(file);
		String decrypt_in_outCode ="";
		try{
			decrypt_in_outCode = DesUtil.decrypt3(str_in_outCode,KEY);

		}catch (Exception e) {
			uploadResult.setMessage("上传失败，请核对密文格式。");
			return uploadResult;
		}

		List<InOutDetailUsed> inOutDetailUsedList = Lists.newArrayList();
		try{
			JSONObject jsStr = JSONObject.parseObject(decrypt_in_outCode); //将字符串{“id”：1}
      		JSONArray jsArr=(JSONArray)jsStr.get("data");
			inOutDetailUsedList = JSONArray.parseArray(jsArr.toJSONString(),InOutDetailUsed.class);
		}catch (Exception e) {
			uploadResult.setMessage("上传失败，请核对密文格式。");
			return uploadResult;
		}


		System.out.println(inOutDetailUsedList.size()+"----inOutDetailUsedList.size()---");
		for(InOutDetailUsed detailUsed:inOutDetailUsedList){
			detailUsed.setId(IdGen.uuid());
			detailUsed.setBaoCaiType(type);
		}

		try{
			inOutDetailUsedService.batchInsertInOutDetailUseds(inOutDetailUsedList);
		}catch (Exception e){
			uploadResult.setMessage("上传失败，您上传的数据有重复数据");
			uploadResult.setCode(2);
			return uploadResult;
		}
		uploadResult.setMessage("上传成功");
		uploadResult.setCode(1);
		return uploadResult;
	}


	public JsonResult writeFile(HttpServletRequest request, HttpServletResponse response,int batch,String type) throws IOException {
		List<String> nList = QRCodeUtil.getCode(50000, "N", type,batch);
		List<String> wList = QRCodeUtil.getCode(50000, "W", type,batch);
		int count = nList.size();
		System.out.println("count:---"+count);
		List<InOutDetail> inOutDetailList = Lists.newArrayList();
		StringBuffer sf = new StringBuffer("");
		sf.append("{");
		sf.append("data:[");
		for (int i = 0; i < count; i++) {
			sf.append("{");
			sf.append("W:");
			sf.append("'");
			sf.append(wList.get(i));
			sf.append("'");
			sf.append(",");

			sf.append("N:");
			sf.append("'");
			sf.append(nList.get(i));
			sf.append("'");
			sf.append("}");
			if (i != nList.size() - 1) {
				sf.append(",");
			}
			InOutDetail inOutDetail = new InOutDetail();
			inOutDetail.setId(IdGen.uuid());
			inOutDetail.setInCode(nList.get(i));
			inOutDetail.setOutCode(wList.get(i));
			inOutDetail.setBaoCaiType(type);
			inOutDetailList.add(inOutDetail);
		}
		sf.append("]");
		sf.append("}");
		String encode_str="";
		try {
			 encode_str = DesUtil.encrypt(sf.toString(),DesUtil.KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult result = new JsonResult();
		result = createFile(request);
		result.setInOutDetailList(inOutDetailList);
		result.setContent(sf.toString());
		result.setEncodes(encode_str);

		//FileUtils.writeToFile(result);
		CreateFileUtil.writeFile(encode_str,result.getFile());
		return result;
	}


	@RequestMapping(value = {"grantInOutCode"})
	@ResponseBody
	public GrantResult grantInOutCode(String loginName,String pwd,String type,
									 HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		JsonResult result = new JsonResult();
		GrantResult grantResult = new GrantResult();
		grantResult.setCode(2);
		grantResult.setMessage("获取失败");


		InOutGrant inOutGrant = new InOutGrant();

		// ---- first 获取登录名、密码、厂商，如果对应上生成密文
		if(!validateLogin(loginName, pwd, type)){
			grantResult.setMessage("您的用户名、密码、类型错误");
			return grantResult;
		}

		//1.获取批次
		Integer batch=0;
		synchronized (this){
			batch = inOutGrantService.getCountBatch(type);
			System.out.println("------------------批次----------------------"+batch);
			if(batch>60){
				grantResult.setMessage("您已超过今日下载次数。");
				return grantResult;
			}
			//2.生成txt文件并写入内外码
			result = writeFile(request,response,batch,type);

			String url = request.getScheme()+"://"
					+ Global.getConfig("subDomain")
					+":"
					+ request.getServerPort()
					+ request.getContextPath();
			System.out.println(url);
			String relativePath = result.getPath();
			result.setPath(url+result.getPath());
			//inOutGrant.setContent(result.getContent());
			//inOutGrant.setEncodes(result.getEncodes());
			inOutGrant.setFilePath(relativePath);
			inOutGrant.setSupplierType(type);
			//3.操作数据库
			inOutGrantService.addInOutGrant(inOutGrant);
		}
		System.out.println("synchronized----------"+batch);
		//4.异步批量插入数据库
		inOutDetailService.batchInsertInOutDetails(result.getInOutDetailList());

		grantResult.setMsg(result.getPath());
		grantResult.setMessage("获取成功");
		grantResult.setCode(1);
		return grantResult;

	}



	public boolean validateLogin(String loginName,String pwd,String type){
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(pwd) || StringUtils.isBlank(type)){
			return false;
		}
		String global_loginName = Global.getConfig("loginName_1");
		String global_loginName2 = Global.getConfig("loginName_2");
		String global_pwd = Global.getConfig("pwd_1");
		String global_pwd2 = Global.getConfig("pwd_2");
		String type_1 = Global.getConfig("type_1");
		String type_2 = Global.getConfig("type_2");
		if((loginName.equals(global_loginName) && pwd.equals(global_pwd) && type.equals(type_1)) ||
				(loginName.equals(global_loginName2) && pwd.equals(global_pwd2) && type.equals(type_2))){
			return true;
		}else {
			return false;
		}
	}



	@RequestMapping(value = {"downSuccess"})
	@ResponseBody
	public UploadResult downSuccess(String loginName,String pwd,String type,String flag,String fileName,
								   HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UploadResult uploadResult = new UploadResult();
		uploadResult.setMessage("移动失败");
		uploadResult.setCode(2);
		System.out.println("------------downSuccess-----start-----------------");
		/*if(request.getScheme().equals("http")){
			System.out.println("invalid request.getScheme()");
			uploadResult.setMessage("路径不合法。");
			return uploadResult;
		}*/
		if(!validateLogin(loginName, pwd, type)){
			System.out.println("loginName,pwd,type error");
			uploadResult.setMessage("您的用户名、密码、类型错误。");
			return uploadResult;
		}
		if(!flag.equals("success")){
			System.out.println("flag==success error");
			uploadResult.setMessage("回传标志错误");
			return uploadResult;
		}
		if(StringUtils.isBlank(fileName)){
			System.out.println("please provide fileName params");
			uploadResult.setMessage("请提供文件名参数");
			return uploadResult;
		}


		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		String datePath =  year+"\\"+month+"\\"+day+"\\";
		String realPath = DISK + "download\\"+datePath+fileName+".txt";

		String filePath = year+"/"+month+"/"+day+"/"+fileName+".txt";
		//todo
		String path="";
		String movePath="moveTo\\"+datePath;
		path = CreateFileUtil.moveFile(realPath,DISK,movePath);
		if(path.equals("error")){
			System.out.println("move fail。。");
		}

		String url = request.getScheme()+"://"
				+ request.getServerName()
				+":"+ request.getServerPort()
				+ request.getContextPath();
		System.out.println(url);

		// 更新数据库中 文件路径

		String newPath = path+filePath;
		String originalPath = "/download/"+filePath;
		InOutGrant inOutGrant = new InOutGrant();

		inOutGrant.setFilePath(newPath);
		inOutGrant.setOriginalFilePath(originalPath);
		inOutGrant.setSupplierType(type);
		try{
			inOutGrantService.updateGrantUrl(inOutGrant);
		}catch (Exception e){
			System.out.println("update url fail---"+e);
		}
		uploadResult.setMessage("移动成功");
		uploadResult.setCode(1);
		System.out.println("----------end----------downSuccess");
		return uploadResult;
	}


	/**
	 * 废弃方法
	 * @param loginName
	 * @param pwd
	 * @param type
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"uploadFailFile"})
	@ResponseBody
	public UploadResult uploadFailFile(String loginName,String pwd,String type,
								   HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		UploadResult uploadResult = new UploadResult();
		uploadResult.setMessage("上传失败");
		uploadResult.setCode(2);
		if(!validateLogin(loginName, pwd, type)){

			uploadResult.setMessage("您的用户名、密码、类型错误。");
			return uploadResult;
		}

		MultipartHttpServletRequest multipartRequest  = null;
		try{
			multipartRequest = 	(MultipartHttpServletRequest) request;
		}catch (Exception e){
			uploadResult.setMessage("上传失败，请上传文件。");
			return uploadResult;
		}

		//  （根据前台的name名称得到上传的文件）
		MultipartFile txtFile  =  multipartRequest.getFile("file");

		String url = request.getScheme()+"://"
				+ request.getServerName()
				+":"+ request.getServerPort()
				+ request.getContextPath();
		System.out.println(url);
		//文件绝对路径F:\IdeaProjects\weichai\trunk\target\rcs\

		//String realPath = request.getSession().getServletContext().getRealPath("/");
		String realPath = DISK;
		JsonResult jsonResult = new JsonResult();
		jsonResult = CreateFileUtil.uploadFile(txtFile, realPath,"uploadFailFile");
		String returnPath = jsonResult.getPath();


		String fileName=txtFile.getOriginalFilename();//获取原文件名
		InOutUsed inOutUsed = new InOutUsed();
		inOutUsed.setSupplierType(type);
		inOutUsed.setFileName(fileName);
		//todo
		/*inOutUsed.setFilePath(url+returnPath);*/
		inOutUsed.setFilePath(returnPath);
		inOutUsedService.addInOutUsed(inOutUsed);


		//去存当天使用的数据
		File file = jsonResult.getFile();
		String str_in_outCode = CreateFileUtil.readFile(file);
		String decrypt_in_outCode ="";
		try{
			decrypt_in_outCode = DesUtil.decrypt3(str_in_outCode,KEY);

		}catch (Exception e) {
			uploadResult.setMessage("上传失败，请核对密文格式。");
			return uploadResult;
		}

		List<InOutDetailUsed> inOutDetailUsedList = Lists.newArrayList();
		try{
			JSONObject jsStr = JSONObject.parseObject(decrypt_in_outCode); //将字符串{“id”：1}
			JSONArray jsArr=(JSONArray)jsStr.get("data");
			inOutDetailUsedList = JSONArray.parseArray(jsArr.toJSONString(),InOutDetailUsed.class);
		}catch (Exception e) {

			uploadResult.setMessage("上传失败，请核对密文格式。");
			return uploadResult;
		}


		System.out.println(inOutDetailUsedList.size()+"----inOutDetailUsedList.size()---");
		for(InOutDetailUsed detailUsed:inOutDetailUsedList){
			detailUsed.setId(IdGen.uuid());
			detailUsed.setBaoCaiType(type);
		}

		try{
			//todo 建新表
			inOutDetailUsedService.batchInsertInOutDetailUsedFailCode(inOutDetailUsedList);
		}catch (Exception e){
			uploadResult.setMessage("上传失败，您上传的数据有重复数据");
			uploadResult.setCode(2);
			return uploadResult;
		}

		uploadResult.setMessage("上传成功");
		uploadResult.setCode(1);
		return uploadResult;
	}




}