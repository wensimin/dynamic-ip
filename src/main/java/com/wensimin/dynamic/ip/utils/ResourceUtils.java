package com.wensimin.dynamic.ip.utils;

import java.util.ResourceBundle;

public class ResourceUtils {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config-dev");

	public static String getString(String key) {
		return bundle.getString(key);
	}
}
