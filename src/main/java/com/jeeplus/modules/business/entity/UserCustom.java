package com.jeeplus.modules.business.entity;

import java.util.Date;

import com.jeeplus.common.persistence.BusinessDataEntity;

public class UserCustom extends BusinessDataEntity<UserCustom>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userId;
    
    private String contact;

    private String phone;  			

    private String customType;

    private Date estDate;

    private Short estNum;

    private String city;

    private Date transTime;

    private String transDesc;

    private String remark;
    
    private Integer eventId ;
    
    public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public Date getEstDate() {
        return estDate;
    }

    public void setEstDate(Date estDate) {
        this.estDate = estDate;
    }

    public Short getEstNum() {
        return estNum;
    }

    public void setEstNum(Short estNum) {
        this.estNum = estNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}