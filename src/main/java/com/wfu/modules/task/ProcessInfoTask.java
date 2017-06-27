package com.wfu.modules.task;

import com.wfu.common.utils.DateUtils;
import com.wfu.common.utils.SpringContextHolder;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.BookBorrow;
import com.wfu.modules.sys.entity.BookReserve;
import com.wfu.modules.sys.entity.UserInfo;
import com.wfu.modules.sys.service.BookBorrowService;
import com.wfu.modules.sys.service.BookReserveService;
import com.wfu.modules.sys.service.UserInfoService;
import com.wfu.modules.sys.utils.Constants;
import com.wfu.modules.weixin.job.SyncWxUserInfoJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static oracle.net.aso.C01.s;

/**
 * @Author XuYunXuan
 * @Date 2017/6/27 15:13
 */
@Service
@Lazy(false)
public class ProcessInfoTask {

    @Autowired
    private BookReserveService bookReserveService;

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 处理图书借阅超期，如果距离还书时间还剩1周则发送模板消息提醒
     * 如果超过则直接超期处理
     *
     * date2 -date1 的天数
     * 还书日期减去当前日期的天数，如果《=7则发送消息
     * 如果《=0则设置超期
     */
    @Scheduled(cron = "0 0 00 * * ?")
    public void processBookOverTime(){
        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setIsOvertime(Constants.BOOK_UNOVERTIME);
        bookBorrow.setIsReturn(Constants.BOOK_UNRETURN);
        List<BookBorrow> bookBorrows = bookBorrowService.findList(bookBorrow);
        for(BookBorrow b : bookBorrows){
            String currentTime = DateUtils.getDate();
            String returnTime = b.getReturnTime();
            Date date1 = com.wfu.modules.sys.utils.DateUtils.StringToDate(currentTime);
            Date date2= com.wfu.modules.sys.utils.DateUtils.StringToDate(returnTime);
            int subDays = com.wfu.modules.sys.utils.DateUtils.daysOfTwo(date1,date2);
            if(subDays<0){
                //设置超期
                b.setIsOvertime(Constants.BOOK_OVERTIME);
                bookBorrowService.update(b);
            }else {
                if(subDays<=7){
                    //发送模板的提醒消息
                    String userId = b.getUserId();
                    UserInfo userInfo = userInfoService.get(userId);
                    if(userInfo!=null){
                        SyncSendTempMsg job = SpringContextHolder.getBean(SyncSendTempMsg.class);
                        job.sendTempMsgThread(userInfo.getOpenid());
                    }
                }
            }
        }
    }
    /**
     * 预定图书过期处理
     */
    @Scheduled(cron = "0 0 00 * * ?")
    public void processReserveBook(){
        List<BookReserve> bookReserveList = bookReserveService.findList(new BookReserve());
        for(BookReserve b : bookReserveList){
            if(!b.getIsOvertime().equals(Constants.BOOK_OVERTIME)){
                String currentTime = DateUtils.getDate();
                String pickTime = b.getPickTime();
                Date date1 = com.wfu.modules.sys.utils.DateUtils.StringToDate(currentTime);
                Date date2= com.wfu.modules.sys.utils.DateUtils.StringToDate(pickTime);
                if(com.wfu.modules.sys.utils.DateUtils.daysOfTwo(date1,date2)<0){
                    b.setIsOvertime(Constants.BOOK_OVERTIME);
                    bookReserveService.update(b);
                }
            }
        }
    }

}
