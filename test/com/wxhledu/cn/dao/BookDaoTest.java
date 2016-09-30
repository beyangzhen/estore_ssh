package com.wxhledu.cn.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wxhledu.cn.dao.impl.BookDaoImpl;
import com.wxhledu.cn.domain.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookDaoTest {
	
	private BookDao dao = new BookDaoImpl();

	@Test
	public void testFindBooksByCategory() throws SQLException {
		List<Book> list = dao.findBooksByCategory(23, 0, 14);
		for (Book book : list) {
			log.info("{}", book);
		}
		Assert.assertNotNull(list);
	}

	@Test
	public void testFindTotalBooksByCategory() throws SQLException {
		int count = dao.findTotalBooksByCategory(23);
		log.info("总数：{}", count);
		Assert.assertEquals(count, 28);
	}

}
