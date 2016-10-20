package com.beyang.cn.web.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.beyang.cn.domain.CartItem;
import com.beyang.cn.domain.PageBean;
import com.beyang.cn.domain.User;
import com.beyang.cn.service.CartService;
import com.beyang.cn.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * 购物车模块
 * @author wxhl
 *
 */
@Namespace("/cart")
@ParentPackage("struts-default")
@Controller
@Scope("protoType")
@Slf4j
public class CartAction extends ActionSupport {

	private static final long serialVersionUID = -7429368737415636375L;
	
	private String bookid; // 获取图书编号
	private String p; // 商品所在页码
	private String id; // 获取删除的商品的编号
	private String action; // 添加或减少商品数量
	
	@Autowired
	private CartService cartService;
	
	// action中获取request、response对象
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();

	/**
	 * 商品添加到购物车 http://localhost:8080/estore/cart/add.action
	 * 
	 * @throws IOException 
	 */
	@Action(value="add")
	public void add() throws IOException {
		// 获取图书编号
		int bid;
		try {
			bid = Integer.parseInt(bookid);
		} catch (Exception e) {
			response.getWriter().write("您拨打的电话是110 ！！！");
			return;
		}
		log.info("添加购物图书, 编号：{}", bookid);
		
		// 获取session中的用户信息
		User user = (User) request.getSession().getAttribute(UserAction.SESSION_LOGIN_USER_KEY);
		
		int result = 0;
		try {
			if (null != user) {
				// 添加图书编号+用户编号到购物车
				result = cartService.add(bid, user.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		}
		response.getWriter().write(result + "");
	}
	
	/**
	 * 删除商品 （不用ajax请求）http://localhost:8080/estore/cart/delect.action
	 * 
	 * ids 商品编号 以,分隔
	 * p 分页参数：当前页
	 * 
	 * @return 
	 */
	@Action(value="delete",results={
			@Result(name="success", location="list", type="chain") // action中方法之间相互调用
	})
	public String delete() {
		log.info("{}", p);
		
		try {
			cartService.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 删除后整个页面刷新（重新获取购物车信息）
		// return "r:/CartCtrl?method=list&p="+request.getParameter("p");//list(request, response);
		return SUCCESS;
	}
	
	/**
	 * 改变商品数量（代替了前面的增加/减少商品数量）http://localhost:8080/estore/cart/modifyQuantity.action
	 * id 商品编号
	 * action +|-
	 * 
	 * @throws IOException 
	 */
	@Action(value="modifyQuantity")
	public void modifyQuantity() throws IOException{
		log.info("商品编号: {}", id);
		
		response.setContentType("application/json"); //返回json字符串。如果返回内容有中文就考虑设置字符集 
		// 保存到数据库里，查询商品的数量和总钱返回
		CartItem item = null;
		try {
			item = cartService.modifyQuantityByItemid(id, action);
			System.out.println("***************************" + item.getQuantity() + "" + item.getQuantity()*item.getPrice());
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
	 * 购物车列表 http://localhost:8080/estore/cart/list.action
	 * 
	 * @return
	 */
	@Action(value="list", results={
			@Result(name="success", location="/WEB-INF/cart/index.jsp")
	})
	public String list() {
		log.info("访问购物车列表");
		// 获取用户信息
		User user = (User) request.getSession().getAttribute(UserAction.SESSION_LOGIN_USER_KEY);
		
		int page = CommonUtil.getIntValue(p, 1);
		try {
			// 根据用户编号查找购物车的商品分页信息
			PageBean<CartItem> pb = cartService.getMyCart(user.getId(), page);
			request.setAttribute("pb", pb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
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
			item = cartService.addQuantityByItemid(id);
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

	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
