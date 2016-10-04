package com.wxhledu.cn.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.web.UserCtrl;

import lombok.extern.slf4j.Slf4j;


/**
 * 过滤未登录用户的请求
 * 
 * @author Administrator
 *
 */
@Slf4j
public class UserExistFilter implements Filter {

    public UserExistFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
	}
    
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req; // ServletRequest不能获取session（没有getSession方法）
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		
		Object user = session.getAttribute(UserCtrl.SESSION_LOGIN_USER_KEY);
		if(user == null) {
			log.error("请您，先登录！！！");
			
			String xRequest = request.getHeader("x-requested-with");
			System.out.println("**********************" + request.getRequestURI());
			System.out.println("**********************" + request.getQueryString());
			System.out.println("**********************" + request.getParameterNames());
			// 请求是异步请求（请求头为 x-requested-with:XMLHttpRequest）
			if(StringUtils.isNotBlank(xRequest)){
				response.getWriter().write("Asyn");
			// 请求不是异步请求（异步请求时，不会改变页面的url。所以用servlet的跳转无效，但是响应体中还是有跳转的页面）
			} else {
				// 未登录的用户直接调转到登录页面：src中java文件			       ，绝对路径访问：(1) 重定向：项目名/...
			    // 													 		   (2) 转发   ：/...（不需要项目名）
				//  					   WebContext下的jsp/css/js ，绝对路径访问：${pageContext.request.contextPath }/...
				// 												    --> ${pageContext.request.contextPath }是 项目名/
				response.sendRedirect(request.getContextPath() + "/user/login.jsp");
			}
			
			// 未登录的都return，不放行（不执行doFilter方法） --> 重定向让response已经响应回去了，doFilter()再调用response会报"响应已经提交的错误"
			return;
		}
		
		chain.doFilter(request, response);
	}

}
