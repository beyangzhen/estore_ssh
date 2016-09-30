package com.wxhledu.cn.util;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库连接池
 * 
 * @author wxhl
 * 
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 生成私有的无参构造器
public class DataSourceUtil {

	@Getter // 生成getter方法
	private static DataSource dataSource;

	static {
		Properties properties = new Properties();
		
		try {
			// 加载属性对象
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
			// 初始化连接池配置
			HikariConfig config = new HikariConfig(properties); 
			// 初始化DataSource
			dataSource = new HikariDataSource(config);
		} catch (IOException e) {
			//e.printStackTrace();
			log.error("数据连接池加载异常：{}", e.getMessage());
		}
	}

}
