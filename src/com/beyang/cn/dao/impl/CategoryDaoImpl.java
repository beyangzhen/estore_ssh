package com.beyang.cn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.beyang.cn.dao.CategoryDao;
import com.beyang.cn.domain.Category;

@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Category> findAll() throws SQLException {
		String sql = "SELECT id, name, pid, IFNULL(display_order,0) as `order` FROM category_t WHERE STATUS=1";
//		ResultSetHandler<List<Category>> rsh = new BeanListHandler<>(Category.class);
//		return new QueryRunner(DataSourceUtil.getDataSource()).query(sql, rsh);
		RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
		return jdbcTemplate.query(sql, rowMapper);
	}

}
