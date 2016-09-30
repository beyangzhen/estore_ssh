package com.wxhledu.cn.util;

public class CommonUtil {
	
	/**
	 * 字符串转为整形（获取的value为空时，设为默认值）
	 * 			                                不为空，转为整型
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int getIntValue(String value, int defaultValue) {
		int v = defaultValue;
		
		try {
			v = Integer.parseInt(value);
		} catch (Exception e) {
			v = defaultValue;
		}
		
		return v;
	}
}
