package com.wxhledu.cn.domain;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 用户模型
 * @author wxhl
 *
 */
@Data // 生成getter, setter
@NoArgsConstructor // 生成无参构造器
@RequiredArgsConstructor // 生成带参构造器
public class User {

	/**
	 * 用户编号
	 */
	private int id;
	
	/**
	 * 用户账号
	 */
	@NonNull // 可以帮我们避免空指针
	private String username;
	
	/**
	 * 用户密码
	 */
	@NonNull
	private String password;
	
	/**
	 * 用户名称
	 */
	private String name;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 注册时间
	 */
	private Date registerTime;
	
	/**
	 * 余额
	 */
	private double balance;
}
