package com.wxhledu.cn.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.wxhledu.cn.dao.BookDao;
import com.wxhledu.cn.dao.impl.BookDaoImpl;
import com.wxhledu.cn.domain.Book;
import com.wxhledu.cn.domain.PageBean;
import com.wxhledu.cn.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookServiceImpl implements BookService {

	/**
	 * 单个页面中记录的条数
	 */
	private static final int LIMIT = 14;

	private BookDao dao = new BookDaoImpl();

	@Override
	public PageBean<Book> getBooks(int categoryid, int page) throws SQLException {
		log.info("目录编号：{}, 当前页：{}", categoryid, page);
		PageBean<Book> bean = new PageBean<>();
		int start = (page - 1) * LIMIT; // 书籍分页查询的开始索引
		
		// 查询的单个页面信息
		List<Book> list = dao.findBooksByCategory(categoryid, start, LIMIT);
		bean.setList(list);
		// 查询的总记录数
		int count = dao.findTotalBooksByCategory(categoryid);
		bean.setTotalRecord(count);
		bean.setCurrentPage(page);
		bean.setPageSize(LIMIT);
		
		return bean;
	}

}
