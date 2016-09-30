package com.wxhledu.cn.service.impl;

import java.sql.SQLException;

import com.wxhledu.cn.dao.OrderDao;
import com.wxhledu.cn.dao.impl.OrderDaoImpl;
import com.wxhledu.cn.service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao dao = new OrderDaoImpl();

	@Override
	public String createOrder(int userid, String itemids) throws SQLException {
		// 1. 创建订单单号
		// TODO maven 仓库里找一个名为idcenter  com.sohu  生成16数字long类型值 new IdCenter().getId();
		// 建议自己写个多线程程序测试查看多程序环境下不产生重复编号
		String orderNum = System.currentTimeMillis()+"";
		// 2. 操纵数据库插入记录
		dao.createOrder(userid, itemids, orderNum);
		return orderNum;
	}

}
