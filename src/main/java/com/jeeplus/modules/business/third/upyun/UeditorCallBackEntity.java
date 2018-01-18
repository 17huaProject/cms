package com.jeeplus.modules.business.third.upyun;


public class UeditorCallBackEntity {
	private String original;
	private String url;
	private String title;
	private String state;

	public UeditorCallBackEntity(UpYunCallBackEntity upyunImage) {
		url = upyunImage.getUrl();
		state = transUpYunCodeToUeState(upyunImage.getCode(), upyunImage.getMessage());
	}

	private String transUpYunCodeToUeState(String code, String msg) {
		String defaultState = "未知错误";
		if (code == null) {
			return defaultState;
		}
		if (code.equals("200")) {
			return "SUCCESS";
		}
		return msg;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
