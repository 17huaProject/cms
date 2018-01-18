package com.jeeplus.modules.business.bo;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单明细 excel
 * @author afanti
 *
 */
public class OrderItemsExcelBO {

    private String  eventName; 		//活动名
    private String  eventTime;		//活动时间
    private String  orderId;		//订单编号
    private String  orderName;		//订单名
    private String  orderType;		//订单类型
    private String  number;			//订单数量
    private String  costPrice;		//原价
    private String  salePrice;		//售价
    private String  orderAmount;	//订单金额
    private String  paidAmount;		//微信支付金额
    private String  balanceAmount;	//账户扣款(虚拟)
    private String  couponAmount;	//优惠券金额
    private String  payBank;		//付款银行
    private String  payTime;		//支付时间
    private String  payTraceNo;		//支付交易号
    private String  status;			//订单状态
    
    @ExcelField(title="活动名", align=2, sort=10)
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	@ExcelField(title="活动时间", align=2, sort=20)
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	@ExcelField(title="订单编号", align=2, sort=30)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="订单名", align=2, sort=40)
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	@ExcelField(title="订单类型", align=2, sort=50)
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@ExcelField(title="订单数量", align=2, sort=60)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="原价", align=2, sort=70)
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	
	@ExcelField(title="售价", align=2, sort=80)
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	@ExcelField(title="订单金额", align=2, sort=90)
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@ExcelField(title="微信支付金额", align=2, sort=100)
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	@ExcelField(title="账户扣款(虚拟)", align=2, sort=110)
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@ExcelField(title="优惠券金额", align=2, sort=120)
	public String getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}
	
	@ExcelField(title="付款银行", align=2, sort=130)
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	@ExcelField(title="支付时间", align=2, sort=140)
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	@ExcelField(title="支付交易号", align=2, sort=150)
	public String getPayTraceNo() {
		return payTraceNo;
	}
	public void setPayTraceNo(String payTraceNo) {
		this.payTraceNo = payTraceNo;
	}
	
	@ExcelField(title="订单状态", align=2, sort=160)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
