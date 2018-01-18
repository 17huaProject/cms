package com.jeeplus.modules.business.entity;

import java.util.Date;

import com.jeeplus.common.persistence.BusinessBaseEntity;

public class OrderDetail extends BusinessBaseEntity<OrderDetail>{
	private static final long serialVersionUID = 1L;

	private Integer id;
    
    private Integer eventId;

    private String orderId;

    private String userId;

    private String ticketCode;

    private Byte usedFlag;

    private Date usedTime;

    private String operator;

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

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Byte getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Byte usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

	@Override
	public void preInsert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preAudit() {
		// TODO Auto-generated method stub
		
	}
}