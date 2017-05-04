package com.weichai.modules.weixin.front;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.util.JsApiUtil;
import com.weichai.common.config.Global;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.StringUtils;
import com.weichai.common.web.BaseController;
import com.weichai.modules.register.entity.UserRegister;
import com.weichai.modules.register.service.UserRegisterService;
import com.weichai.modules.weixin.util.WebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by wwhui on 2016-4-8.
 */
@Controller
@RequestMapping("${frontPath}/wx/register")
public class FrontWxRegisterController extends BaseController {
    @Autowired
    private UserRegisterService userRegisterService;
    /**
     * 验证通过  jsAPi的配置
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public String index(Model model,String code,String state, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if(null ==session.getAttribute("openId")){
            /**
             * 1.get openId
             */
            OauthAPI oauthAPI=new OauthAPI(WebAPI.getConfig());
            OauthGetTokenResponse oauthGetTokenResponse = new OauthGetTokenResponse();
            oauthGetTokenResponse =  oauthAPI.getToken(code);

            String openId = oauthGetTokenResponse.getOpenid();
            if(StringUtils.isBlank(openId)){
                model.addAttribute("type","01");
                return "modules/register/register";
            }
            session.setAttribute("openId",openId);
        }
        if(null == session.getAttribute("userExist")){
            String openId = session.getAttribute("openId").toString();
            UserRegister userRegister = new UserRegister();
            userRegister.setOpenId(openId);
            boolean flag = false;
            flag = userRegisterService.validateRegister(userRegister);
            if(!flag){
                //服务号 用户类型type  01  待定 todo
                model.addAttribute("type","01");
                return "modules/register/register";
            }
           session.setAttribute("userExist",true);
        }


        String openId = session.getAttribute("openId").toString();
        /***
         * 2.验证url地址，signatrue
         */
        long timestamp= DateUtils.getNowTimeMillis();
        String  nonceStr= IdGen.uuid();
        String appUrl = Global.getConfig("AppUrl")+"?code="+code+"&state="+state;
        //String visitUrl = WXPathUtils.getWXPath(code,state);

        model.addAttribute("appId", WebAPI.getConfig().getAppid());
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("nonceStr",nonceStr);

        String signature= JsApiUtil.sign(WebAPI.getConfig().getJsApiTicket(),
                nonceStr,timestamp ,appUrl);


        model.addAttribute("signature",signature);
        model.addAttribute("openId", openId);

        return "modules/weixin/front/frontScan";
    }



    @RequestMapping("indexEnterprise")
    public String indexEnterprise(Model model,String code,String state) throws Exception {
        /**
         * 1.get openId
         */
        OauthAPI oauthAPI=new OauthAPI(WebAPI.getConfig());
        OauthGetTokenResponse oauthGetTokenResponse = new OauthGetTokenResponse();
        oauthGetTokenResponse =  oauthAPI.getToken(code);

        String openId = oauthGetTokenResponse.getOpenid();

        /***
         * 2.验证url地址，signatrue
         */

        long timestamp= DateUtils.getNowTimeMillis();
        String  nonceStr= IdGen.uuid();

        String appUrl = Global.getConfig("AppUrl")+"?code="+code+"&state="+state;
        //String appUrl = WXPathUtils.getWXPath(code,state);

        model.addAttribute("appId", WebAPI.getConfig().getAppid());
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("nonceStr",nonceStr);

       String signature= JsApiUtil.sign(WebAPI.getConfig().getJsApiTicket(),
                nonceStr,timestamp ,appUrl);
        model.addAttribute("signature",signature);
        model.addAttribute("openId", openId);

        //todo register by validate openId

        /**
         * 3.判断用户是否注册
         */
        /*if(StringUtils.isBlank(openId)){
            model.addAttribute("type","02");
            return "modules/register/register";
        }else if(StringUtils.isNotBlank(openId)){
            UserRegister userRegister = new UserRegister();
            userRegister.setOpenId(openId);
            boolean flag = false;
            flag = userRegisterService.validateRegister(userRegister);
            if(!flag){
                // 企业号 用户类型type  02  待定 todo
                model.addAttribute("type","02");
                return "modules/register/register";
            }
        }*/
        return "modules/weixin/front/frontScanEnterprise";
    }

}
