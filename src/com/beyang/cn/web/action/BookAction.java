package com.beyang.cn.web.action;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.beyang.cn.domain.Book;
import com.beyang.cn.domain.Category;
import com.beyang.cn.domain.PageBean;
import com.beyang.cn.service.BookService;
import com.beyang.cn.util.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;

import lombok.extern.slf4j.Slf4j;

@Namespace("/book")
@ParentPackage("struts-default")
@AllowedMethods({"list"})
@Controller
@Scope("protoType") // 非单例（多例模式）
@Slf4j
public class BookAction extends ActionSupport {

	private static final long serialVersionUID = 8767206020910654700L;
	
	private String parentid; // 获取父目录的编号，string类型
	private String id; // 子目录编号（查询子目录时，传的参数）
	private String p; // 第N页
	
	@Autowired
	private BookService bookService;
	
	// action中获取request、response、session对象
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	/**
	 *	显示分类图书 http://localhost:8080/estore/book/list.action
	 *
	 * @return
	 */
	@Action(value="list", results={
			@Result(name="success", location="/book/index.jsp")
	})
	public String list() {
		
		// 保存当前页面的url信息（方便用户登录后可以回到此页面）
		String goUrl = request.getRequestURI();
		Enumeration<String> params = request.getParameterNames();
		if(null != params) {
			goUrl = goUrl.concat("?");
			while(params.hasMoreElements()) {
				String param = params.nextElement();
				String urlParam = param + "=" + request.getParameter(param) + "&";
				goUrl = goUrl.concat(urlParam);
			}
			goUrl = goUrl.substring(request.getContextPath().length(), goUrl.length() - 1);
		}
		// 保存(第一次访问) 或  更新(再次访问) session中goUrl的当前页面信息
		request.getSession().setAttribute("goUrl", goUrl);
		
		
		// 未获取到值时（设为默认值） ， 获取到值时（转为整型）
		int pId = CommonUtil.getIntValue(parentid, 0);
		int cId = CommonUtil.getIntValue(id, 0);
		int page = CommonUtil.getIntValue(p, 1);
		log.info("查找父目录编号：{}", pId);
		
		// 获取所有父目录
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) request.getServletContext().getAttribute("categories");
		if (pId == 0) {
			request.setAttribute("cats", categories);
		} else {
			for (Category category : categories) {
				// 获取为页面参数id的父目录
				if (category.getId() == pId) {
					List<Category> children = category.getChildren();
					request.setAttribute("cats", children);
				}
			}
		}
		
		try {
			// 获取单个页面的信息（分页）
			PageBean<Book> pb = bookService.getBooks(cId, page);
			request.setAttribute("pb", pb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}

}
