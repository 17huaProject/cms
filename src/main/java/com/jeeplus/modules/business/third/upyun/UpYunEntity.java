package com.jeeplus.modules.business.third.upyun;

public class UpYunEntity {
	private String bucket;
	private String operator;
	private String policy;
	private String signature;
	private String notifyUrl;
	private String expiration;

	public UpYunEntity() {
		super();
	}

	public UpYunEntity(String bucket, String operator, String policy, String signature , String notifyUrl , String expiration) {
		super();
		this.bucket = bucket;
		this.operator = operator;
		this.policy = policy;
		this.signature = signature;
		this.notifyUrl = notifyUrl;
		this.expiration = expiration;
	}
	

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	

}
