package com.wxhledu.cn.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wxhledu.cn.domain.Category;
import com.wxhledu.cn.service.impl.CategoryServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategorySerivceTest {
	
	private CategorySerivce service = new CategoryServiceImpl();

	@Test
	public void testFindCategory() throws SQLException {
		List<Category> categorys = service.findParentCategory();
		for (Category category : categorys) {
			log.info("分类信息：{}", category);
		}
		
		Assert.assertNotNull(categorys);
	}

}
