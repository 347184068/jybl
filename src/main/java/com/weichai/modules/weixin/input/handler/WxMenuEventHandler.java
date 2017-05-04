package com.weichai.modules.weixin.input.handler;

import org.springframework.stereotype.Component;

import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;

/**
 * Created by wwhui on 2016-4-6.
 */
@Component
public class WxMenuEventHandler extends WxHandler {
    public BaseMsg handle(MenuEvent event) {
        return null;
    }


}
