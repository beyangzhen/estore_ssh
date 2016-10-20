package com.beyang.cn.domain;

import lombok.Data;

@Data
public class Admin {

	/**
	 * 编号
	 */
	private int id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;
}
