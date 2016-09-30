package com.wxhledu.cn.domain;

import java.util.List;

import lombok.Data;

@Data
public class Category {

	/**
	 * 图书编号
	 */
	private int id;

	/**
	 * 图书分类名字
	 */
	private String name;

	/**
	 * 上级图书编号
	 */
	private int pid;

	/**
	 * 显示顺序
	 */
	private int order;

	/**
	 * 子级分类
	 */
	private List<Category> children;
}
