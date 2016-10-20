package com.beyang.cn.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beyang.cn.dao.OrderDao;
import com.beyang.cn.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;

	@Transactional
	@Override
	public String createOrder(int userid, String itemids) throws SQLException {
		// 1. 创建订单单号
		// TODO maven 仓库里找一个名为idcenter  com.sohu  生成16数字long类型值 new IdCenter().getId();
		// 建议自己写个多线程程序测试查看多程序环境下不产生重复编号
		String orderNum = System.currentTimeMillis()+"";
		// 2. 操纵数据库插入记录
		orderDao.createOrder(userid, itemids, orderNum);
		return orderNum;
	}

}
