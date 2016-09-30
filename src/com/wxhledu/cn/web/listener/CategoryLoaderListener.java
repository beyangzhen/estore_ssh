package com.wxhledu.cn.web.listener;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wxhledu.cn.domain.Category;
import com.wxhledu.cn.service.CategorySerivce;
import com.wxhledu.cn.service.impl.CategoryServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 *  监听应用启动时，加载父目录信息
 *
 */
@Slf4j
public class CategoryLoaderListener implements ServletContextListener {

	private CategorySerivce serivce = new CategoryServiceImpl();
	
    public CategoryLoaderListener() {
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    }

    public void contextInitialized(ServletContextEvent event)  { 
         // 获取application域
    	ServletContext context = event.getServletContext();
    	
    	List<Category> categories;
		try {
			// 获取目录信息
			categories = serivce.findParentCategory();
			log.info("找到分类信息{}条记录", categories.size());
			// 将目录信息添加到application域中
			context.setAttribute("categories", categories);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
	
}
