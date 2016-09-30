package com.wxhledu.cn.service;

import java.sql.SQLException;

public interface OrderService {

	/**
	 * 创建订单
	 * @param userid 用户编号
	 * @param itemids 商品编号  多个商品之间用逗号分隔
	 * @return
	 * @throws SQLException
	 */
	String createOrder(int userid, String itemids) throws SQLException;

}
