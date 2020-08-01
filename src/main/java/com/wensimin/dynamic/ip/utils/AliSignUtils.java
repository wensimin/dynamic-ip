package com.wensimin.dynamic.ip.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AliSignUtils {
	static final String ENCODE = "UTF-8";
	private static final String SECRET = ResourceUtils.getString("ali.secret") + "&";
	private static final String Id = ResourceUtils.getString("ali.id");

	public static String getSign(String method, SortedMap<String, String> param) throws Exception {
		String sb = map2QueryString(param);
		String StringToSign = method + "&" + percentEncode("/") + "&" + percentEncode(sb);
		return toHmacSHA1Encrypt(StringToSign, SECRET);
	}

	public static String map2QueryString(Map<String, String> param) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> e : param.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			String s = percentEncode(key) + "=" + percentEncode(value);
			sb.append(sb.length() == 0 ? s : "&" + s);
		}
		return sb.toString();
	}

	private static String percentEncode(String s){
		return Optional.ofNullable(s).map(string -> {
			try {
				return URLEncoder.encode(string, ENCODE);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		})
				.map(string -> string.replace("+", "%20"))
				.map(string -> string.replace("*", "%2A"))
				.map(string -> string.replace("%7E", "~")).orElse(null);

	}

	private static String toHmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(encryptKey.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);
		return Base64.getEncoder().encodeToString(mac.doFinal(encryptText.getBytes()));
	}

	public static String getSecret() {
		return SECRET;
	}

	public static String getId() {
		return Id;
	}

}
