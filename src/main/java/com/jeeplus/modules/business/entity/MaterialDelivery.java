package com.jeeplus.modules.business.entity;

import com.jeeplus.common.persistence.BusinessDataEntity;

public class MaterialDelivery  extends BusinessDataEntity<MaterialDelivery> {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer eventId;

    private Integer goodId;

    private Short outCount;

    private Short inCount;

    private Byte deleted;
    
    private String eventName ;
    private Integer modelId ; 

    
    
	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
    

    public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Short getOutCount() {
        return outCount;
    }

    public void setOutCount(Short outCount) {
        this.outCount = outCount;
    }

    public Short getInCount() {
        return inCount;
    }

    public void setInCount(Short inCount) {
        this.inCount = inCount;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

}