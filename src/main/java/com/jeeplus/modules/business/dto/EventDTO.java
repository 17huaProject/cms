package com.jeeplus.modules.business.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.BusinessDataEntity;

public class EventDTO extends BusinessDataEntity<EventDTO>{

	private static final long serialVersionUID = 1L;

	private Integer id;

    private String eventName;

    private String type;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date eventTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    
    private Integer eventId;
    
    private String eventDesc;

    private String keywords;

    private String content;
    
    private String eventStatus ;
    
    private String capacityScope ;		//人数范围
    private Short capacityStart ;		//人数开始范围
    private Short capacityEnd ;		//人数结束范围

    private Date eventStateTime ; 	//活动开始时间
    private Date eventEndTime ;		//活动结束时间
    private Date closingStateTime ;	//报名开始时间
    private Date closingEndTime ;		//报名结束时间
    
    private String provinceCode ;
    
    private String freeService;
    private String[] freeServices;

    private Integer customId ;
    private String shareDesc ;  //分享描述语
    private BigDecimal venueFeeRate;	//1=固定金额 ， <1 的数是佣金费率，X总金额
    private BigDecimal venueFee;		//本次活动商家佣金
    private Integer vunueSettleStatus ;
    
    
    public Integer getVunueSettleStatus() {
		return vunueSettleStatus;
	}

	public void setVunueSettleStatus(Integer vunueSettleStatus) {
		this.vunueSettleStatus = vunueSettleStatus;
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

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public Integer getCustomId() {
		return customId;
	}


	public void setCustomId(Integer customId) {
		this.customId = customId;
	}


	/**
     * 同步 id = eventId
     * @return
     */
    public void synchroId(){
    	eventId = id ;
    }
    
    
    public String[] getFreeServices() {
		return freeServices;
	}


	public void setFreeServices(String[] freeServices) {
		this.freeServices = freeServices;
	}


	public String getFreeService() {
		return freeService;
	}

	public void setFreeService(String freeService) {
		this.freeService = freeService;
	}
    
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCapacityScope() {
		return capacityScope;
	}

	public void setCapacityScope(String capacityScope) {
		this.capacityScope = capacityScope;
	}

	public Date getEventStateTime() {
		return eventStateTime;
	}

	public void setEventStateTime(Date eventStateTime) {
		this.eventStateTime = eventStateTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Date getClosingStateTime() {
		return closingStateTime;
	}

	public void setClosingStateTime(Date closingStateTime) {
		this.closingStateTime = closingStateTime;
	}

	public Date getClosingEndTime() {
		return closingEndTime;
	}

	public void setClosingEndTime(Date closingEndTime) {
		this.closingEndTime = closingEndTime;
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
	
	
	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
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
		return HtmlUtils.htmlUnescape(content);
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Short getCapacityStart() {
		return capacityStart;
	}


	public void setCapacityStart(Short capacityStart) {
		this.capacityStart = capacityStart;
	}


	public Short getCapacityEnd() {
		return capacityEnd;
	}


	public void setCapacityEnd(Short capacityEnd) {
		this.capacityEnd = capacityEnd;
	}
    
    
}
