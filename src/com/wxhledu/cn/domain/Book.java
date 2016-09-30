package com.wxhledu.cn.domain;

import lombok.Data;


@Data
public class Book {
	private int id;
	
	/**
	 * 书名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 原价
	 */
	private double oldPrice;
	/**
	 * 现格
	 */
	private double price;
	/**
	 * 书的图片路径
	 */
	private String filename;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 书的数量
	 */
	private int amount;
	/**
	 * 书的星级
	 */
	private int star;
}