package com.yst.web;

import java.security.Key;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yst.util.HttpUtil;
import com.yst.util.KeyUtil;

public class SystemInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemInterceptor.class);

	private List<String> excludedMethods;

	public void setExcludedMethods(List<String> excludedMethods) {
		this.excludedMethods = excludedMethods;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HttpUtil.setRespose(response);
		// 后台session控制
		String methodName = request.getParameter("method");
		//Validate.notEmpty(methodName, "method参数为空");
		boolean beFilter = true;
		for (String name : excludedMethods) {
			/*if (methodName.startsWith(name)) {
				beFilter = false;
				break;
			}*/
		}
		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				System.err.println(cookie[i].getName() + "------"
						+ cookie[i].getValue());
			}
		}
		// 获取本地的私钥
		Key key = KeyUtil.getKey(request.getServletContext());
		String authToken = request.getParameter("authToken");
		Enumeration<String> e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String paramName = (String) e.nextElement();
			System.out.println(paramName);
		}
		System.out.println(authToken);
		/*if (beFilter) {
			JSONObject result = new JSONObject();
			if (StringUtils.isEmpty(authToken)) {
				LOGGER.error("请求【" + methodName + "】authToken 信息为空不能校验通过");
				result.put("errorCode", "9999");
				result.put("errorDesc", "authToken 信息不能为空");
				response.getWriter().write(result.toJSONString());
				return false;
			}
		}*/
		return super.preHandle(request, response, handler);
	}

}
