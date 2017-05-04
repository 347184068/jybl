package com.weichai.modules.weixin.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.ImageReqMsg;
import com.github.sd4324530.fastweixin.message.req.LocationReqMsg;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import com.weichai.modules.weixin.input.handler.KeyWordHandler;
import com.weichai.modules.weixin.input.handler.SubscribeHandler;
import com.weichai.modules.weixin.input.handler.UnsubscribeHandler;

/**
 * Created by wwhui on 2016-4-6.
 */
@RequestMapping("/wx/")
@Controller
public class WxController extends WeixinControllerSupport {
    private static final Logger LOG  = LoggerFactory.getLogger(WxController.class);
    @Autowired
    private KeyWordHandler keyWordHandler;//关键字管理
    @Autowired
    private SubscribeHandler subscribeHandler;//关注时的处理事件
    @Autowired
    private UnsubscribeHandler unsubscribeHandler;//取消关注时的事件
    @Override
    protected String getToken() {
        return "tepusoft";
    }

    /**
     * 关注时的事件
     * @param event
     * @return
     */
    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
         return subscribeHandler.handle(event);
       // return super.handleSubscribe(event);
    }

    /**
     * 取消关注
     * @param event
     * @return
     */
    @Override
    protected BaseMsg handleUnsubscribe(BaseEvent event) {
        LOG.error("取消是的关注"+event.getFromUserName());
        return unsubscribeHandler.handle(event);
    }

    /**
     * 处理文本消息
     * @param msg
     * @return
     */
    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
         return keyWordHandler.handle(msg);
    }

    /***
     * 图片事件的处理
     * @param msg
     * @return
     */
    @Override
    protected BaseMsg handleImageMsg(ImageReqMsg msg) {
        return super.handleImageMsg(msg);
    }

    /***
     * 处理click事件
     * @param event
     * @return
     */
    @Override
    protected BaseMsg handleMenuClickEvent(MenuEvent event) {
        return super.handleMenuClickEvent(event);
    }

    /**
     * 处理地理位置消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    @Override
    protected BaseMsg handleLocationMsg(LocationReqMsg msg) {
        return handleDefaultMsg(msg);
    }

    /**
     * 默认处理方法。 默认上传地理位置
     * @param event
     * @return
     */
    protected BaseMsg handleDefaultEvent(BaseEvent event) {

        return null;
    }
}
