package com.wfu.modules.weixin.input.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.google.common.collect.Lists;
import com.wfu.common.config.Global;
import com.wfu.common.utils.StringUtils;
import com.wfu.common.utils.SystemPath;
import com.wfu.modules.weixin.entity.WxArticle;
import com.wfu.modules.weixin.entity.WxArticleCollect;
import com.wfu.modules.weixin.entity.WxAutoKeyword;
import com.wfu.modules.weixin.service.WxArticleCollectService;
import com.wfu.modules.weixin.service.WxArticleService;
import com.wfu.modules.weixin.service.WxAutoKeywordService;

/**
 * Created by wwhui on 2016-4-7.
 * 关键词回复 的处理方式
 */
@Component
public class KeyWordHandler  extends WxHandler{
    @Autowired
    private WxAutoKeywordService keywordService;
    @Autowired
    private WxArticleCollectService wxArticleCollectService;
    @Autowired
    private WxArticleService wxArticleService;

    /***
     * TODO 实现 关键字层级的划分需要借助缓存来实现
     * @param msg
     * @return
     */
    public BaseMsg handle(TextReqMsg msg) {
        WxAutoKeyword wxAutoKeyword=keywordService.findByKey(msg.getContent());
        if(wxAutoKeyword!=null){
            if("news".equals(wxAutoKeyword.getMsgType())){

                return createNews(wxAutoKeyword.getMsgId());
            }
            return new TextMsg(wxAutoKeyword.getName());
        }
        return new TextMsg("您的消息已经收到");
    }

    public BaseMsg createNews(String msgId) {
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
