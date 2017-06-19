package com.wfu.modules.weixin.front.web;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.util.JsApiUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.tools.classfile.Opcode;
import com.sun.tools.corba.se.idl.StringGen;
import com.wfu.common.config.Global;
import com.wfu.common.utils.DateUtils;
import com.wfu.common.utils.IdGen;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.Category;
import com.wfu.modules.weixin.front.WXPathUtils;
import com.wfu.modules.weixin.service.FrontService;
import com.wfu.modules.weixin.util.WebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;


/**
 * @Author XuYunXuan
 * @Date 2017/6/12 18:57
 */
@Controller
@RequestMapping(value = "${frontPath}/weixin")
public class FrontController extends BaseController {

    @Autowired
    private FrontService frontService;

    @RequestMapping(value = "index")
    public String index(Model model) throws Exception {
        List<Book> recommendBookList = frontService.getRandBook(4);
        model.addAttribute("recommendBook",recommendBookList);
        return "modules/weixin/front/frontMain";
    }



    @ResponseBody
    @RequestMapping(value = "searchBook")
    public List<Book> searchBook(String type, String keyWord){
        List<Book> bookList = frontService.searchBookByKeyWord(type, keyWord);
        return bookList;
    }


    @RequestMapping(value = "searchView")
    public String redirectSearchView(){
        return "modules/weixin/front/bookSearch";
    }


    @RequestMapping(value = "bookDetail")
    public String bookDetail(Model model,String bookId){
        Book book = frontService.getBookById(bookId);
        if(null == book) {
//            model.addAttribute("book",);
        }else {
            List<Book> bookList = frontService.getConnectionBook(book.getBookIsbn(),book.getCategoryCustomer().getCategoryId(),4);
            model.addAttribute("book",book);
            model.addAttribute("bookList",bookList);
        }
        return "modules/weixin/front/bookDetail";
    }


    @RequestMapping(value = "category")
    public String category(Model model,String categoryId) throws Exception {
        if(StringUtils.isNotBlank(categoryId)){
            model.addAttribute("currentCategory",categoryId);
        }
        return "modules/weixin/front/bookCategory";
    }

    @ResponseBody
    @RequestMapping(value = "getAllCategory")
    public List<Category> getAllCategory(){
        return frontService.getAllCategory();
    }

    @ResponseBody
    @RequestMapping(value = "getBookByCategory")
    public List<Book> getBookByCategory(String categoryId){
        return frontService.getBookByCategory(categoryId);
    }

    @ResponseBody
    @RequestMapping(value = "getBookById")
    public Book getBookById(String bookId){
        return frontService.getBookById(bookId);
    }
    @RequestMapping(value = "register")
    public String register(Model model,String code,String state, HttpServletRequest request) throws Exception {
        //判断用户是否注册过
        HttpSession session = request.getSession();
        if(null ==session.getAttribute("openId")){
            OauthAPI oauthAPI=new OauthAPI(WebAPI.getConfig());
            OauthGetTokenResponse oauthGetTokenResponse = new OauthGetTokenResponse();
            oauthGetTokenResponse =  oauthAPI.getToken(code);
            String openId = oauthGetTokenResponse.getOpenid();
            if(StringUtils.isBlank(openId)){
                return "modules/register/register";
            }
            session.setAttribute("openId",openId);
        }
        if(null == session.getAttribute("userExist")){
            //判断用户是否存储在
            String openId = session.getAttribute("openId").toString();
            boolean flag = false;
//            UserRegister userRegister = new UserRegister();
//            userRegister.setOpenId(openId);
//            boolean flag = false;
//            flag = userRegisterService.validateRegister(userRegister);
            if(!flag){
                //TODO
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
        return "modules/weixin/front/frontMain";
    }




}
