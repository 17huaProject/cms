package com.jeeplus.modules.business.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单汇总统计业务数据
 * @author afanti
 *
 */
public class OrderSummaryBO {

	private int sumNumber ;					//订单总数
	private BigDecimal sumOrderAmount ;		//订单总额
	private BigDecimal sumPaidAmount ;		//微信支付总额
	private BigDecimal sumBalanceAmount ;	//账户扣款总额
	private BigDecimal sumCouponAmount ;	//优惠券总金额
	private Date payDate ;					//支付日期
	
	
	
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public int getSumNumber() {
		return sumNumber;
	}
	public void setSumNumber(int sumNumber) {
		this.sumNumber = sumNumber;
	}
	public BigDecimal getSumOrderAmount() {
		return sumOrderAmount;
	}
	public void setSumOrderAmount(BigDecimal sumOrderAmount) {
		this.sumOrderAmount = sumOrderAmount;
	}
	public BigDecimal getSumPaidAmount() {
		return sumPaidAmount;
	}
	public void setSumPaidAmount(BigDecimal sumPaidAmount) {
		this.sumPaidAmount = sumPaidAmount;
	}
	public BigDecimal getSumBalanceAmount() {
		return sumBalanceAmount;
	}
	public void setSumBalanceAmount(BigDecimal sumBalanceAmount) {
		this.sumBalanceAmount = sumBalanceAmount;
	}
	public BigDecimal getSumCouponAmount() {
		return sumCouponAmount;
	}
	public void setSumCouponAmount(BigDecimal sumCouponAmount) {
		this.sumCouponAmount = sumCouponAmount;
	}
	
	
}
