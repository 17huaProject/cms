package com.jeeplus.modules.business.entity;

import java.math.BigDecimal;
import com.jeeplus.common.persistence.BusinessDataEntity;

public class CommissionSettlement extends BusinessDataEntity<CommissionSettlement>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer eventId;

    private Integer tollerId;
    
    private Integer tollerType;

    private BigDecimal fee;

    private BigDecimal feeRate;

    private String certImg;

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

    public Integer getTollerId() {
        return tollerId;
    }

    public void setTollerId(Integer tollerId) {
        this.tollerId = tollerId;
    }
    
    public Integer getTollerType() {
		return tollerType;
	}

	public void setTollerType(Integer tollerType) {
		this.tollerType = tollerType;
	}

	public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public String getCertImg() {
        return certImg;
    }

    public void setCertImg(String certImg) {
        this.certImg = certImg;
    }

}