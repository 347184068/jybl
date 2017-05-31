package com.wfu.modules.weixin.input.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.google.common.collect.Lists;
import com.wfu.common.config.Global;
import com.wfu.common.utils.SpringContextHolder;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.utils.SystemPath;
import com.wfu.modules.weixin.entity.WxArticle;
import com.wfu.modules.weixin.entity.WxArticleCollect;
import com.wfu.modules.weixin.entity.WxSubscribe;
import com.wfu.modules.weixin.entity.WxUser;
import com.wfu.modules.weixin.job.SyncWxUserInfoJob;
import com.wfu.modules.weixin.service.WxArticleCollectService;
import com.wfu.modules.weixin.service.WxArticleService;
import com.wfu.modules.weixin.service.WxSubscribeService;
import com.wfu.modules.weixin.service.WxUserService;

/**
 * Created by wwhui on 2016-4-7.
 * 关注时的回复的处理
 */
@Component
public class SubscribeHandler  extends WxHandler {
    @Autowired
    private WxSubscribeService wxSubscribeService;
    @Autowired
    private WxArticleCollectService wxArticleCollectService;
    @Autowired
    private WxArticleService wxArticleService;
    @Autowired
    private WxUserService wxUserService;

    public BaseMsg handle(BaseEvent event) {
      String openId=event.getFromUserName();
       saveUser(openId);
      List<WxSubscribe> wxList=wxSubscribeService.findList(null);
       if(wxList!=null&&wxList.size()>0){
           WxSubscribe wxSubscribe=wxList.get(0);
           if("view".equals(wxSubscribe.getMsgType())){
               return createNews(wxSubscribe.getMsgId());
           }else {
               return new TextMsg(wxSubscribe.getName());
           }
       }
        return new TextMsg("欢迎关注");
    }

    /***
     *保存关注的用户
     * @param openId
     */
    private void saveUser(String openId) {
        WxUser  wxUser=  wxUserService.getByOpenId(openId);
        if(wxUser==null){
            wxUser=new WxUser();
        }
        wxUser.setOpenid(openId);
        wxUserService.save(wxUser);
        SyncWxUserInfoJob job= SpringContextHolder.getBean(SyncWxUserInfoJob.class);
        job.syncWxinUserInfo(openId);
    }

    private BaseMsg createNews(String msgId) {
        WxArticleCollect wxArticleCollect=wxArticleCollectService.get(msgId);
        String[] ids=wxArticleCollect.getArticleList().split(",");
        NewsMsg newsMsg=new NewsMsg();
        List<Article> articleList= Lists.newArrayList();
        for(String id :ids){
            WxArticle wxArticle=wxArticleService.get(id);
            Article  article=new Article();
            article.setTitle(wxArticle.getTitle());
            article.setDescription(StringUtils.substring( wxArticle.getContent(),100));
            article.setPicUrl(SystemPath.getServerPath()+wxArticle.getShowCoverPic());
            article.setUrl(SystemPath.getServerPath()+ Global.getFrontPath()+"wx/index");
            articleList.add(article);
        }
        newsMsg.setArticles(articleList);
        return newsMsg;
    }


}
