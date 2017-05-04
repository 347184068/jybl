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
@RequestMapping("${frontPath}/wx/success")
public class FrontWxRegisterSuccessController extends BaseController {
    @Autowired
    private UserRegisterService userRegisterService;
    /**
     * 验证通过  jsAPi的配置
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public String index(Model model,HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String openId = "";
        /***
         * 2.验证url地址，signatrue
         */
        long timestamp= DateUtils.getNowTimeMillis();
        String  nonceStr= IdGen.uuid();
        String appUrl = WXPathUtils.getWXSuccessPath();
        model.addAttribute("appId", WebAPI.getConfig().getAppid());
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("nonceStr",nonceStr);

        String signature= JsApiUtil.sign(WebAPI.getConfig().getJsApiTicket(),
                nonceStr,timestamp ,appUrl);

        model.addAttribute("signature",signature);
        model.addAttribute("openId", openId);

        return "modules/weixin/front/frontScan";
    }

}
