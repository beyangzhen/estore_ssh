package com.wxhledu.cn.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wxhledu.cn.domain.CartItem;
import com.wxhledu.cn.domain.PageBean;
import com.wxhledu.cn.domain.User;
import com.wxhledu.cn.service.CartService;
import com.wxhledu.cn.service.impl.CartServiceImpl;
import com.wxhledu.cn.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 购物车模块
 * @author wxhl
 *
 */
@Slf4j
@Controller
public class CartCtrl {

	private CartService service = new CartServiceImpl();

	/**
	 * 商品添加到购物车
	 * 
	 * 路径：CartCtrl?method=add&bookid=
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取图书编号
		String bookid = request.getParameter("bookid");
		int bid;
		try {
			bid = Integer.parseInt(bookid);
		} catch (Exception e) {
			response.getWriter().write("您拨打的电话是110 ！！！");
			return;
		}
		log.info("添加购物图书, 编号：{}", bookid);
		
		// 获取session中的用户信息
		User user = (User) request.getSession().getAttribute(UserCtrl.SESSION_LOGIN_USER_KEY);
		
		int result = 0;
		try {
			if (null != user) {
				// 添加图书编号+用户编号到购物车
				result = service.add(bid, user.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		}
		response.getWriter().write(result + "");
	}
	
	/**
	 * 删除商品 （不用ajax请求）
	 * CartCtrl?method=deleteItems&ids=1,2,3
	 * ids 商品编号 以,分隔
	 * p 分页参数：当前页
	 * @param request
	 * @param response
	 * @return 
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		log.info("{}", request.getParameter("p"));
		// 获取删除的商品的编号和商品所在页码
		String id = request.getParameter("id");
		try {
			service.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 删除后整个页面刷新（重新获取购物车信息）
		// return "r:/CartCtrl?method=list&p="+request.getParameter("p");//list(request, response);
		return list(request, response);
	}
	
	/**
	 * 改变商品数量（代替了前面的增加/减少商品数量）
	 * CartCtrl?method=addQuantity&id=&action=
	 * id 商品编号
	 * action +|-
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void modifyQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json"); //返回json字符串。如果返回内容有中文就考虑设置字符集 
		String id = request.getParameter("id"); // 数据库支持类型不转换情况，可考虑java代码不换类型
		String action = request.getParameter("action");
		log.info("商品编号: {}", id);
		// 保存到数据库里，查询商品的数量和总钱返回
		CartItem item =null;
		try {
			item = service.modifyQuantityByItemid(id, action);
			// 把数量和总金额返回
			String result = "{\"total\":" + item.getQuantity() + ",\"money\":" + (item.getQuantity()*item.getPrice())+"}";
			response.getWriter().write(result);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 失败情况
		response.getWriter().write("{}");
	}
	
	/**
	 * 购物车列表
	 * 
	 * 访问路径：CartCtrl?method=list
	 * @param request
	 * @param response
	 * @return
	 */
	public String list(HttpServletRequest request, HttpServletResponse response) {
		log.info("访问购物车列表");
		// 获取用户信息
		User user = (User) request.getSession().getAttribute(UserCtrl.SESSION_LOGIN_USER_KEY);
		
		// 获取当前页码
		String p = request.getParameter("p");
		int page = CommonUtil.getIntValue(p, 1);
		try {
			// 根据用户编号查找购物车的商品分页信息
			PageBean<CartItem> pb = service.getMyCart(user.getId(), page);
			request.setAttribute("pb", pb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "f:/WEB-INF/cart/index.jsp";
	}
	
	
	/**
	 * 处理商品添加
	 * CartCtrl?method=addQuantity&id=
	 * id 商品编号
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@Deprecated
    public void addQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json"); //返回json字符串。如果返回内容有中文就考虑设置字符集 
		String id = request.getParameter("id"); // 数据库支持类型不转换情况，可考虑java代码不换类型
		log.info("商品编号: {}", id);
		// 保存到数据库里，查询商品的数量和总钱返回
		CartItem item =null;
		try {
			item = service.addQuantityByItemid(id);
			// 把数量和总金额返回
			String result = "{\"total\":" + item.getQuantity() + ",\"money\":" + (item.getQuantity()*item.getPrice())+"}";
			response.getWriter().write(result);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 失败情况
		response.getWriter().write("{}");
	}
	
}
