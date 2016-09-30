package com.wxhledu.cn.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wxhledu.cn.dao.impl.CartDaoImpl;
import com.wxhledu.cn.domain.CartItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartDaoTest {

	private CartDao dao = new CartDaoImpl();

	@Test
	public void testAdd() throws SQLException {
		int add = dao.add(20, 1);
		log.info("结果：{}", add);
		Assert.assertTrue(add > 0);
	}
	
	@Test
	public void testAddQuantityByItemid() throws SQLException{
		dao.addQuantityByItemid("3");
	}

	@Test
	public void testGetTotalCartItems() throws SQLException{
		int count = dao.getTotalCartItems(1);
		log.info("结果：{}", count);
		Assert.assertTrue("没有记录条数", count>0);
	}
	
	@Test
	public void testGetCartItems() throws SQLException{
		List<CartItem> items = dao.getCartItems(1, 0, 14);
		for (CartItem item : items) {
			log.info("{}", item.getName());
		}
		Assert.assertNotNull(items);
	}
	
	@Test
	public void testGetCartItemByItemid() throws SQLException{
		CartItem item = dao.getCartItemByItemid("3");
		log.info("商品:{}", item);
		Assert.assertNotNull(item);
	}
	
	@Test
	public void testDeleteItems() throws SQLException{
		dao.delete("8,9");
	}
}
