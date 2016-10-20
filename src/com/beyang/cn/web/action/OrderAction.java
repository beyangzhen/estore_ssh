package com.beyang.cn.web.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.beyang.cn.service.OrderService;
import com.opensymphony.xwork2.ActionSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * 用来处理订单
 * OrderCtrl&method=
 * 1. 扫描进去 @Controller
 * 2. web.xml 加入控制路径
 * 
 * @author administrator
 *
 */
@Namespace("/order")
@ParentPackage("struts-default")
@Controller
@Scope("protoType")
@Slf4j
public class OrderAction extends ActionSupport {
	
	private static final long serialVersionUID = -5703040103369846344L;
	
	private String ids; // 收集用户购物的商品编号
	
	@Autowired
	private OrderService orderService;
	
	// action中获取request、response对象
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	
	/**
	 * 生成订单 http://localhost:8080/estore/order/create.action
	 * ids 商品编号  以逗号分隔开
	 * 
	 * @throws IOException 
	 */
	@Action(value="create", results={
			@Result(name="success", location="/WEB-INF/personCenter/order.jsp")
	})
	public String create() throws IOException {
		
		// 设置response的内部编码为utf-8（response内部，是另外一种编码格式）
		response.setContentType("text/html;charset=UTF-8");
		
		if (StringUtils.isNotBlank(ids)) {
			// 2.当前登录用户信息
			User user = (User) request.getSession().getAttribute(UserAction.SESSION_LOGIN_USER_KEY);
			log.info("用户{} 买 {} 商品", user, ids);
			// 3.考虑返回？返回订单单号 -1
			try {
				String orderNum = orderService.createOrder(user.getId(), ids);
				response.getWriter().write(orderNum);
//				request.setAttribute(arg0, arg1);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		response.getWriter().write("-1");
		
		return SUCCESS;
	}
	
	/**
	 * 个人中心 http://localhost:8080/estore/order/personCenter.action
	 * 
	 */
	@Action(value="personCenter", results={
			@Result(name="success", location="/WEB-INF/personCenter/index.jsp")
	})
	public String personCenter() throws IOException {
		return SUCCESS;
	}
	
	/**
	 * 账户余额 http://localhost:8080/estore/order/amount.action
	 * 
	 */
	@Action(value="amount", results={
			@Result(name="success", location="/WEB-INF/personCenter/amount.jsp")
	})
	public String amount() throws IOException {
		return SUCCESS;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
