package com.beyang.cn.service;

import java.sql.SQLException;
import java.util.List;

import com.beyang.cn.domain.Category;

public interface CategorySerivce {

	/**
	 * 查找父目录信息
	 * @return
	 * @throws SQLException 
	 */
	List<Category> findParentCategory() throws SQLException;
}
