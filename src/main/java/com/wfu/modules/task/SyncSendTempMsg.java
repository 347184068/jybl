package com.wfu.modules.task;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.entity.TemplateParam;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.wfu.common.utils.DateUtils;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.BookBorrow;
import com.wfu.modules.weixin.entity.WxUser;
import com.wfu.modules.weixin.util.WebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 还书提醒模板消息
 *
 * @Author XuYunXuan
 * @Date 2017/6/27 15:59
 */
@Component
public class SyncSendTempMsg {
    @Autowired
    private TaskExecutor taskExecutor;

    private TemplateMsgAPI templateMsgAPI = new TemplateMsgAPI(WebAPI.getConfig());

    public SyncSendTempMsg() {
    }

    public void sendTempMsgThread(String openId, Book book, BookBorrow b) {
        taskExecutor.execute(new SendTempMsgThread(openId, book, b));
    }

    class SendTempMsgThread implements Runnable {
        private String openId;
        private Book book;
        private BookBorrow b;

        public SendTempMsgThread(String openId, Book book, BookBorrow b) {
            this.openId = openId;
            this.book = book;
            this.b = b;
        }

        @Override
        public void run() {
            TemplateMsg templateMsg = new TemplateMsg();
            templateMsg.setTouser(this.openId);
            templateMsg.setTemplateId("wD_6WUuF9Qoxai-1ZYbKg8O_KXb8Lrf0WXUgfCcGhTI");
            Map<String, TemplateParam> map = new HashMap<String, TemplateParam>();
            map.put("bookName", new TemplateParam(book.getBookName(), "#00bfff"));
            map.put("borrowTime", new TemplateParam(b.getBorrowTime(), "#00bfff"));
            map.put("returnTime", new TemplateParam(b.getReturnTime(), "#00bfff"));
            templateMsg.setData(map);
            templateMsg.setTopcolor("#FF0000");
            System.out.println(templateMsgAPI.send(templateMsg).getMsgid());
        }
    }
}
