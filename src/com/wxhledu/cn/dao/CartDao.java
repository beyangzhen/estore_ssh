package com.wxhledu.cn.dao;

import java.sql.SQLException;
import java.util.List;

import com.wxhledu.cn.domain.CartItem;

public interface CartDao {
	
	/**
	 * 添加商品到购物车里
	 * @param bookid 图书编号
	 * @param userid 用户编号
	 * @return
	 * @throws SQLException
	 */
	int add(int bookid, int userid) throws SQLException;
	
	/**
	 * @param ids 要删除的商品编号
	 * @throws SQLException
	 */
	void delete(String id) throws SQLException;
	
	/**
	 * 更新商品数量
	 * @param id 商品编号
	 * @return
	 * @throws SQLException 
	 */
	void addQuantityByItemid(String id) throws SQLException;
	
	/**
	 * 删除商品数量
	 * @param id 商品编号
	 * @throws SQLException
	 */
	void subQuantityByItemid(String id) throws SQLException;

	/**
	 * 我的购物车里商品的记录
	 * @param userid 用户编号
	 * @param start 开始记录行
	 * @param limit 偏移记录条数
	 * @return
	 * @throws SQLException 
	 */
	List<CartItem> getCartItems(int userid, int start, int limit) throws SQLException;
	
	/**
	 * 我的购物车里的商品总数
	 * @param userid 用户编号
	 * @return
	 * @throws SQLException 
	 */
	int getTotalCartItems(int userid) throws SQLException;
	
	/**
	 * 根据商品编号查找商品信息
	 * @param id 商品编号
	 * @return
	 * @throws SQLException 
	 */
	CartItem getCartItemByItemid(String id) throws SQLException;

	/**
	 * 支付单个商品
	 * @param id 商品编号
	 * @return
	 * @throws SQLException 
	 */
	void buy(String id) throws SQLException;
	
	/**
	 * 批量支付商品
	 * @param id 商品编号
	 * @return
	 * @throws SQLException 
	 */
	void buyItems(String ids) throws SQLException;
	
}
