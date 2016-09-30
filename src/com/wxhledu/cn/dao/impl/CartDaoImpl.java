package com.wxhledu.cn.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.dao.CartDao;
import com.wxhledu.cn.domain.CartItem;
import com.wxhledu.cn.util.DataSourceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartDaoImpl implements CartDao {

	@Override
	public int add(int bookid, int userid) throws SQLException {
		String updateSql = "UPDATE item_t set amount=amount+1, price=price+(select b.price from book_t b where b.id=?) where user_id=? and book_id=?";
		String insertSql = "INSERT INTO item_t (price, amount, user_id, book_id) select price, 1 amount, ? user_id, ? book_id from book_t b where b.id=?";
		// 事务       
		Connection connection = DataSourceUtil.getDataSource().getConnection();
		// 开启事务
		connection.setAutoCommit(false);
		// xxx 1. 对于重复添加的图书来说, 数据+价格：a.找我购物车里存在此商品，b.如果存在则更新数量+价格，c.如果不存在插入记录（添加商品）
		// 2. 以用户编号+图书编号进行更新操作，如果更新到大于一条记录，那么是我里边已在此商口。如果小于等于0（里边没有此类商吕）插入数据
		QueryRunner runner = new QueryRunner();
		try {
			int update = runner.update(connection, updateSql, bookid, userid, bookid);
			if (update <= 0) {
				log.info("添加商品记录");
				return runner.update(connection, insertSql, userid, bookid, bookid);
			} else {
				return update;
			}
		} catch (Exception e) {
			// 如果有异常则事务回滚
			connection.rollback();
		} finally {
			// finally事务提交
			connection.commit();
			connection.close();
		}
		return 0;
	}
	
	@Override
	public void delete(String id) throws SQLException {
		String sql = "DELETE FROM item_t where id = ?";
		new QueryRunner(DataSourceUtil.getDataSource()).update(sql, id);
	}
	
	@Override
	public void addQuantityByItemid(String id) throws SQLException {
		new QueryRunner(DataSourceUtil.getDataSource()).update("UPDATE item_t SET amount = amount + 1, price=amount*(SELECT price FROM book_t WHERE id=book_id) WHERE id =?", id);
	}
	
	@Override
	public void subQuantityByItemid(String id) throws SQLException {
		new QueryRunner(DataSourceUtil.getDataSource()).update("UPDATE item_t SET amount = amount - 1, price=amount*(SELECT price FROM book_t WHERE id=book_id) WHERE id =? AND amount > 1", id);
	}

	@Override
	public List<CartItem> getCartItems(int userid, int start, int limit) throws SQLException {
		String sql = "SELECT *, i.amount quantity, i.id itemid, i.user_id userid, b.old_price oldPrice FROM item_t i LEFT JOIN book_t b ON i.book_id=b.id WHERE i.user_id=? AND b.`STATUS`=1 ORDER BY i.id LIMIT ?, ?";
		ResultSetHandler<List<CartItem>> rsh = new BeanListHandler<>(CartItem.class);
		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh, userid, start, limit);
	}
	
	@Override
	public int getTotalCartItems(int userid) throws SQLException {
		String sql = "SELECT count(1) FROM item_t i LEFT JOIN book_t b ON i.book_id=b.id WHERE user_id=?";
		ResultSetHandler<Long> rsh = new ScalarHandler<>();
		Long query = new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh, userid);
		return query.intValue();
	}
	
	@Override
	public CartItem getCartItemByItemid(String id) throws SQLException {
		// Ctrl+Alt+J 合并行操作
		String sql = "SELECT *, i.amount quantity, i.id itemid, i.user_id userid, b.old_price oldPrice FROM item_t i LEFT JOIN book_t b ON i.book_id = b.id WHERE i.id=?";
		ResultSetHandler<CartItem> rsh = new BeanHandler<>(CartItem.class);
		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh, id);
	}
	
	@Override
	public void buy(String id) throws SQLException {
	}
	
	@Override
	public void buyItems(String ids) throws SQLException {
		String sql = "DELETE FROM item_t where id in ("+ids.replaceAll("\\d+", "?")+")";
		new QueryRunner(DataSourceUtil.getDataSource()).update(sql, (Object[])StringUtils.split(ids, ','));//Object[] String[]
	}

}
