package com.wfu.tools;

import com.wfu.modules.sys.entity.BookBorrow;
import com.wfu.modules.sys.entity.UserBadrecord;
import com.wfu.modules.sys.service.UserBadrecordService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static oracle.net.aso.C01.s;

/**
 * @Author XuYunXuan
 * @Date 2017/6/6 19:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml"
        ,"classpath*:spring-context.xml"
        ,"classpath*:spring-context-shiro.xml"
        ,"classpath*:spring-context-jedis.xml"})
public class Test {

    @Autowired
    private UserBadrecordService userBadrecordService;

    @org.junit.Test
    public void badRecordTest() {
        UserBadrecord userBadrecord = new UserBadrecord();
        BookBorrow queryObj = new BookBorrow();
        queryObj.setBookUserName("a");
        userBadrecord.setBookBorrow(queryObj);
        int badRecordCount = userBadrecordService.selectUserBadRecordCount(userBadrecord);
        System.out.println(badRecordCount);
    }

}
