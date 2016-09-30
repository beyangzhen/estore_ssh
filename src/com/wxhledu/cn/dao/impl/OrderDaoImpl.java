package com.wxhledu.cn.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.dao.OrderDao;
import com.wxhledu.cn.util.DataSourceUtil;

/**
 * 难点: 
 * 1.处理金钱
 * 2.插入数据后返回主键 
 * @author yz
 *
 */
public class OrderDaoImpl implements OrderDao {

	@Override
	public void createOrder(int userid, String itemids, String orderNum) throws SQLException {
		// 1. 开户事务
		Connection connection = DataSourceUtil.getDataSource().getConnection();
		connection.setAutoCommit(false);
		try {
			// 2.0 找到总金额
			String qs = itemids.replaceAll("\\d+", "?");
			String select = "SELECT SUM(price) from item_t WHERE id in(" + qs + ")";
			QueryRunner runner = new QueryRunner();
			ResultSetHandler<Double> rsh = new ScalarHandler<>();
			String[] ids = StringUtils.split(itemids, ',');
			Double price = (Double) runner.query(connection, select, rsh, (Object[]) ids);
			// 2. 插入订单信息
			String insertOrder = "INSERT INTO order_t (order_num, created_time, price) VALUES (?, NOW(), ?)";
			// 2.0 金钱处理 new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
			double _price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			// 2.1 返回主键
			Long orderKey = runner.insert(connection, insertOrder, new ScalarHandler<Long>(1), orderNum, _price);
			// 3. 更新商品信息 UPDATE item_t SET order_id=? WHERE user_id= AND id in (?,?,?)
			String updateImem = "UPDATE item_t SET order_id=? WHERE user_id=? AND id in (" + qs + ")";
			runner.update(connection, updateImem, (Object[])StringUtils.split(orderKey+","+userid+","+itemids, ','));
		} catch (Exception e) {
			// 4. 有异常，事务回滚
			connection.rollback();
			e.printStackTrace();
		} finally {
			// 5. 不管成败提交,关闭连接
			connection.commit();
			connection.close();
		}

	}

}
