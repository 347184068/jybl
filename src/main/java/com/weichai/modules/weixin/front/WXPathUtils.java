package com.weichai.modules.weixin.front;

import com.weichai.common.config.Global;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tepusoft on 2017/3/25.
 */
public class WXPathUtils {
    public static String getWXPath(String code,String state){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+ Global.getConfig("subDomain")
               /* +":"+request.getServerPort()*/
                +path+Global.getFrontPath()+"/wx/index"+"?code="+code+"&state="+state;
        return basePath;
    }

    public static String getWXEnterprisePath(String code,String state){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+ Global.getConfig("subDomain")
               /* +":"+request.getServerPort()*/
                +path+Global.getFrontPath()+"/wx/indexEnterprise"+"?code="+code+"&state="+state;
        return basePath;
    }


    public static String getWXSuccessPath(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+ Global.getConfig("subDomain")
               /* +":"+request.getServerPort()*/
                +path+Global.getFrontPath()+"/wx/success/index";
        return basePath;
    }




}
