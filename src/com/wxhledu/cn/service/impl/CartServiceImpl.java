package com.wxhledu.cn.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wxhledu.cn.dao.CartDao;
import com.wxhledu.cn.dao.impl.CartDaoImpl;
import com.wxhledu.cn.domain.CartItem;
import com.wxhledu.cn.domain.PageBean;
import com.wxhledu.cn.service.CartService;

public class CartServiceImpl implements CartService {
	
	/**
	 * 单个页面中记录的条数
	 */
	private static final int LIMIT = 8;
	
	private CartDao dao = new CartDaoImpl();

	
	@Override
	public int add(int bookid, int userid) throws SQLException {
		return dao.add(bookid, userid);
	}
	
	@Override
	public void delete(String id) throws SQLException {
		if (StringUtils.isNotBlank(id)) {
			dao.delete(id);
		}
	}
	
	// 改变商品数量（代替了前面的增加/减少商品数量）
	@Override
	public CartItem modifyQuantityByItemid(String id, String action) throws SQLException {
		if (StringUtils.equals("+", action)) {
			//商品数量加1
			dao.addQuantityByItemid(id);
		}
		if (StringUtils.equals("-", action)) {
			// TODO 商品数量减1
			dao.subQuantityByItemid(id);
		}
		// 2. 商品信息查询出来
		return dao.getCartItemByItemid(id);
	}
	
	@Override
	public PageBean<CartItem> getMyCart(int userid, int page) throws SQLException {
		// 1. 找到总记录条数
		int totalCount = dao.getTotalCartItems(userid);
		if (totalCount > 0) {
			int start = (page - 1) * LIMIT; // 开始页
			// 2. 找到购物车商品列表
			List<CartItem> list = dao.getCartItems(userid, start, LIMIT);
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
		dao.addQuantityByItemid(id);
		// 2. 商品信息查询出来
		return dao.getCartItemByItemid(id);
	}
	
}
