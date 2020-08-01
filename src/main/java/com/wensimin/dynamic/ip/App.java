package com.wensimin.dynamic.ip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wensimin.dynamic.ip.entity.DomainRecord;
import com.wensimin.dynamic.ip.service.AliService;
import com.wensimin.dynamic.ip.service.CheckIpService;
import com.wensimin.dynamic.ip.utils.ResourceUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Logger log = LogManager.getLogger(App.class);
		Integer time = 60 * 1000 * Integer.parseInt(ResourceUtils.getString("time"));
		CheckIpService ipService = CheckIpService.getService();
		AliService aliService = AliService.getService();
		while (true) {
			try {
				ipService.checkIpAndRun(ip -> {
					log.debug("start change, ip:" + ip);
					for (DomainRecord record : aliService.getAllRecord()) {
						if (!record.getValue().equals(ip)) {
							record.setValue(ip);
							aliService.updateRecord(record);
						}
					}
				});
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("failed!", e);
			}
		}
	}
}
