package com.beyang.cn.dao;

import java.sql.SQLException;

public interface OrderDao {

	/**
	* 创建订单
	 * @param userid 用户编号
	 * @param itemids 商品编号  多个商品之间用逗号分隔
	 * @param orderNum 订单单号
	 * @throws SQLException 
	 */
	void createOrder(int userid, String itemids, String orderNum) throws SQLException;

}
