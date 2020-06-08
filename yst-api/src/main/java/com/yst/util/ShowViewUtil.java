package com.yst.util;

public class ShowViewUtil {
	
	public static String errorHtml(){
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
//		builder.append("alert(\"页面过期，请重新登录\");");
		builder.append("window.top.location.href=\"");
		builder.append("https://www.baidu.com\";</script>");
		return builder.toString();
	}
}
