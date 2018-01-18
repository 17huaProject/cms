package com.jeeplus.modules.business.third.upyun;

/**
 * upyun返回的消息
 * @author afanti
 *
 */
public class UpYunCallBackEntity {
	private String code;
	private String message;
	private String url;		//upyun图片调用地址
	private String time;
	private String sign;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
