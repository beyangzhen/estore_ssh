package com.wxhledu.cn.service;

import java.sql.SQLException;

import com.wxhledu.cn.domain.User;

public interface UserService {

	/**
	 * 查询用户
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	User findUser(User user) throws SQLException;

}
