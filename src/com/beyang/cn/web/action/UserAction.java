package com.beyang.cn.web.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.beyang.cn.domain.User;
import com.beyang.cn.service.UserService;
import com.beyang.cn.util.CaptchaUtil;
import com.opensymphony.xwork2.ActionSupport;

import lombok.extern.slf4j.Slf4j;

@Namespace("/user")
@ParentPackage("struts-default")
@Controller
@Scope("protoType")
@Slf4j
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = -69664787922965339L;
	
	// 获取用户输入
	private String username;
	private String password;
	private String vcode;
	
	private String goUrl; // 上一个页面的url

	/**
	 * 登录的验证码
	 */
	private static final String SESSION_LOGIN_VCODE_KEY = "login_vcode";
	/**
	 * 登录的用户
	 */
	public static final String SESSION_LOGIN_USER_KEY = "login_user";
	/**
	 * 验证码的宽度
	 */
	private static final int WIDTH = 128;
	/**
	 * 验证码的高度
	 */
	private static final int HEIGHT = 44;
	
	@Autowired
	private UserService userService;
	
	// action中获取request、response、session对象
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();

	/**
	 * 处理用户登录 UserCtrl?method=login
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Action(value="login", results={
			@Result(name="index", location="/"),
			@Result(name="goUrl", type="redirect", location="${goUrl}"), // 默认是 "转发" 的方式
			@Result(name="error", location="/user/login.jsp")
	})
	public String login() throws ServletException, IOException {
		
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
			return ERROR;
		} else {
			// 到数据库查询用户和密码
			User user = new User(username, password);
			try {
				User u = userService.findUser(user);
				if(null != u){ //数据库里存在此用户
					// 如果找到则用户信息保存到session里，并且返回到首页
					session.setAttribute(SESSION_LOGIN_USER_KEY, u);
					
					// 判断session中上一个url是否为空
					String goUrl = (String)session.getAttribute("goUrl");
					// 将goUrl设置到action的request域中，然后在location中使用el表达式获取
					this.setGoUrl(goUrl); 
					
					if(null != goUrl) {
						// 登录后回到之前的页面
						return "goUrl";
					} else {
						// 用户直接访问登录页面时，转到首页
						return "index";
					}
				} else {
					// 如果用户不存在,则用户要重新登录
					return ERROR;
				}
			} catch (SQLException e) {
				// e.printStackTrace();
				log.error("查询用户异常：{}", e.getMessage());
			}
		}

		return null; // 成功 f:首页 失败:r:登录
	}

	/**
	 * 注销  http://localhost:8080/estore/user/logout.action
	 * 
	 * @return
	 */
	@Action(value="logout", results={
			@Result(name="success", location="/")
	})
	public String logout() {
		log.info("注销用户");
		// 将登录时存放到session里的对象清除 用户信息(30分钟)，验证码（创建时应该设置它存活时间3-5）
		session.removeAttribute(SESSION_LOGIN_USER_KEY);
		
		return SUCCESS;
	}
	
	/**
	 * 处理注册 http://localhost:8080/estore/user/register.action
	 * 
	 * @return
	 */
	@Action(value="register")
	public String register() {
		log.info("username:{},password:{},vcode:{}", username, password, vcode);
		
		return null;
	}
	
	/**
	 * 用户登录时的验证码 http://localhost:8080/estore/user/vcode.action
	 * 
	 * @return
	 * @throws IOException 
	 */
	@Action(value="vcode")
	public String vcode() throws IOException {
		// 对图片不缓存
		response.setHeader("Cache-Control", "no-cache");
		// 生成随机的验证码
		String vcode = CaptchaUtil.getRandomString(4);
		// 将验证码保存到session
		session.setAttribute(SESSION_LOGIN_VCODE_KEY, vcode);
		// 输出验证码
		CaptchaUtil.outputImage(WIDTH, HEIGHT, response.getOutputStream(), vcode);
		
		return null;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getGoUrl() {
		return goUrl;
	}
	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
	
}
