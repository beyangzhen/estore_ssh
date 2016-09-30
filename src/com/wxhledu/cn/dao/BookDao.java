package com.wxhledu.cn.dao;

import java.sql.SQLException;
import java.util.List;

import com.wxhledu.cn.domain.Book;

public interface BookDao {

	/**
	 * 按照图书分类找图书
	 * @param categoryid 分类编号
	 * @param start 开始
	 * @param limit 共多少条数据
	 * @return
	 * @throws SQLException 
	 */
	List<Book> findBooksByCategory(int categoryid, int start, int limit) throws SQLException;
	
	
	/**
	 * 分类图书的总数
	 * @param categoryid 分类编号
	 * @return
	 * @throws SQLException 
	 */
	int findTotalBooksByCategory(int categoryid) throws SQLException;
}
