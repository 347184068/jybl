package com.weichai.modules.weixin.front;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.company.api.QYOauthAPI;
import com.github.sd4324530.fastweixin.company.api.QYUserAPI;
import com.github.sd4324530.fastweixin.company.api.response.GetOauthUserInfoResponse;
import com.github.sd4324530.fastweixin.company.api.response.GetQYUserInfoResponse;
import com.github.sd4324530.fastweixin.util.JsApiUtil;
import com.weichai.common.utils.StringUtils;
import com.weichai.modules.register.entity.UserRegister;
import com.weichai.modules.register.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weichai.common.config.Global;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.SystemPath;
import com.weichai.common.web.BaseController;
import com.weichai.modules.weixin.util.WebAPI;

/**
 * Created by wwhui on 2016-4-8.
 */
@Controller
@RequestMapping("${frontPath}/wx/")
public class FrontWxController extends BaseController {
    @Autowired
    private UserRegisterService userRegisterService;
    /**
     * 验证通过  jsAPi的配置
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public String index(Model model,String code,String state) throws Exception {
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
        //String appUrl = Global.getConfig("AppUrl")+"?code="+code+"&state="+state;
        String appUrl = WXPathUtils.getWXPath(code,state);
        System.out.println(appUrl+"-------appUrl---");
        model.addAttribute("appId", WebAPI.getConfig().getAppid());
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("nonceStr",nonceStr);

        String signature= JsApiUtil.sign(WebAPI.getConfig().getJsApiTicket(),
                nonceStr,timestamp ,appUrl);
        model.addAttribute("signature",signature);
        model.addAttribute("openId", openId);
        //return "modules/weixin/front/frontScanEnterprise";
        return "modules/weixin/front/frontScan";
    }



    @RequestMapping("indexEnterprise")
    public String indexEnterprise(Model model,String code,String state) throws Exception {
        /**
         * 1.get openId
         */
        QYOauthAPI oauthAPI=new QYOauthAPI(WebAPI.getQYConfig());

        QYUserAPI qyUserAPI = new QYUserAPI(WebAPI.getQYConfig());
        GetOauthUserInfoResponse getQYUserInfoResponse = new GetOauthUserInfoResponse();
        getQYUserInfoResponse = qyUserAPI.getOauthUserInfo(code);

        String openId = getQYUserInfoResponse.getUserid();
        System.out.println("userId-----"+openId);
        /**
         * 2.验证url地址，signatrue
         */

        long timestamp= DateUtils.getNowTimeMillis();
        String  nonceStr= IdGen.uuid();



        //String appUrl = Global.getConfig("CorpUrl")+"?code="+code+"&state="+state;
        String appUrl = WXPathUtils.getWXEnterprisePath(code, state);

        System.out.println("qi ye hao-----appUrl----"+appUrl);
        model.addAttribute("appId", WebAPI.getQYConfig().getCorpid());
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("nonceStr",nonceStr);

       String signature= JsApiUtil.sign(WebAPI.getQYConfig().getJsApiTicket(),
                nonceStr,timestamp ,appUrl);
        model.addAttribute("signature",signature);
        model.addAttribute("openId", openId);

        return "modules/weixin/front/frontScanEnterprise";
    }

}
