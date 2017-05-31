package com.wfu.common.filter;

import com.wfu.common.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter implements Filter {

	 FilterConfig filterConfig = null;

	    public void init(FilterConfig filterConfig) throws ServletException {
	        this.filterConfig = filterConfig;
	    }

	    public void destroy() {
	        this.filterConfig = null;
	    }

	    public void doFilter(ServletRequest request, ServletResponse response,
	            FilterChain chain) throws IOException, ServletException {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse)response;
			//主机ip和端口  或 域名和端口 
			String myhosts = req.getHeader("host");
			if(!StringUtils.equals(myhosts, "scmstest.wfu.com:8443")
					&& !StringUtils.equals(myhosts, "10.0.11.162:8443")
					){
				System.out.println("======访问host非法，已拦截======"+myhosts);
				//res.sendRedirect(req.getContextPath() + "/a");
				return;
			}
	        chain.doFilter(new XssHttpServletRequestWrapperNew(
	                    (HttpServletRequest) request), response);
	    }


}
