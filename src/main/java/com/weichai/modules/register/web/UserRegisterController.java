package com.weichai.modules.register.web;

import com.weichai.common.web.BaseController;
import com.weichai.modules.filegen.utils.ScanResult;
import com.weichai.modules.register.entity.UserRegister;
import com.weichai.modules.register.service.UserRegisterService;
import com.weichai.modules.validatecode.service.InOutProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping(value = "user/userRegister")
public class UserRegisterController extends BaseController {

	@Autowired
	private UserRegisterService userRegisterService;


	@RequestMapping(value = {"register"})
	public String register(UserRegister userRegister,Model model,HttpServletRequest request,
						   String appId,String timestamp,String nonceStr,String signature) {


		String account = userRegister.getAccount();
		String password = userRegister.getPassword();

		model.addAttribute("openId",userRegister.getOpenId());
		model.addAttribute("type",userRegister.getType());

		Pattern pt_account = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
		Pattern pt_pwd = Pattern.compile("[A-Za-z0-9_]{1,20}$");
		Matcher matcher_account = pt_account.matcher(account);
		Matcher matcher_pwd = pt_pwd.matcher(password);

		model.addAttribute("appId",appId);
		model.addAttribute("timestamp",timestamp);
		model.addAttribute("nonceStr",nonceStr);
		model.addAttribute("signature",signature);

		if(!matcher_account.matches() ||  !matcher_pwd.matches()){
			model.addAttribute("msg","请核对用户名、密码格式");
			return  "modules/register/register";
		}

		UserRegister user = new UserRegister();
		user.setAccount(account);
		if(userRegisterService.validateRegister(user)){
			model.addAttribute("msg","该账号已注册");
			return "modules/register/register";
			//
		}
		int n=0;
		n= userRegisterService.addUserRegister(userRegister);
		if(n>0){
			/*if(userRegister.getType().equals("01")){
			}*/
			 return "modules/register/successJump";
		}else{
			model.addAttribute("msg","注册失败");
			return "modules/register/register";
		}
	}


	@RequestMapping(value = {"gotoScanPage"})
	public String gotoScanPage(Model model,HttpServletRequest request) {
		return "redirect:"+frontPath+"/wx/success/index";
	}



	}