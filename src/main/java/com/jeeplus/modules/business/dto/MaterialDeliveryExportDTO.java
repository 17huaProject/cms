package com.jeeplus.modules.business.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

public class MaterialDeliveryExportDTO {
	private Integer eventId;
	@ExcelField(title="活动名", align=1, sort=1)
    private String eventName;
	
	@ExcelField(title="活动人数", align=1, sort=2)
    private Short capacity;
    
	@ExcelField(title="活动时间", align=1, sort=3)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date eventTime;
    
	@ExcelField(title="活动地点", align=1, sort=4)
	private String address ;

	@ExcelField(title="出库时间", align=1, sort=5)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date exportTime ;
    
	@ExcelField(title="负责人", align=1, sort=6)
    private String assistantName ;
	
	@ExcelField(title="负责人手机号", align=1, sort=7)
    private String assistantPhone ;
    
    private List<MaterialDeliveryDTO> materials ;

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

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Date getExportTime() {
		return exportTime;
	}

	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getAssistantPhone() {
		return assistantPhone;
	}

	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<MaterialDeliveryDTO> getMaterials() {
		return materials;
	}

	public void setMaterials(List<MaterialDeliveryDTO> materials) {
		this.materials = materials;
	}
    
    
    
}
