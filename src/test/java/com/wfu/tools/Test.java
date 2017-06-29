package com.wfu.tools;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.entity.TemplateParam;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.wfu.common.utils.SpringContextHolder;
import com.wfu.modules.sys.entity.*;
import com.wfu.modules.sys.service.BookBorrowService;
import com.wfu.modules.sys.service.BookReserveService;
import com.wfu.modules.sys.service.BookService;
import com.wfu.modules.sys.service.UserBadrecordService;
import com.wfu.modules.task.SyncSendTempMsg;
import com.wfu.modules.weixin.service.FrontService;
import com.wfu.modules.weixin.util.WebAPI;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static oracle.net.aso.C05.b;

/**
 * @Author XuYunXuan
 * @Date 2017/6/6 19:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml"
        , "classpath*:spring-context.xml"
        , "classpath*:spring-context-shiro.xml"
        , "classpath*:spring-context-jedis.xml"})
public class Test {

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private UserBadrecordService userBadrecordService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FrontService frontService;

    @Autowired
    private BookReserveService bookReserveService;

    @org.junit.Test
    public void test01() {
        Book book = bookService.get("039bd8fe-eae2-4d8f-81f1-cf793cbeea70");
        book.setBookCollections(String.valueOf(Integer.parseInt(book.getBookCollections())-1));
        bookService.update(book);

    }

    @org.junit.Test
    public void badRecordTest() {
        UserBadrecord userBadrecord = new UserBadrecord();
        BookBorrow queryObj = new BookBorrow();
        queryObj.setBookUserName("a");
        userBadrecord.setBookBorrow(queryObj);
        int badRecordCount = userBadrecordService.selectUserBadRecordCount(userBadrecord);
        System.out.println(badRecordCount);
    }


    @org.junit.Test
    public void testGetBookByCategory() {
        //查询某分类下指定图书数量
        //文学分类下当前只有3本书
        Category c = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", 0);
        Category c1 = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", 1);
        Category c2 = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", 2);
        Category c3 = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", null);
        Category c4 = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", c3.getBookList().size() + 1);
        Category c5 = frontService.showBookByCategory("3eba5c033108454b89f1da012f72d88e", 3);
        Assert.assertEquals(0, c.getBookList().size());
        Assert.assertEquals(1, c1.getBookList().size());
        Assert.assertEquals(2, c2.getBookList().size());
        Assert.assertEquals(3, c3.getBookList().size());
        Assert.assertEquals(3, c4.getBookList().size());
        Assert.assertEquals(3, c5.getBookList().size());
    }

    @org.junit.Test
    public void testRandBook() {
        List<Book> list = frontService.getRandBook(3);
        Assert.assertEquals(3, list.size());
        for (Book b : list) {
            System.out.println(b.getBookName());
        }
    }


    @org.junit.Test
    public void testGetBookById(){
        Book book = frontService.getBookById("153b6003-d9c9-4344-8165-ceace4d689f9");
        Assert.assertNotNull(book);
    }

    @org.junit.Test
    public void testTempMsg(){
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid("oTy9uxHhC2YNnz6V3sN0tCxfd6SE");
        Book book  = new Book();
        book.setBookName("嫌疑人x的献身");
        book.setBookIsbn("1234134");
        BookBorrow b = new BookBorrow();
        b.setBorrowTime("2017-01");
        b.setReturnTime("2017-07");
        TemplateMsg templateMsg = new TemplateMsg();
        templateMsg.setTouser(userInfo.getOpenid());
        templateMsg.setTemplateId("qJQ8iArYRc8b-EdyQtno6U-CgHGtgvfa1FLTyJyWWAg");
        Map<String, TemplateParam> map = new HashMap<String, TemplateParam>();
        map.put("bookName", new TemplateParam(book.getBookName(), "#00bfff"));
        map.put("bookISBN", new TemplateParam(book.getBookIsbn(), "#00bfff"));
        map.put("borrowTime", new TemplateParam(b.getBorrowTime(), "#00bfff"));
        map.put("returnTime", new TemplateParam(b.getReturnTime(), "#00bfff"));
        templateMsg.setData(map);
        templateMsg.setTopcolor("#FF0000");
        TemplateMsgAPI templateMsgAPI = new TemplateMsgAPI(WebAPI.getConfig());
        System.out.println(templateMsgAPI.send(templateMsg).getMsgid());
    }



}
