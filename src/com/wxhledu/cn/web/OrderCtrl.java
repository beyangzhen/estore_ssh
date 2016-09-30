package com.wxhledu.cn.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.domain.User;
import com.wxhledu.cn.service.OrderService;
import com.wxhledu.cn.service.impl.OrderServiceImpl;

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
@Slf4j
@Controller
public class OrderCtrl {
	
	private OrderService service = new OrderServiceImpl();

	
	/**
	 * 生成订单
	 * 访问路径 OrderCtrl?method=create&ids=
	 * ids 商品编号  以逗号分隔开
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public String create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 设置response的内部编码为utf-8（response内部，是另外一种编码格式）
		response.setContentType("text/html;charset=UTF-8");
		
		// 1.收集用户购物的商品编号 
		String ids = request.getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			// 2.当前登录用户信息
			User user = (User) request.getSession().getAttribute(UserCtrl.SESSION_LOGIN_USER_KEY);
			log.info("用户{} 买 {} 商品", user, ids);
			// 3.考虑返回？返回订单单号 -1
			try {
				String orderNum = service.createOrder(user.getId(), ids);
				response.getWriter().write(orderNum);
//				request.setAttribute(arg0, arg1);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		response.getWriter().write("-1");
		
		return "f:/WEB-INF/personCenter/order.jsp";
	}
	
	/**
	 * 个人中心
	 * 访问路径 OrderCtrl?method=personCenter&ids=
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public String personCenter(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "f:/WEB-INF/personCenter/index.jsp";
	}
	
	/**
	 * 账户余额
	 * 访问路径 OrderCtrl?method=amount&ids=
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public String amount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "f:/WEB-INF/personCenter/amount.jsp";
	}
	
}
