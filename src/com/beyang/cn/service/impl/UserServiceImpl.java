package com.beyang.cn.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beyang.cn.dao.UserDao;
import com.beyang.cn.domain.User;
import com.beyang.cn.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User findUser(User user) throws SQLException {
		return userDao.findUser(user);
	}

}
