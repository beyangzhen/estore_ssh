package com.wxhledu.cn.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wxhledu.cn.dao.impl.CategoryDaoImpl;
import com.wxhledu.cn.domain.Category;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryDaoTest {
	
	private CategoryDao dao = new CategoryDaoImpl();

	@Test
	public void testFindAll() throws SQLException {
		List<Category> list = dao.findAll();
		for (Category category : list) {
			log.info("分类：{}", category);
		}
		Assert.assertNotNull(list);
	}

}
