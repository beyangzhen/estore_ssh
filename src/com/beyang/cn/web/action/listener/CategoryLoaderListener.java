package com.beyang.cn.web.action.listener;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beyang.cn.domain.Category;
import com.beyang.cn.service.CategorySerivce;

import lombok.extern.slf4j.Slf4j;

/**
 *  监听应用启动时，加载父目录信息
 *
 */
@Slf4j
public class CategoryLoaderListener implements ServletContextListener {

    public CategoryLoaderListener() {
    }

    public void contextDestroyed(ServletContextEvent event)  { 
    }

    public void contextInitialized(ServletContextEvent event)  { 
         // 获取application域
    	ServletContext context = event.getServletContext();
    	
    	List<Category> categories;
    	// 获取spring容器中的bean对象
    	ApplicationContext act = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    	CategorySerivce categorySerivce = (CategorySerivce) act.getBean("categorySerivce");
    	
		try {
			// 获取目录信息
			categories = categorySerivce.findParentCategory();
			log.info("找到分类信息{}条记录", categories.size());
			// 将目录信息添加到application域中
			context.setAttribute("categories", categories);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
	
}
