package com.wxhledu.cn.util;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import com.wxhledu.cn.domain.Admin;

import lombok.extern.slf4j.Slf4j;

/**
 * DataSourceUtil 测试
 * @author wxhl
 *
 */
@Slf4j
public class DataSourceUtilTest {

	@Test
	public void testGetDataSource() throws SQLException {
		Admin admin = new QueryRunner(DataSourceUtil.getDataSource()).query("SELECT * FROM admin_user_t WHERE username=?", new BeanHandler<Admin>(Admin.class), "admin");
		log.info("{}", admin);
	}

}
