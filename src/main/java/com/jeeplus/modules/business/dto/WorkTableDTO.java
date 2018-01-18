package com.jeeplus.modules.business.dto;

/**
 * 工作台
 *
 */
public class WorkTableDTO {
	private String owner ;
	private String type ; //类型
	private int count ;		//记录数
	private String status ;	//状态
	private double sumPaidAmount ; //支付总额
	
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getSumPaidAmount() {
		return sumPaidAmount;
	}
	public void setSumPaidAmount(double sumPaidAmount) {
		this.sumPaidAmount = sumPaidAmount;
	}
	
	
}
