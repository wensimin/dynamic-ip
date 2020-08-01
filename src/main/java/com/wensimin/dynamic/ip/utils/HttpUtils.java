package com.wensimin.dynamic.ip.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static final int HTTP_OK = 200;
	private static int CONNECTION_TIMEOUT_MS = Integer.parseInt(ResourceUtils.getString("time.out")) * 1000; // Timeout
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS).setConnectTimeout(CONNECTION_TIMEOUT_MS)
			.setSocketTimeout(CONNECTION_TIMEOUT_MS).build();

	public static void exec(HttpMethod method, String url, HttpCallback success, HttpCallback error)
			throws Exception {
		HttpClient hc = HttpClients.createDefault();
		HttpRequestBase request;
		switch (method) {
		case GET:
			request = new HttpGet(url);
			break;
		case POST:
			request = new HttpPost(url);
			break;
		default:
			throw new NullPointerException();
		}
		request.setConfig(requestConfig);
		HttpResponse response = hc.execute(request);
		if (response.getStatusLine().getStatusCode() == HTTP_OK) {
			success.run(EntityUtils.toString(response.getEntity(), AliSignUtils.ENCODE));
		} else {
			error.run(EntityUtils.toString(response.getEntity(), AliSignUtils.ENCODE));
		}
	}
}
