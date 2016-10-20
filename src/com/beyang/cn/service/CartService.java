package com.beyang.cn.service;

import java.sql.SQLException;

import com.beyang.cn.domain.CartItem;
import com.beyang.cn.domain.PageBean;

public interface CartService {

	/**
	 * 添加商品
	 * @param bookid 图书编号
	 * @param userid 用户编号
	 * @return
	 * @throws SQLException
	 */
	int add(int bookid, int userid) throws SQLException;
	
	/**
	 * 删除商品
	 * @param ids 商品编号，以,分隔开来
	 * @throws SQLException
	 */
	void delete(String id) throws SQLException;
	
	/**
	 * 改变商品数量（代替了前面的增加/减少商品数量）
	 * @param id 商品编号
	 * @param action +|- 添加商品或删除商品
	 * @return
	 */
	CartItem modifyQuantityByItemid(String id, String action) throws SQLException;
	
	/**
	 * 分页查找我的购物车
	 * @param userid 用户编号
	 * @param page 当前页数
	 * @return
	 * @throws SQLException 
	 */
	PageBean<CartItem> getMyCart(int userid, int page) throws SQLException;
	
	
	/**
	 * 增加商品数量
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	CartItem addQuantityByItemid(String id) throws SQLException;

}
