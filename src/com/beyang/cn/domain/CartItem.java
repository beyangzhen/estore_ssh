package com.beyang.cn.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车里的商品项（使用 extends Book 可以代替写  private Book book ）
 * 
 * @author yz
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false) // 必须带上，代表不使用父类的equals和hashCode
public class CartItem extends Book { // 或者写 private Book book
	
	/**
	 * 商品项的编号
	 */
	private int itemid;

	/**
	 * 用户信息 user.id
	 */
	private int userid;

	/**
	 * 图书信息 book.id
	 */
	// private Book book;

	/**
	 * 数量
	 */
	private int quantity;

	/**
	 * 价格
	 */
	private double price;
}
