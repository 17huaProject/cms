package com.jeeplus.modules.business.dto;

import org.springframework.web.util.HtmlUtils;

import com.jeeplus.common.utils.excel.annotation.ExcelField;


public class MaterialDeliveryDTO{

    private Integer id;
    private Integer eventId;
    private Integer goodId;
	@ExcelField(title="出库量", align=1, sort=30)
    private Short outCount;
	@ExcelField(title="入库量", align=1, sort=40)
    private Short inCount;
    private String goodsInfo ;
	@ExcelField(title="物料名", align=1, sort=20)
    private String goodName ;
	@ExcelField(title="物料编号", align=1, sort=10)
    private String goodsNo;
    private Integer modelId ; 

    
    
	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo =  HtmlUtils.htmlUnescape(goodsInfo);
	}


}