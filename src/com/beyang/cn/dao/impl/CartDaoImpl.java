package com.beyang.cn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.beyang.cn.dao.CartDao;
import com.beyang.cn.domain.CartItem;

import lombok.extern.slf4j.Slf4j;

@Repository("cartDao")
@Slf4j
public class CartDaoImpl implements CartDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int add(int bookid, int userid) throws SQLException {
		String updateSql = "UPDATE item_t set amount=amount+1, price=price+(select b.price from book_t b where b.id=?) where user_id=? and book_id=?";
		String insertSql = "INSERT INTO item_t (price, amount, user_id, book_id) select price, 1 amount, ? user_id, ? book_id from book_t b where b.id=?";
		
		int update = jdbcTemplate.update(updateSql, bookid, userid, bookid);
		if (update <= 0) {
			log.info("添加商品记录");
			return jdbcTemplate.update(insertSql, userid, bookid, bookid);
		} else {
			return update;
		}
	}
	
	@Override
	public void delete(String id) throws SQLException {
		String sql = "DELETE FROM item_t where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	@Override
	public void addQuantityByItemid(String id) throws SQLException {
		jdbcTemplate.update("UPDATE item_t SET amount = amount + 1, price=amount*(SELECT price FROM book_t WHERE id=book_id) WHERE id =?", id);
	}
	
	@Override
	public void subQuantityByItemid(String id) throws SQLException {
		jdbcTemplate.update("UPDATE item_t SET amount = amount - 1, price=amount*(SELECT price FROM book_t WHERE id=book_id) WHERE id =? AND amount > 1", id);
	}

	@Override
	public List<CartItem> getCartItems(int userid, int start, int limit) throws SQLException {
		String sql = "SELECT i.id as itemid, i.user_id as userid, i.amount as quantity, i.id, b.name, b.description, b.old_price as oldPrice, b.price, b.filename, b.author, IFNULL(b.amount,0) AS amount, b.star FROM item_t i LEFT JOIN book_t b ON i.book_id=b.id WHERE i.user_id=? AND i.order_id IS NULL AND b.`STATUS`=1 ORDER BY i.id LIMIT ?, ?";
		RowMapper<CartItem> rowMapper=new BeanPropertyRowMapper<>(CartItem.class);
		return jdbcTemplate.query(sql, rowMapper,userid, start, limit);
	}
	
	@Override
	public int getTotalCartItems(int userid) throws SQLException {
		String sql = "SELECT count(1) FROM item_t i LEFT JOIN book_t b ON i.book_id=b.id WHERE user_id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userid);
	}
	
	@Override
	public CartItem getCartItemByItemid(String id) throws SQLException {
		// 1. beanUtil中表关联类时，支持查询所有后，再添加额外属性
//		String sql = "SELECT *, i.amount quantity, i.id itemid, i.user_id userid, b.old_price oldPrice FROM item_t i LEFT JOIN book_t b ON i.book_id = b.id WHERE i.id=?";
		// 2. spring的jdbcTemplate中表关联类时，不支持查询所有后，再添加额外属性
		String sql = "SELECT i.id itemid, i.price, i.amount quantity, i.user_id userid, i.book_id bookid, b.old_price oldPrice FROM item_t i LEFT JOIN book_t b ON i.book_id = b.id WHERE i.id=?";
		RowMapper<CartItem> rowMapper = new BeanPropertyRowMapper<>(CartItem.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}
	
	@Override
	public void buy(String id) throws SQLException {
	}
	
	@Override
	public void buyItems(String ids) throws SQLException {
		String sql = "DELETE FROM item_t where id in ("+ids.replaceAll("\\d+", "?")+")";
		jdbcTemplate.update(sql, (Object[])StringUtils.split(ids, ','));//Object[] String[]
	}

}
