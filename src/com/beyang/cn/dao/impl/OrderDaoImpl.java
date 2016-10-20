package com.beyang.cn.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.beyang.cn.dao.OrderDao;

/**
 * 难点: 
 * 1.处理金钱
 * 2.插入数据后返回主键 
 * @author yz
 *
 */
@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void createOrder(int userid, String itemids, String orderNum) throws SQLException {
		// 找到总金额
		String qs = itemids.replaceAll("\\d+", "?");
		String select = "SELECT SUM(price) from item_t WHERE id in(" + qs + ")";
		String[] ids = StringUtils.split(itemids, ',');
		Double price = jdbcTemplate.queryForObject(select, Double.class, (Object[]) ids);
		
		// 插入订单信息
		String insertOrder = "INSERT INTO order_t (order_num, created_time, price) VALUES (?, NOW(), ?)";
		double _price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 金钱处理 
		jdbcTemplate.update(insertOrder, orderNum, _price);
		
		// 返回主键
		Long orderKey = jdbcTemplate.queryForObject("select id from order_t where  order_num =? ", Long.class, orderNum);
		
		// 更新商品信息
		String updateImem = "UPDATE item_t SET order_id=? WHERE user_id=? AND id in (" + qs + ")";
		jdbcTemplate.update(updateImem,(Object[])StringUtils.split(orderKey+","+userid+","+itemids, ','));
	}

}
