package com.wensimin.dynamic.ip.service;

import com.wensimin.dynamic.ip.utils.HttpMethod;
import com.wensimin.dynamic.ip.utils.HttpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckIpService {
	private Logger log = LogManager.getLogger(CheckIpService.class);
	private String url;
	private String ip;
	private static CheckIpService service;

	private CheckIpService() {
		url = "https://checkip.amazonaws.com/";
		ip = "";
	}

	public static CheckIpService getService() {
		if (service == null) {
			service = new CheckIpService();
		}
		return service;
	}

	public void checkIpAndRun(RunIpChangeTask task) throws Exception {
		log.debug("checking ip");
		HttpUtils.exec(HttpMethod.GET, url, newIp -> {
			newIp = newIp.trim();
			log.debug("ip:"+newIp);
			if (!ip.equals(newIp)) {
				ip = newIp;
				task.run(ip);
			}
		}, log::error);
	}
}
