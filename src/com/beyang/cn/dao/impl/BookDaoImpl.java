package com.beyang.cn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.beyang.cn.dao.BookDao;
import com.beyang.cn.domain.Book;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Book> findBooksByCategory(int categoryid, int start, int limit) throws SQLException {
		// 1. beanUtil中表关联类时，支持查询所有后，再添加额外属性
//		String sql = "SELECT t.*, t.old_price oldPrice from book_category_rt r LEFT JOIN book_t t on r.bid=t.id WHERE cid=? AND status=1 ORDER BY t.id LIMIT ?, ?";
		// 2. spring的jdbcTemplate中表关联类时，不支持查询所有后，再添加额外属性
		String sql = "select t.id, t.name, t.description, t.old_price as oldPrice, t.price,	t.filename,	t.author, IFNULL(t.amount,0) as amount,	t.star from book_category_rt r LEFT JOIN book_t t on r.bid=t.id  WHERE cid=? AND status=1 ORDER BY t.id LIMIT ?, ?";
		RowMapper<Book> rowMapper  =new BeanPropertyRowMapper<>(Book.class);
		return jdbcTemplate.query(sql, rowMapper, categoryid, start, limit);
	}

	@Override
	public int findTotalBooksByCategory(int categoryid) throws SQLException {
		String sql = "SELECT IFNULL(count(1),0)  from book_category_rt r LEFT JOIN book_t t on r.bid=t.id WHERE cid=? AND status=1";
		return jdbcTemplate.queryForObject(sql, Integer.class, categoryid);
	}

}
