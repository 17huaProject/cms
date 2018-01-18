package com.jeeplus.modules.business.dto;

public class MaterialDeliveryBaseInfoDTO {
	private Integer eventId;
    private String eventName;
    private Short capacity;
    private String artName;
	private Integer materialModelId;
    private String materialModelName;
    private String materialModelGoodsInfo;
    private String materialJson ;
    
    
	public String getMaterialJson() {
		return materialJson;
	}
	public void setMaterialJson(String materialJson) {
		this.materialJson = materialJson;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Short getCapacity() {
		return capacity;
	}
	public void setCapacity(Short capacity) {
		this.capacity = capacity;
	}
	public String getArtName() {
		return artName;
	}
	public void setArtName(String artName) {
		this.artName = artName;
	}
	public Integer getMaterialModelId() {
		return materialModelId;
	}
	public void setMaterialModelId(Integer materialModelId) {
		this.materialModelId = materialModelId;
	}
	public String getMaterialModelName() {
		return materialModelName;
	}
	public void setMaterialModelName(String materialModelName) {
		this.materialModelName = materialModelName;
	}
	public String getMaterialModelGoodsInfo() {
		return materialModelGoodsInfo;
	}
	public void setMaterialModelGoodsInfo(String materialModelGoodsInfo) {
		this.materialModelGoodsInfo = materialModelGoodsInfo;
	}	
	
    
    
	
}
