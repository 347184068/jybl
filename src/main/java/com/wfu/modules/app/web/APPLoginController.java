package com.wfu.modules.app.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.wfu.common.utils.DateUtils;
import com.wfu.common.web.BaseController;
import com.wfu.modules.app.entity.*;
import com.wfu.modules.app.entity.BookCustomer;
import com.wfu.modules.app.service.BookUserService;
import com.wfu.modules.sys.entity.*;
import com.wfu.modules.sys.service.*;
import com.wfu.modules.sys.web.BookReserveController;
import com.wfu.modules.weixin.entity.JsonData;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.BAD_CONTEXT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static oracle.net.aso.C05.b;
import static oracle.net.aso.C05.c;
import static oracle.net.aso.C05.e;

/**
 * @Author XuYunXuan
 * @Date 2017/6/25 15:38
 */

@Controller
@RequestMapping("/app")
public class APPLoginController extends BaseController {

    @Autowired
    private BookUserService bookUserService;

    @Autowired
    private UserBadrecordService userBadrecordService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private BookReserveService bookReserveService;

    @ResponseBody
    @RequestMapping(value = "login")
    public JsonData login(String user, String password){


        JsonData jsonData = new JsonData();
        if(user == null || user.equals("")) {
            jsonData.setStatus("false");
            jsonData.setMsg("用户不存在！！！");
            return jsonData;
        }

        BookUser bookUser = bookUserService.findBookUserByName(user);


        if(bookUser == null) {
            jsonData.setStatus("false");
            jsonData.setMsg("用户不存在！！！");
            return jsonData;
        }

        if(!bookUser.getPassword().equals(password)) {
            jsonData.setStatus("false");
            jsonData.setMsg("密码错误！！！");
            return jsonData;
        }

        jsonData.setStatus("true");
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "getBorrowInfo")
    public UserInfoCustomer queryBookInfo(String bookjson) {
//        String bookjson = "{\"userId\": \"oTy9uxHhC2YNnz6V3sN0tCxfd6SE\",\"bookList\": [{\"bookId\": \"2ea686fd-e0f8-42b2-83d1-41c1eef72598\",\"count\": \"1\"},{\"bookId\": \"68571dbf-9c64-4934-b371-3438da82f4a5\",\"count\": \"1\"}]}\n";
        bookjson =  StringEscapeUtils.unescapeHtml4(bookjson);
        BookJsonBean bookJsonBean = JSONUtil.toBean(bookjson, BookJsonBean.class);
        List<BookCustomer> bookList = new ArrayList<BookCustomer>();
        List<BookList> list = bookJsonBean.getBookList();

        for(int i = 0;i < list.size();i++) {
            String bookId = list.get(i).getBookId();
            Book book = bookService.get(bookId);

            BookCustomer bookCustomer = new BookCustomer();
            bookCustomer.setBookAuthor(book.getBookAuthor());
            bookCustomer.setBookImage(book.getBookImage());
            bookCustomer.setBookIsbn(book.getBookIsbn());
            bookCustomer.setBookName(book.getBookName());
            bookCustomer.setBookPublisherid(book.getBookPublisher().getPublisherName());

            bookList.add(bookCustomer);
        }

        UserInfo user = userInfoService.getByOpenId(bookJsonBean.getUserId());


        UserInfoCustomer userInfoCustomer = new UserInfoCustomer();
        userInfoCustomer.setUsername(user.getPersonName());
        userInfoCustomer.setListbook(bookList);

        return userInfoCustomer;
    }


    @ResponseBody
    @RequestMapping(value = "borrowBook")
    public JsonData borrrowBook(String bookjson) {
        JsonData jsonData = new JsonData();

        if(StringUtils.isBlank(bookjson)){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }

        bookjson =  StringEscapeUtils.unescapeHtml4(bookjson);
        BookJsonBean bookJsonBean = null;
        try{
            bookJsonBean = JSONUtil.toBean(bookjson, BookJsonBean.class);
        } catch (Exception e){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }
        if(bookJsonBean == null){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }
        UserInfo userInfo =  userInfoService.getByOpenId(bookJsonBean.getUserId());
        String userId = userInfo.getId();
        if(userId.trim().equals("") || userId == null) {
            jsonData.setStatus("false");
            jsonData.setMsg("用户名不能为空！！");
            return jsonData;
        }
        //判断借的书是否>2本;
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setUserId(userId);
        bookBorrow.setIsReturn("0");
        List<BookBorrow> list = bookBorrowService.findList(bookBorrow);
        if(list.size() >= 2) {
            jsonData.setStatus("false");
            jsonData.setMsg("最多可以借两本书！！！");
            return jsonData;
        }

        //判断是否有超期的书;
        bookBorrow.setIsReturn(null);
        bookBorrow.setIsOvertime("1");
        list = bookBorrowService.findList(bookBorrow);
        if(list.size() > 0) {
            jsonData.setStatus("false");
            jsonData.setMsg("您有超期未还的书！！！");
            return jsonData;
        }


        List<BookList> borrowList = bookJsonBean.getBookList();
        for(int i = 0;i < borrowList.size();i++) {

            //向bookBorrow表里插入新记录;
            BookBorrow newBorrow = new BookBorrow();
            newBorrow.setBorrowId(UUID.randomUUID().toString());
            newBorrow.setUserId(userInfo.getId());
            newBorrow.setBookId(borrowList.get(i).getBookId());
            newBorrow.setIsOvertime("0");
            newBorrow.setIsReturn("0");
            newBorrow.setIsConfirm("0");
            newBorrow.setIsRenew("0");
            Date now = new Date();
            String date = DateUtils.formatDate(now);
            newBorrow.setBorrowTime(date);
            newBorrow.setReturnTime(DateUtils.formatDate(DateUtils.addMonths(now, 1)));
            bookBorrowService.save(newBorrow);

            //更改图书状态;
            Book book = bookService.get(borrowList.get(i).getBookId());
            book.setBookCollections(String.valueOf(Integer.parseInt(book.getBookCollections())-1));
            bookService.update(book);

            //查看是否预定了图书;
            BookReserve bookReserve = new BookReserve();
            bookReserve.setBookId(borrowList.get(i).getBookId());
            bookReserve.setUserId(userInfo.getId());
            bookReserve.setIsOvertime("0");
            bookReserve.setIsPick("0");
            List<BookReserve> reserveList = bookReserveService.findList(bookReserve);

            //如果预定了就更改预定表的状态;
            if (reserveList.size() > 0) {
                bookReserve = reserveList.get(0);
                bookReserve.setIsPick("1");
                bookReserve.setPickTime(date);
                bookReserveService.update(bookReserve);
            }

        }

        jsonData.setStatus("true");
        jsonData.setMsg("借书成功！！！");
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "returnConfirm")
    public JsonData confirmBookReturn(String bookjson){
        JsonData jsonData = new JsonData();

        if(StringUtils.isBlank(bookjson)){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }

        bookjson =  StringEscapeUtils.unescapeHtml4(bookjson);
        BookJsonBean bookJsonBean = null;
        try{
            bookJsonBean = JSONUtil.toBean(bookjson, BookJsonBean.class);
        } catch (Exception e){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }
        if(bookJsonBean == null){
            jsonData.setStatus("false");
            jsonData.setMsg("格式错误");
            return jsonData;
        }
        UserInfo userInfo =  userInfoService.getByOpenId(bookJsonBean.getUserId());
        String userId = userInfo.getId();

        List<BookList> borrowList = bookJsonBean.getBookList();
        for(int i = 0;i < borrowList.size();i++) {

            BookBorrow bookBorrow = new BookBorrow();
            bookBorrow.setUserId(userId);
            bookBorrow.setIsReturn("0");
            bookBorrow.setBookId(borrowList.get(i).getBookId());
            List<BookBorrow> bookBorrowListList = bookBorrowService.findList(bookBorrow);
            bookBorrow = bookBorrowListList.get(0);

            //判断过期;
            if(bookBorrow.getIsOvertime().equals("1")) {
                UserBadrecord badrecord = new UserBadrecord();
                badrecord.setBorrowId(bookBorrow.getBorrowId());
                badrecord.setBookBorrow(bookBorrow);
                userBadrecordService.save(badrecord);
            }

            bookBorrow.setIsConfirm("1");
            bookBorrow.setIsReturn("1");
            bookBorrowService.update(bookBorrow);

            //更改图书库存
            Book book = bookService.get(borrowList.get(i).getBookId());
            book.setBookCollections(String.valueOf(Integer.parseInt(book.getBookCollections())+1));
            bookService.update(book);

        }


        jsonData.setStatus("true");
        jsonData.setMsg("还书成功！！！");
        return jsonData;
    }

}












