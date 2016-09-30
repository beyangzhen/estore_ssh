package com.wxhledu.cn.service.impl;

import java.sql.SQLException;

import com.wxhledu.cn.dao.UserDao;
import com.wxhledu.cn.dao.impl.UserDaoImpl;
import com.wxhledu.cn.domain.User;
import com.wxhledu.cn.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao dao = new UserDaoImpl();

	@Override
	public User findUser(User user) throws SQLException {
		return dao.findUser(user);
	}

}
