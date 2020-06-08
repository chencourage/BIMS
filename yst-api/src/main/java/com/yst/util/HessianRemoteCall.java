package com.yst.util;

import org.apache.commons.lang.StringUtils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yspay.framework.core.exception.YSTException;

/***
 * 调用公司平台发布的Hessian接口，remoteType为myhessian接口
 * 
 * @author tanqin.huang
 *
 */
public class HessianRemoteCall {

	private static final long TIME_OUT = 30 * 1000L;

	public static Object getRemoteObject(String url, Class<?> clazz,
			Object... objects) throws YSTException {
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setReadTimeout(getTimeOut(objects));
		if (StringUtils.isEmpty(url))
			throw new YSTException("远程Object接口[" + clazz + "]的url地址是空");
		Object object = null;
		try {
			object = factory.create(clazz, url);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new YSTException("Hessian寻找对象失败" + t.getMessage());
		}
		return object;
	}

	private static long getTimeOut(Object[] objects) {
		if (null == objects) {
			return TIME_OUT;
		}
		if (objects.length == 0 || null == objects[0]) {
			return TIME_OUT;
		}
		return parseLong(objects[0].toString(), TIME_OUT);

	}

	private static long parseLong(String obj, long defaultValue) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
