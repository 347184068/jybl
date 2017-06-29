package com.wfu.modules.weixin.front.web;

import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.util.JsApiUtil;
import com.wfu.common.config.Global;
import com.wfu.common.utils.DateUtils;
import com.wfu.common.utils.IdGen;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.sys.entity.*;
import com.wfu.modules.sys.service.*;
import com.wfu.modules.sys.utils.Constants;
import com.wfu.modules.weixin.entity.JsonData;
import com.wfu.modules.weixin.service.FrontService;
import com.wfu.modules.weixin.util.WebAPI;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.javac.jvm.ByteCodes.ret;
import static oracle.net.aso.C01.i;
import static oracle.net.aso.C01.r;
import static oracle.net.aso.C05.b;


/**
 * @Author XuYunXuan
 * @Date 2017/6/12 18:57
 */
@Controller
@RequestMapping(value = "/f/weixin")
public class FrontController extends BaseController {

    @Autowired
    private FrontService frontService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private UserBadrecordService userBadrecordService;

    @Autowired
    private BookReserveService bookReserveService;

    @Autowired
    private BookarticleService bookarticleService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRecommendService bookRecommendService;

    @RequestMapping(value = "index")
    public String index(Model model, String code, String state, HttpServletRequest request) throws Exception {
        //判断用户是否注册过
        HttpSession session = request.getSession();
        if (null != code) {
            if (null == session.getAttribute("openId")) {
                OauthAPI oauthAPI = new OauthAPI(WebAPI.getConfig());
                OauthGetTokenResponse oauthGetTokenResponse = new OauthGetTokenResponse();
                oauthGetTokenResponse = oauthAPI.getToken(code);
                String openId = oauthGetTokenResponse.getOpenid();
                if (StringUtils.isBlank(openId)) {
                    //提示请注册页面
                    return "modules/weixin/front/registerInfo";
                }
                session.setAttribute("openId", openId);
            }
            //判断用户是否存储在
            String openId = session.getAttribute("openId").toString();
            boolean flag = false;
            flag = frontService.isUserExistByOpenId(openId);
            if (!flag) {
                //提示请注册页面
                return "modules/weixin/front/registerInfo";
            }
        }
        initWeiXinParam(model, code, state, session);
        initLoginSuccessParam(model);
        return "modules/weixin/front/frontMain";
    }


    @RequestMapping(value = "firstView")
    public String redirectRegister(String code, String state,HttpSession session){
        if(code!=null){
            OauthAPI oauthAPI = new OauthAPI(WebAPI.getConfig());
            OauthGetTokenResponse oauthGetTokenResponse = new OauthGetTokenResponse();
            oauthGetTokenResponse = oauthAPI.getToken(code);
            String openId = oauthGetTokenResponse.getOpenid();
            boolean flag  =  frontService.isUserExistByOpenId(openId);
            if(!flag){
                session.setAttribute("openId", openId);
                return "modules/weixin/front/register";
            }else {
                //提示已经注册注册过了
                return "modules/weixin/front/registerInfo2";
            }
        }else{
            return "modules/weixin/front/registerInfo3";
        }
    }

    private void initWeiXinParam(Model model, String code, String state, HttpSession session) throws Exception {
        String openId = session.getAttribute("openId").toString();
        if (null != openId) {
            UserInfo userInfo = frontService.findUserByOpenId(openId);
            model.addAttribute("userInfo", userInfo);
        }
        /***
         * 2.验证url地址，signatrue
         */
        long timestamp = DateUtils.getNowTimeMillis();
        String nonceStr = IdGen.uuid();
//        String appUrl = "http://www.hong.xyz/f/weixin/index" + "?code=" + code + "&state=" + state;
        String appUrl = "http://rcs.tunnel.qydev.com/f/weixin/index" + "?code=" + code + "&state=" + state;
        //String visitUrl = WXPathUtils.getWXPath(code,state);
        model.addAttribute("appId", WebAPI.getConfig().getAppid());
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("nonceStr", nonceStr);
        String signature = JsApiUtil.sign(WebAPI.getConfig().getJsApiTicket(),
                nonceStr, timestamp, appUrl);
        model.addAttribute("signature", signature);
        model.addAttribute("openId", openId);
    }

    private void initLoginSuccessParam(Model model) {
        List<Book> recommendBookList = frontService.getRandBook(4);
        model.addAttribute("recommendBook", recommendBookList);
        List<Bookarticle> bookarticleList = bookarticleService.findList(new Bookarticle());
        int count = bookarticleList.size() <= 4 ? bookarticleList.size() : 4;
        List<Bookarticle> articles = new ArrayList<Bookarticle>();
        for (int i = 0; i < count; i++) {
            articles.add(bookarticleList.get(bookarticleList.size() - i - 1));
        }
        model.addAttribute("articles", articles);
        model.addAttribute("bookarticleList", bookarticleList);
    }


    @ResponseBody
    @RequestMapping(value = "getBorrowBook")
    public List<BookCustomer> getBorrowBook(HttpSession session) {
        List<BookCustomer> list = new ArrayList<BookCustomer>();
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setUserId(userInfo.getId());
        List<BookBorrow> bookBorrowList = bookBorrowService.findList(bookBorrow);
        for (BookBorrow b : bookBorrowList) {
            BookCustomer bookCustomer = new BookCustomer();
            Book book = bookService.get(b.getBookId());
            bookCustomer.setBook(book);
            if(b.getIsReturn().equals(Constants.BOOK_RETURN)){
                bookCustomer.setMsg("已还书");
            }else{
                if (b.getIsOvertime().equals(Constants.BOOK_OVERTIME)) {
                    bookCustomer.setMsg("已超期");
                } else {
                    if (b.getIsRenew().equals(Constants.BOOK_RENEW)) {
                        bookCustomer.setMsg("已续借(未还书)");
                    } else {
                        bookCustomer.setMsg("未续借(未还书)");
                    }
                }
            }
            list.add(bookCustomer);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getReserveBook")
    public List<BookCustomer> getReserveBook(HttpSession session) {
        List<BookCustomer> list = new ArrayList<BookCustomer>();
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookReserve bookReserve = new BookReserve();
        bookReserve.setUserId(userInfo.getId());
        List<BookReserve> bookReserves = bookReserveService.findList(bookReserve);
        for (BookReserve b : bookReserves) {
            BookCustomer bookCustomer = new BookCustomer();
            Book book = bookService.get(b.getBookId());
            bookCustomer.setBook(book);
            if (b.getIsOvertime().equals(Constants.BOOK_OVERTIME)) {
                bookCustomer.setMsg("已过期");
            } else {
                if (b.getIsOvertime().equals(Constants.BOOK_PICK)) {
                    bookCustomer.setMsg("已取书");
                } else {
                    bookCustomer.setMsg("未取书");
                }
            }
            list.add(bookCustomer);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getOverTimeBook")
    public List<BookCustomer> getOverTimeBook(HttpSession session) {
        List<BookCustomer> list = new ArrayList<BookCustomer>();
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setUserId(userInfo.getId());
        bookBorrow.setIsOvertime(Constants.BOOK_OVERTIME);
        List<BookBorrow> bookBorrowList = bookBorrowService.findList(bookBorrow);
        for (BookBorrow b : bookBorrowList) {
            BookCustomer bookCustomer = new BookCustomer();
            Book book = bookService.get(b.getBookId());
            bookCustomer.setBook(book);
            bookCustomer.setMsg("已超期,联系管理员");
            list.add(bookCustomer);
        }
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "recommend")
    public List<BookCustomer> recommendBook() {
        List<BookCustomer> list = new ArrayList<BookCustomer>();
        List<BookRecommend> bookRecommends = bookRecommendService.findList(new BookRecommend());
        for (BookRecommend bookRecommend : bookRecommends) {
            Book book = bookService.selectBookByIsbn(bookRecommend.getIsbn());
            Book realBook = bookService.get(book.getBookId());
            BookCustomer bookCustomer = new BookCustomer();
            bookCustomer.setBook(realBook);
            bookCustomer.setMsg(realBook.getCategoryCustomer().getCategoryName());
            list.add(bookCustomer);
        }
        return list;
    }


    @RequestMapping(value = "returnBook")
    public String returnBook(HttpSession session, Model model) {
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setUserId(userInfo.getId());
        bookBorrow.setIsReturn(Constants.BOOK_UNRETURN);
        List<BookBorrow> bookBorrows = bookBorrowService.findList(bookBorrow);
        List<Book> res = new ArrayList<Book>();
        for (BookBorrow b : bookBorrows) {
            res.add(bookService.get(b.getBookId()));
        }
        model.addAttribute("borrowList", res);
        model.addAttribute("userInfo", userInfo);
        return "modules/weixin/front/returnBook";
    }


    @ResponseBody
    @RequestMapping(value = "searchBook")
    public List<Book> searchBook(String type, String keyWord) {
        List<Book> bookList = frontService.searchBookByKeyWord(type, keyWord);
        return bookList;
    }

    @RequestMapping(value = "getBookArticle")
    public String getBookArticle(String id, Model model) {
        Bookarticle bookarticle = bookarticleService.get(id);
        model.addAttribute("bookarticle", bookarticle);
        return "modules/weixin/front/bookarticle";
    }


    @RequestMapping(value = "searchView")
    public String redirectSearchView() {
        return "modules/weixin/front/bookSearch";
    }


    @RequestMapping(value = "bookDetail")
    public String bookDetail(Model model, String bookId, HttpSession session) {
        //TODO 加入当前微信用户对本书的借阅预定情况查询
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setBookId(bookId);
        bookBorrow.setUserId(userInfo.getId());
        List<BookBorrow> bookBorrowList = bookBorrowService.findList(bookBorrow);
        Book book = frontService.getBookById(bookId);
        if (null == book) {
//            model.addAttribute("book",);
        } else {
            List<Book> bookList = frontService.getConnectionBook(book.getBookIsbn(), book.getCategoryCustomer().getCategoryId(), 4);
            model.addAttribute("book", book);
            model.addAttribute("bookList", bookList);
        }
        if (bookBorrowList.size() == 1) {
            model.addAttribute("borrowInfo", bookBorrowList.get(0));
        }
        return "modules/weixin/front/bookDetail";
    }


    @RequestMapping(value = "category")
    public String category(Model model, String categoryId) throws Exception {
        if (StringUtils.isNotBlank(categoryId)) {
            model.addAttribute("currentCategory", categoryId);
        }
        return "modules/weixin/front/bookCategory";
    }

    @ResponseBody
    @RequestMapping(value = "getAllCategory")
    public List<Category> getAllCategory() {
        return frontService.getAllCategory();
    }

    @ResponseBody
    @RequestMapping(value = "getBookByCategory")
    public List<Book> getBookByCategory(String categoryId) {
        return frontService.getBookByCategory(categoryId);
    }

    @ResponseBody
    @RequestMapping(value = "getBookById")
    public Book getBookById(String bookId) {
        return frontService.getBookById(bookId);
    }

    @RequestMapping(value = "register")
    public String register(UserInfo userInfo, HttpSession session) throws Exception {
        String openId = session.getAttribute("openId").toString();
        if (null == openId) {
            //到注册页面
            return "modules/weixin/front/registerInfo3";
        }
        boolean flag = frontService.isUserExistByOpenId(openId);
        if (!flag) {
            userInfo.setOpenid(openId);
            userInfoService.save(userInfo);
        }
        //提示注册成功
        return "modules/weixin/front/registerInfo1";
    }

    @ResponseBody
    @RequestMapping(value = "bookBorrow")
    public JsonData bookBorrrow() {
        JsonData jsonData = new JsonData();
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "bookReserve")
    public JsonData bookReserve(String bookId, int borrowTime, HttpSession session) {
        JsonData jsonData = new JsonData();
        try {
            String openId = session.getAttribute("openId").toString();
            UserInfo userInfo = userInfoService.getByOpenId(openId);
            BookReserve bookReserve = new BookReserve();
            bookReserve.setUserId(userInfo.getId());
            bookReserve.setBookId(bookId);
            List<BookReserve> list = bookReserveService.findList(bookReserve);
            if (list.size() == 0) {
                String time = DateUtils.getDate();
                bookReserve.setReserveTime(time);
                bookReserve.setPickTime(com.wfu.modules.sys.utils.DateUtils.addDay(time, borrowTime));
                bookReserve.setIsOvertime("0");
                bookReserve.setIsPick("0");
                bookReserveService.save(bookReserve);
                jsonData.setMsg("预定成功");
            } else {
                jsonData.setMsg("图书已经预订");
            }
        } catch (Exception e) {
            jsonData.setMsg("预定失败");
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "bookRenew")
    public JsonData bookRenew(String bookId, HttpSession session) throws ParseException {
        JsonData jsonData = new JsonData();
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        BookBorrow query = new BookBorrow();
        query.setBookId(bookId);
        query.setUserId(userInfo.getId());
        List<BookBorrow> bookBorrowList = bookBorrowService.findList(query);
        if (bookBorrowList.size() == 1) {
            BookBorrow bookBorrow = bookBorrowList.get(0);
            if (bookBorrow != null) {
                if (!bookBorrow.getIsOvertime().equals(Constants.BOOK_OVERTIME)) {
                    //TODO 需要加入不良借阅记录条数判断
                    if (!bookBorrow.getIsRenew().equals(Constants.BOOK_RENEW)) {
                        UserBadrecord userBadrecord = new UserBadrecord();
                        BookBorrow queryObj = new BookBorrow();
                        queryObj.setBookUserName(bookBorrow.getBookUserName());
                        userBadrecord.setBookBorrow(queryObj);
                        int badRecordCount = userBadrecordService.selectUserBadRecordCount(userBadrecord);
                        if (badRecordCount <= Constants.MAX_BADRECORD_COUNT) {
                            bookBorrow.setIsRenew(Constants.BOOK_RENEW);
                            String newReturnTime = com.wfu.modules.sys.utils.DateUtils.addMonth(bookBorrow.getReturnTime());
                            bookBorrow.setReturnTime(newReturnTime);
                            bookBorrowService.update(bookBorrow);
                            jsonData.setMsg("续借成功");
                            jsonData.setStatus("true");
                        } else {
                            jsonData.setMsg("不良记录过多，无法续借");
                            jsonData.setStatus("false");
                        }
                    } else {
                        jsonData.setMsg("超过最大续借次数");
                        jsonData.setStatus("false");
                    }
                } else {
                    jsonData.setMsg("图书已经超期,无法续借");
                    jsonData.setStatus("false");
                }
            } else {
                jsonData.setMsg("图书不存在");
                jsonData.setStatus("false");
            }
        } else {
            jsonData.setMsg("图书未借阅，无法续借");
            jsonData.setStatus("false");
        }
        return jsonData;
    }


    @ResponseBody
    @RequestMapping(value = "addBook")
    public JsonData<Book> addBook(String bookId) {
        JsonData<Book> jsonData = new JsonData<Book>();
        Book book = frontService.getBookById(bookId);
        if (null == book) {
            jsonData.setStatus("false");
            jsonData.setMsg("添加图书失败");
        } else {
            jsonData.setStatus("true");
            jsonData.setEntity(book);
            jsonData.setMsg("添加图书成功");
        }
        return jsonData;
    }


    @ResponseBody
    @RequestMapping(value = "isBookExist")
    public JsonData<Book> isBookExist(String bookId) {
        JsonData<Book> jsonData = new JsonData<Book>();
        Book book = frontService.getBookById(bookId);
        if (null == book) {
            jsonData.setStatus("false");
            jsonData.setMsg("图书不存在");
        } else {
            jsonData.setStatus("true");
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "getUserInfo")
    public UserInfo getUserInfo(HttpSession session) {
        String openId = session.getAttribute("openId").toString();
        UserInfo userInfo = frontService.findUserByOpenId(openId);
        return userInfo;
    }


    @ResponseBody
    @RequestMapping(value = "updateInfo")
    public JsonData updateUserInfo(UserInfo userInfo) {
        JsonData jsonData = new JsonData();
        try {
            userInfoService.update(userInfo);
            jsonData.setStatus("true");
            jsonData.setMsg("修改成功");
        } catch (Exception e) {
            jsonData.setStatus("false");
            jsonData.setMsg("修改失败");
        }
        return jsonData;
    }

}
