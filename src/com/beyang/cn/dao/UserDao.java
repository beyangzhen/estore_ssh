package com.beyang.cn.dao;

import java.sql.SQLException;

import com.beyang.cn.domain.User;

public interface UserDao {

	User findUser(User user) throws SQLException;

}
