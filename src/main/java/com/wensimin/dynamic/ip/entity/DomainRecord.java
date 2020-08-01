package com.wensimin.dynamic.ip.entity;

public class DomainRecord {
	private String RR;
	private String status;
	private String value;
	private String recordId;
	private String type;
	private String domainName;
	private Boolean locked;
	private String line;
	private String TTL;

	public String getRR() {
		return RR;
	}

	public void setRR(String rR) {
		RR = rR;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getTTL() {
		return TTL;
	}

	public void setTTL(String tTL) {
		TTL = tTL;
	}

}
