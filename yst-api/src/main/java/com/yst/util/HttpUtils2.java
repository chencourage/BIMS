package com.yst.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 * HttpUtils请求
 * 
 * @author yuqin.zhou
 *
 */
public class HttpUtils2 {

	private static final Logger logger = Logger.getLogger(HttpUtils2.class);

	private static final int DEFAULT_TIME_OUT = 30 * 1000;

	public static String executeHttpPost(String url, Map<String, String> params, String... encoded) {
		String result = null;
		try {
			result = doPost(url, params, encoded);
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	/**
	 * 获取httpURL连接
	 * 
	 * @param urlAdd
	 * @return
	 * @throws IOException
	 */
	private static String doPost(String url, Map<String, String> paramMap, String... encoded) throws IOException {
		String result = "";
		CloseableHttpResponse response = null;
		SSLClient httpclient = null;
		try {
			httpclient = new SSLClient();
			Builder builder = RequestConfig.custom();
			builder.setSocketTimeout(DEFAULT_TIME_OUT);// 设置请求超时时间
			builder.setConnectTimeout(DEFAULT_TIME_OUT);// 设置传输超时时间
			RequestConfig requestConfig = builder.build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			List<NameValuePair> params = generateParam(paramMap);
			if (params.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				httpPost.setEntity(entity);
			}
			response = httpclient.execute(httpPost);
			if (null != response) {
				result = readerResult(response.getEntity().getContent(), encoded);
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			closeHttpClient(httpclient, response);

		}
		return result;
	}

	/***
	 * 生成请求参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private static List<NameValuePair> generateParam(Map<String, String> paramMap) {
		if (null == paramMap || paramMap.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		BasicNameValuePair pair = null;
		for (Entry<String, String> entry : paramMap.entrySet()) {
			pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
			paramList.add(pair);
		}
		return paramList;
	}

	/**
	 * 读服务器数据
	 * 
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private static String readerResult(InputStream in, String... encoded) {
		String result = null;
		BufferedReader reader = null;
		try {
			String readEncoded = "utf-8";
			if (null != encoded && encoded.length > 0) {
				readEncoded = encoded[0];
			}
			reader = new BufferedReader(new InputStreamReader(in, readEncoded));
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			closeBufferReader(reader);
		}
		return result;
	}

	/***
	 * 关闭httpclient
	 * 
	 * @param httpclient
	 */
	private static void closeHttpClient(CloseableHttpClient httpclient, CloseableHttpResponse response) {
		try {
			if (response != null) {
				response.close();
			}

			if (httpclient != null) {
				httpclient.close();
			}
		} catch (Exception e) {

		}
	}

	/***
	 * 关闭IO流
	 * 
	 * @param reader
	 */
	private static void closeBufferReader(BufferedReader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void main(String[] args) {
		String url = "http://10.211.149.210:8075/yspay-web/mealRefund";
		Map<String, String> map = new HashMap<String, String>();
		String id = "YST0B151610281634202300000553944";
		map.put("ids", "[\"" + id + "\"]");
		map.put("verifier", "amdin");
		map.put("opinion", "通过");
		String result = executeHttpPost(url, map);

		logger.info("|---------------订餐退款审核返回结果：" + result);

	}
}
