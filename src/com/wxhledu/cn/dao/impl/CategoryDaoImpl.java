package com.wxhledu.cn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wxhledu.cn.dao.CategoryDao;
import com.wxhledu.cn.domain.Category;
import com.wxhledu.cn.util.DataSourceUtil;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws SQLException {
		String sql = "SELECT id, name, pid, display_order `order` FROM category_t WHERE STATUS=1";
		ResultSetHandler<List<Category>> rsh = new BeanListHandler<>(Category.class);
		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh);
	}

}
