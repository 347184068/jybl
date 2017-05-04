package com.weichai.modules.filegen.filter;

import com.weichai.common.config.Global;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.StringUtils;
import com.weichai.modules.filegen.dao.DownloadFileRecDao;
import com.weichai.modules.filegen.entity.DownloadFileRec;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by tepusoft on 2017/4/5.
 */

public class GrantFileFilter implements Filter {

    private DownloadFileRecDao downloadFileRecDao;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter生效。。。。。");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        ServletContext sc = request.getSession().getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);

        if(cxt != null && downloadFileRecDao == null){
            downloadFileRecDao = (DownloadFileRecDao) cxt.getBean("downloadFileRecDao");
        }

        String loginName = req.getParameter("loginName");
        String pwd = req.getParameter("pwd");
        String type=req.getParameter("type");
        String uri = ((ShiroHttpServletRequest) req).getRequestURI();
        String contextPath = ((ShiroHttpServletRequest) req).getContextPath();
        String url = uri.replace(contextPath,"");
        String addr = request.getRemoteAddr();
        String txtType=url.indexOf(".")!=-1?url.substring(url.lastIndexOf(".")+1, url.length()):null;
        if(txtType==null || !"TXT".equals(txtType.toUpperCase())){
            //todo 处理
        }
        if(validateLogin(loginName,pwd,type)){
            //todo 建表
            DownloadFileRec fileRec = new DownloadFileRec();
            fileRec.setId(IdGen.uuid());
            fileRec.setLoginName(loginName);
            fileRec.setFilePath(url);
            fileRec.setSupplierType(type);
            fileRec.setPwd(pwd);
            fileRec.setAddress(addr);
            downloadFileRecDao.insertDownloadFileRec(fileRec);
            chain.doFilter(req, resp);
           }

    }

    public void init(FilterConfig config) throws ServletException {
    }

    public boolean validateLogin(String loginName,String pwd,String type){
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(pwd) || StringUtils.isBlank(type)){
            return false;
        }
        String global_loginName = Global.getConfig("loginName_1");
        String global_loginName2 = Global.getConfig("loginName_2");
        String global_pwd = Global.getConfig("pwd_1");
        String global_pwd2 = Global.getConfig("pwd_2");
        String type_1 = Global.getConfig("type_1");
        String type_2 = Global.getConfig("type_2");
        if((loginName.equals(global_loginName) && pwd.equals(global_pwd) && type.equals(type_1)) ||
                (loginName.equals(global_loginName2) && pwd.equals(global_pwd2) && type.equals(type_2))){
            return true;
        }else {
            return false;
        }
    }


}

