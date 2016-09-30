package com.wxhledu.cn.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.wxhledu.cn.dao.impl.OrderDaoImpl;
import com.wxhledu.cn.util.DataSourceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDaoTest {
	
	private OrderDao dao = new OrderDaoImpl();
	
	/**
	 * 金钱的处理
	 * @throws SQLException 
	 */
	@Test
	public void testBigDecimal2Double() throws SQLException{
		Object object = new QueryRunner(DataSourceUtil.getDataSource()).query("select price*5 FROM book_t WHERE id=48689", new ScalarHandler<>(1));
		Double r = (Double)object;
		double value = new BigDecimal(r).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		log.info("{}",value);
	}
	
	/**
	 * 插入数据让它返回值
	 * @throws SQLException 
	 */
	@Test
	public void testInsertFeedback() throws SQLException{
		QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "INSERT INTO order_t (order_num, created_time, price) VALUES (?, NOW(), ?)";
		ResultSetHandler<Long> rsh = new ScalarHandler<>(1);
		Long insert = runner.insert(sql, rsh , System.currentTimeMillis()+"", 2.99);
		log.info("{}", insert);
	}

	@Test
	public void testCreateOrder() throws SQLException {
		dao.createOrder(1, "18,19,20", System.currentTimeMillis()+"");
	}

	@Test
	public void testArray(){
		String[] arr = StringUtils.split("18,19,20", ',');
		System.out.println(arr);
	}
}
