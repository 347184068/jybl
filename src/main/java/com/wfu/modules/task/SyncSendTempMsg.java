package com.wfu.modules.task;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.wfu.common.utils.DateUtils;
import com.wfu.modules.weixin.entity.WxUser;
import com.wfu.modules.weixin.util.WebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Author XuYunXuan
 * @Date 2017/6/27 15:59
 */
@Component
public class SyncSendTempMsg {
    @Autowired
    private TaskExecutor taskExecutor;

    private TemplateMsgAPI templateMsgAPI=new TemplateMsgAPI(WebAPI.getConfig());

    public SyncSendTempMsg(){}

    public void sendTempMsgThread(String openId){
        taskExecutor.execute(new SendTempMsgThread(openId));
    }
    class SendTempMsgThread implements Runnable {
        private String openId;
        public SendTempMsgThread(String openId){
            this.openId=openId;
        }
        @Override
        public void run() {

        }
    }
}
