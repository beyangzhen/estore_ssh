package com.beyang.cn.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beyang.cn.dao.BookDao;
import com.beyang.cn.domain.Book;
import com.beyang.cn.domain.PageBean;
import com.beyang.cn.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Service("bookService")
@Slf4j
public class BookServiceImpl implements BookService {

	/**
	 * 单个页面中记录的条数
	 */
	private static final int LIMIT = 14;

	@Autowired
	private BookDao bookDao;

	@Override
	public PageBean<Book> getBooks(int categoryid, int page) throws SQLException {
		log.info("目录编号：{}, 当前页：{}", categoryid, page);
		PageBean<Book> bean = new PageBean<>();
		int start = (page - 1) * LIMIT; // 书籍分页查询的开始索引
		
		// 查询的单个页面信息
		List<Book> list = bookDao.findBooksByCategory(categoryid, start, LIMIT);
		bean.setList(list);
		// 查询的总记录数
		int count = bookDao.findTotalBooksByCategory(categoryid);
		bean.setTotalRecord(count);
		bean.setCurrentPage(page);
		bean.setPageSize(LIMIT);
		
		return bean;
	}

}
