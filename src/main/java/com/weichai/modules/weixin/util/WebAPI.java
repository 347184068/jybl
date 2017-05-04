package com.weichai.modules.weixin.util;

import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.company.api.QYOauthAPI;
import com.github.sd4324530.fastweixin.company.api.config.QYAPIConfig;
import com.weichai.common.config.Global;

/**
 * Created by wwhui on 2016-4-5.
 */
public class WebAPI {
 private static ApiConfig config;
 private static QYAPIConfig qyapiConfig;

  public static ApiConfig getConfig(){
      String appId = Global.getConfig("AppId");
      String appSecret = Global.getConfig("AppSecret");
    if(config==null){
      config=new ApiConfig(appId,appSecret,true);
    }
   return config;
  }

   public static QYAPIConfig getQYConfig(){
       String appId = Global.getConfig("CorpId");
       String appSecret = Global.getConfig("CorpSecret");
       if(qyapiConfig==null){
           qyapiConfig=new QYAPIConfig(appId,appSecret,true);
       }
       return qyapiConfig;
   }

}
