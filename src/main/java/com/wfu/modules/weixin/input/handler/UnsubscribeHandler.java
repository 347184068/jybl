package com.wfu.modules.weixin.input.handler;

import org.springframework.stereotype.Component;

import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;

/**
 * Created by wwhui on 2016-4-7.
 * 取消关注的方法
 */
@Component
public class UnsubscribeHandler extends WxHandler {
    public BaseMsg handle(BaseEvent event) {

        return null;
    }


}
