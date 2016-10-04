package com.wxhledu.cn.web;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wxhledu.cn.domain.Book;
import com.wxhledu.cn.domain.Category;
import com.wxhledu.cn.domain.PageBean;
import com.wxhledu.cn.service.BookService;
import com.wxhledu.cn.service.impl.BookServiceImpl;
import com.wxhledu.cn.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookCtrl {

	private BookService service = new BookServiceImpl();

	/**
	 *	显示分类图书 BookCtrl?method=list&cid= 
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public String list(HttpServletRequest request, HttpServletResponse response) {
		
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
		
		
		String parentCategoryId = request.getParameter("parentid"); // 获取父目录的编号，string类型
		String categoryId = request.getParameter("id"); // 子目录编号（查询子目录时，传的参数）
		String page = request.getParameter("p"); // 第N页
		
		// 未获取到值时（设为默认值） ， 获取到值时（转为整型）
		int id = CommonUtil.getIntValue(parentCategoryId, 0);
		int _cid = CommonUtil.getIntValue(categoryId, 0);
		int p = CommonUtil.getIntValue(page, 1);
		log.info("查找父目录编号：{}", id);
		
		// 获取所有父目录
		@SuppressWarnings("unchecked")
		List<Category> categories = (List<Category>) request.getServletContext().getAttribute("categories");
		if (id == 0) {
			request.setAttribute("cats", categories);
		} else {
			for (Category category : categories) {
				// 获取为页面参数id的父目录
				if (category.getId() == id) {
					List<Category> children = category.getChildren();
					request.setAttribute("cats", children);
				}
			}
		}
		
		try {
			// 获取单个页面的信息（分页）
			PageBean<Book> pb = service.getBooks(_cid, p);
			request.setAttribute("pb", pb);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "f:/book/index.jsp";
	}

}
