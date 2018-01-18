package com.jeeplus.modules.business.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.BusinessDataEntity;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.common.utils.CommonConstants;

@MyBatisDao
public class Event  extends BusinessDataEntity<Event>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String eventName;

    private String type;

    private Date eventTime;

    private Date closingTime;

    private String cityCode;

    private Integer artId;

    private Integer artistId;

    private Integer venueId;

    private Short capacity;

    private Short sold;

    private Integer viewNum;

    private BigDecimal price;

    private String assistantId;

    private String remark;

    private Byte isRefund;

    private Byte isDelete;
    
    private String eventStatus ;
    
    private String shareDesc ;  //活动分享描述语

    private BigDecimal venueFeeRate;	//1=固定金额 ， <1 的数是佣金费率，X总金额
    
    private BigDecimal venueFee;		//本次活动商家佣金
    
    /**
	 * 逻辑删除前执行的方法，需要手动调用
	 */
	public void preDeleteLogic(){
		preUpdate();
		this.setIsDelete(CommonConstants.DELETED_Y);
	}
    
    public BigDecimal getVenueFeeRate() {
		return venueFeeRate;
	}

	public void setVenueFeeRate(BigDecimal venueFeeRate) {
		this.venueFeeRate = venueFeeRate;
	}

	public BigDecimal getVenueFee() {
		return venueFee;
	}

	public void setVenueFee(BigDecimal venueFee) {
		this.venueFee = venueFee;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public Short getSold() {
        return sold;
    }

    public void setSold(Short sold) {
        this.sold = sold;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Byte isRefund) {
        this.isRefund = isRefund;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

    
}