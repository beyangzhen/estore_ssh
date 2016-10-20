package com.beyang.cn.dao;

import java.sql.SQLException;
import java.util.List;

import com.beyang.cn.domain.Category;

public interface CategoryDao {

	/**
	 * 查找所有的分类信息
	 * @return
	 * @throws SQLException 
	 */
	List<Category> findAll() throws SQLException;
}
