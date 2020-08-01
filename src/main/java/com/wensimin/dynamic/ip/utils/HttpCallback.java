package com.wensimin.dynamic.ip.utils;

@FunctionalInterface
public interface HttpCallback {
	void run(String rs) throws Exception;
}
