package com.wensimin.dynamic.ip.service;

@FunctionalInterface
public interface RunIpChangeTask {
	void run(String ip)throws Exception;
}
