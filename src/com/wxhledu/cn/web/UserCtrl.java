package com.wxhledu.cn.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.domain.User;
import com.wxhledu.cn.service.UserService;
import com.wxhledu.cn.service.impl.UserServiceImpl;
import com.wxhledu.cn.util.CaptchaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserCtrl {

	
	/**
	 * 登录的验证码
	 */
	private static final String SESSION_LOGIN_VCODE_KEY = "login_vcode";
	/**
	 * 登录的用户
	 */
	public static final String SESSION_LOGIN_USER_KEY = "login_user";
	/**
	 * 重定向到首页
	 */
	private static final String HOME_PAGE_URL = "r:/";
	/**
	 * 验证码的宽度
	 */
	private static final int WIDTH = 128;
	/**
	 * 验证码的高度
	 */
	private static final int HEIGHT = 44;
	
	private UserService service = new UserServiceImpl();

	/**
	 * 处理用户登录 UserCtrl?method=login
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户输入
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		log.info("username:{}, password:{}, vcode:{}", username, password, vcode);
		
		List<String> errors = new ArrayList<>();
		// 再次验证必填字段不为空
		if (StringUtils.isBlank(username)) {
			errors.add("用户账号为空！");
		}
		if (StringUtils.isBlank(password)) {
			errors.add("用户密码为空！");
		}
		if (StringUtils.isBlank(vcode)) {
			errors.add("验证码为空！");
		}
		HttpSession session = request.getSession();
		// 验证码信息取回
		String loginVcode = (String) session.getAttribute(SESSION_LOGIN_VCODE_KEY);
		log.info("服务器验证码:{}", loginVcode);
		if (StringUtils.isBlank(loginVcode)) {
			errors.add("服务器验证码找不到！");
		}
		if(!StringUtils.equals(vcode, loginVcode)){
			errors.add("验证码不匹配！");
		}
		
		// 将提示信息发送到页面上
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			return "f:/user/login.jsp";
		} else {
			// 到数据库查询用户和密码
			User user = new User(username, password);
			try {
				User u = service.findUser(user);
				if(null != u){ //数据库里存在此用户
					// 如果找到则用户信息保存到session里，并且返回到首页
					session.setAttribute(SESSION_LOGIN_USER_KEY, u);
					
					// 判断session中上一个url是否为空
					String goUrl = (String)session.getAttribute("goUrl");
					if(null != goUrl) {
						// 登录后回到之前的页面
						return "r:" + goUrl;
					} else {
						// 用户直接访问登录页面时，转到首页
						return HOME_PAGE_URL;
					}
				} else {
					// 如果用户不存在,则用户要重新登录
					return "r:/user/login.jsp";
				}
			} catch (SQLException e) {
				// e.printStackTrace();
				log.error("查询用户异常：{}", e.getMessage());
			}
		}

		return null; // 成功 f:首页 失败:r:登录
	}

	/**
	 * 注销 UserCtrl?method=logout
	 * @param request
	 * @param response
	 * @return
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		log.info("注销用户");
		// 1. 将登录时存放到session里的对象清除 用户信息(30分钟)，验证码（创建时应该设置它存活时间3-5）
		request.getSession().removeAttribute(SESSION_LOGIN_USER_KEY);
		
		return HOME_PAGE_URL;
	}
	
	/**
	 * 处理注册
	 * @param request
	 * @param response
	 * @return
	 */
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		log.info("username:{},password:{},vcode:{}", username, password, vcode);
		
		return null;
	}
	
	/**
	 * 用户登录时的验证码 UserCtrl?method=vcode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public String vcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 3.对图片不缓存
		response.setHeader("Cache-Control", "no-cache");
		// 1.生成随机的验证码
		String vcode = CaptchaUtil.getRandomString(4);
		// 4.将验证码保存到session
		request.getSession().setAttribute(SESSION_LOGIN_VCODE_KEY, vcode);
		// 2.输出验证码
		CaptchaUtil.outputImage(WIDTH, HEIGHT, response.getOutputStream(), vcode);
		
		return null;
	}
}
