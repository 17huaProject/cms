package com.jeeplus.modules.business.dto;


import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 开具发票信息
 */
public class InvoiceInfoDTO {

    private String invoiceType; 	//发票类型
    private String orderName;		//品名
    private String salePrice;	//单价
    private String number;		//数量
    private String amount;		//金额
    private String title;			//抬头
    private String companyCode;		//纳税人识别号
    private String companyInfo;		//公司地址、电话
    private String bankName;		//开户银行
    private String bankCard;		//开户账号
    private String taxRate = CommonConstants.TAX_RATE;			//税率
	private String address = CommonConstants.COMPANY_ADDRESS ;	//寄送发票地址
	
	@ExcelField(title="发票类型", align=2, sort=10)
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		if (CommonConstants.InvoiceType.PERSON.name().equals(invoiceType)) {
			this.invoiceType = CommonConstants.InvoiceType.PERSON.getCode() ;
		} else if (CommonConstants.InvoiceType.TAXCOM.name().equals(invoiceType)) {
			this.invoiceType = CommonConstants.InvoiceType.TAXCOM.getCode() ;
		} else if (CommonConstants.InvoiceType.TAXSPE.name().equals(invoiceType)) {
			this.invoiceType = CommonConstants.InvoiceType.TAXSPE.getCode() ;
		}
	}
	
	@ExcelField(title="品名", align=2, sort=20)
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@ExcelField(title="单价", align=2, sort=30)
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	@ExcelField(title="数量", align=2, sort=40)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="金额", align=2, sort=50)
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="发票抬头", align=2, sort=60)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="纳税人识别号", align=2, sort=70)
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@ExcelField(title="公司信息", align=2, sort=80)
	public String getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	
	@ExcelField(title="开户银行", align=2, sort=90)
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@ExcelField(title="开户账号", align=2, sort=100)
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
	@ExcelField(title="税率", align=2, sort=110)
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	
	@ExcelField(title="寄送发票地址", align=2, sort=120)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	


	
	


}
