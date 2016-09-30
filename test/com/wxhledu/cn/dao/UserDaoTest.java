package com.wxhledu.cn.dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.wxhledu.cn.dao.impl.UserDaoImpl;
import com.wxhledu.cn.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDaoTest {
	
	private UserDao dao = new UserDaoImpl();

	@Test
	public void testFindUser() throws SQLException {
		User user = new User("haizong", "123456");
		User findUser = dao.findUser(user);
		log.info("查找的用户信息：{}", findUser);
		Assert.assertNotNull(findUser);
	}

}
