package com.wxhledu.cn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.wxhledu.cn.dao.BookDao;
import com.wxhledu.cn.domain.Book;
import com.wxhledu.cn.util.DataSourceUtil;

public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> findBooksByCategory(int categoryid, int start, int limit) throws SQLException {
		String sql = "SELECT t.*, t.old_price oldPrice from book_category_rt r LEFT JOIN book_t t on r.bid=t.id WHERE cid=? AND status=1 ORDER BY t.id LIMIT ?, ?";
		ResultSetHandler<List<Book>> rsh = new BeanListHandler<>(Book.class);
		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh, categoryid, start, limit);
	}

	@Override
	public int findTotalBooksByCategory(int categoryid) throws SQLException {
		String sql = "SELECT count(1) from book_category_rt r LEFT JOIN book_t t on r.bid=t.id WHERE cid=? AND status=1";
		ResultSetHandler<Long> rsh = new ScalarHandler<>();
		Long query = new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh, categoryid);
		return query.intValue();
	}

}
