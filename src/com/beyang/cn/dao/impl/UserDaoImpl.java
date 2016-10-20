package com.beyang.cn.dao.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.beyang.cn.dao.UserDao;
import com.beyang.cn.domain.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User findUser(User user) throws SQLException {
		String sql = "SELECT *, register_time registerTime FROM user_t WHERE username=? AND password=MD5(?)";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, user.getUsername(), user.getPassword());
	}

}
