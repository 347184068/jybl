package com.wfu.modules.filter;

import com.wfu.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by tepusoft on 2017/4/6.
 */
public class HttpSchemeFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(HttpSchemeFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String httpHeader = request.getScheme();
        if(StringUtils.isBlank(httpHeader) || "http".equals(httpHeader)){
            logger.info(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
                +request.getContextPath()+request.getServletPath());
        }else{
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
