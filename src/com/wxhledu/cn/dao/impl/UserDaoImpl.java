package com.wxhledu.cn.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.wxhledu.cn.dao.UserDao;
import com.wxhledu.cn.domain.User;
import com.wxhledu.cn.util.DataSourceUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public User findUser(User user) throws SQLException {
		String sql = "SELECT *, register_time registerTime FROM user_t WHERE username=? AND password=MD5(?)";
		ResultSetHandler<User> rsh = new BeanHandler<>(User.class);
		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql , rsh, user.getUsername(), user.getPassword());
	}

}
