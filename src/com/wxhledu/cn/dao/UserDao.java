package com.wxhledu.cn.dao;

import java.sql.SQLException;

import com.wxhledu.cn.domain.User;

public interface UserDao {

	User findUser(User user) throws SQLException;

}
