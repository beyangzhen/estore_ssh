package com.beyang.cn.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beyang.cn.dao.CartDao;
import com.beyang.cn.domain.CartItem;
import com.beyang.cn.domain.PageBean;
import com.beyang.cn.service.CartService;

@Service("cartService")
public class CartServiceImpl implements CartService {
	
	/**
	 * 单个页面中记录的条数
	 */
	private static final int LIMIT = 8;
	
	@Autowired
	private CartDao cartDao;

	
	@Transactional
	@Override
	public int add(int bookid, int userid) throws SQLException {
		return cartDao.add(bookid, userid);
	}
	
	@Transactional
	@Override
	public void delete(String id) throws SQLException {
		if (StringUtils.isNotBlank(id)) {
			cartDao.delete(id);
		}
	}
	
	// 改变商品数量（代替了前面的增加/减少商品数量）
	@Transactional
	@Override
	public CartItem modifyQuantityByItemid(String id, String action) throws SQLException {
		if (StringUtils.equals("+", action)) {
			//商品数量加1
			cartDao.addQuantityByItemid(id);
		}
		if (StringUtils.equals("-", action)) {
			// TODO 商品数量减1
			cartDao.subQuantityByItemid(id);
		}
		// 商品信息查询出来
		return cartDao.getCartItemByItemid(id);
	}
	
	@Override
	public PageBean<CartItem> getMyCart(int userid, int page) throws SQLException {
		// 1. 找到总记录条数
		int totalCount = cartDao.getTotalCartItems(userid);
		if (totalCount > 0) {
			int start = (page - 1) * LIMIT; // 开始页
			// 2. 找到购物车商品列表
			List<CartItem> list = cartDao.getCartItems(userid, start, LIMIT);
			PageBean<CartItem> pb = new PageBean<>();
			pb.setList(list);
			pb.setTotalRecord(totalCount);
			pb.setCurrentPage(page);
			pb.setPageSize(LIMIT);
			return pb;
		}
		return null;
	}
	
	
	@Deprecated
	@Override
	public CartItem addQuantityByItemid(String id) throws SQLException {
		// 1. 商品数量加1
		cartDao.addQuantityByItemid(id);
		// 2. 商品信息查询出来
		return cartDao.getCartItemByItemid(id);
	}
	
}
