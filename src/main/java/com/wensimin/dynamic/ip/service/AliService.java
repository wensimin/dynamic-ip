package com.wensimin.dynamic.ip.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wensimin.dynamic.ip.entity.DomainRecord;
import com.wensimin.dynamic.ip.utils.AliSignUtils;
import com.wensimin.dynamic.ip.utils.HttpMethod;
import com.wensimin.dynamic.ip.utils.HttpUtils;
import com.wensimin.dynamic.ip.utils.RandomUtils;
import com.wensimin.dynamic.ip.utils.ResourceUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AliService {
	private static AliService service;
	private String domain;
	private Logger log = LogManager.getLogger(AliService.class);
	private AliService() {
		domain = ResourceUtils.getString("domain");
	}

	public static AliService getService() {
		if (service == null) {
			service = new AliService();
		}
		return service;
	}

	public List<DomainRecord> getAllRecord() throws Exception {
		SortedMap<String, String> param = new TreeMap<>();
		// action param
		param.put("Action", "DescribeDomainRecords");
		param.put("DomainName", domain);
		initPublicParam(param);
		String url = "http://alidns.aliyuncs.com?" + AliSignUtils.map2QueryString(param);
		List<DomainRecord> records = new ArrayList<>();
		HttpUtils.exec(HttpMethod.GET, url, rs -> {
			JSONObject jsonObject = JSONObject.fromObject(rs);
			JSONArray allRecord = jsonObject.getJSONObject("DomainRecords").getJSONArray("Record");
			for (Object jsonRecord : allRecord) {
				JSONObject jo = (JSONObject) jsonRecord;
				// 返回的json为key首字母大写，无法使用toBean
				// Object o = JSONObject.toBean(jo, DomainRecord.class);
				DomainRecord record = new DomainRecord();
				record.setDomainName(jo.getString("DomainName"));
				record.setLine(jo.getString("Line"));
				record.setLocked(jo.getBoolean("Locked"));
				record.setRR(jo.getString("RR"));
				record.setTTL(jo.getString("TTL"));
				record.setStatus(jo.getString("Status"));
				record.setType(jo.getString("Type"));
				record.setValue(jo.getString("Value"));
				record.setRecordId(jo.getString("RecordId"));
				records.add(record);
			}
		}, log::debug);
		return records;
	}

	public void updateRecord(DomainRecord record) throws Exception {
		SortedMap<String, String> param = new TreeMap<>();
		param.put("Action", "UpdateDomainRecord");
		param.put("RecordId", record.getRecordId());
		param.put("RR", record.getRR());
		param.put("Type", record.getType());
		param.put("Value", record.getValue());
		param.put("TTL", record.getTTL());
		param.put("Line", record.getLine());
		initPublicParam(param);
		String url = "http://alidns.aliyuncs.com?" + AliSignUtils.map2QueryString(param);
		HttpUtils.exec(HttpMethod.GET, url, log::debug, log::error);
	}

	/**
	 * 本方法需要在非公共参数都已经设定之后调用
	 * 
	 * @param param param
	 * @throws Exception exception
	 */
	private void initPublicParam(SortedMap<String, String> param) throws Exception {
		// public param
		param.put("Format", "JSON");
		param.put("Version", "2015-01-09");
		param.put("AccessKeyId", AliSignUtils.getId());
		param.put("SignatureMethod", "HMAC-SHA1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		param.put("Timestamp", sdf.format(new Date()));
		param.put("SignatureVersion", "1.0");
		param.put("SignatureNonce", RandomUtils.generateString(12));
		// sign param
		param.put("Signature", AliSignUtils.getSign("GET", param));
	}

}
