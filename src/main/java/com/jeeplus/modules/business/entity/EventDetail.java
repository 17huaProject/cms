package com.jeeplus.modules.business.entity;

import java.io.Serializable;

import com.jeeplus.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public class EventDetail implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer eventId;

    private String eventDesc;

    private String keywords;

    private String content;

    private String freeService;

    
    
    public String getFreeService() {
		return freeService;
	}

	public void setFreeService(String freeService) {
		this.freeService = freeService;
	}

	public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}