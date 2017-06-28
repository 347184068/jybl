package com.wfu.modules.task;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.wfu.common.utils.DateUtils;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.weixin.entity.WxUser;
import com.wfu.modules.weixin.util.WebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 还书提醒模板消息
 * @Author XuYunXuan
 * @Date 2017/6/27 15:59
 */
@Component
public class SyncSendTempMsg {
    @Autowired
    private TaskExecutor taskExecutor;

    private TemplateMsgAPI templateMsgAPI=new TemplateMsgAPI(WebAPI.getConfig());

    public SyncSendTempMsg(){}

    public void sendTempMsgThread(String openId, Book book){
        taskExecutor.execute(new SendTempMsgThread(openId,book));
    }
    class SendTempMsgThread implements Runnable {
        private String openId;
        private Book book;
        public SendTempMsgThread(String openId ,Book book){
            this.openId=openId;
            this.book = book;
        }
        @Override
        public void run() {
            TemplateMsg templateMsg = new TemplateMsg();
            templateMsg.setTouser(this.openId);
            templateMsg.setTemplateId("");
            templateMsg.setData(null);
            templateMsg.setTopcolor("");
            templateMsgAPI.send(templateMsg);
        }
    }
}
