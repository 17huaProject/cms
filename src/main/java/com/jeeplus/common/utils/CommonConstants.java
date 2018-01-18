package com.jeeplus.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public class CommonConstants {

	/**添加*/
	public static final int ADD = 2;
	/**修改*/
	public static final int UPDATE = 3;
	/**删除*/
	public static final int DELETED = 1;
	/**不删除*/
	public static final Byte DELETED_N = 0;
	/**删除*/
	public static final Byte DELETED_Y = 1;
	/**未审核*/  
	public static final int UNCHECK = 0;
	/**审核通过*/
	public static final int CHECKPASSED = 1;
	
	/**显示*/
	public static final Byte CITY_SHOW = 1 ;
	/**隐藏*/
	public static final Byte CITY_HIDDEN = 0 ;
	
	/**待审核*/
	public static final int AUDIT_WAIT = 0 ; 
	/**审核通过*/
	public static final int AUDIT_PASS = 1 ;
	
	/**无效*/
	public static final int STATUS_INVALID = 0 ;
	/**有效*/
	public static final int STATUS_EFFECTIVE = 1 ;
	
	/**未处理*/
	public static final int STATUS_UNDEAL = 0 ;
	/**已处理*/
	public static final int STATUS_DEALED = 1 ;
	
	public static final String MUNICIPALITY = "11,12,31,50"; //直辖市
	
	public static final String COMPANY_ADDRESS = "长宁区长宁路1436号倍格老船坞f212室" ; 
	
	public static final String TAX_RATE = "6%" ;
	
	public static enum SysUserType{
		
		/**助教*/
	    assistant("4"),	
	    /**画师*/
	    artist("5");	
	    
		private String code ;
	    SysUserType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	public static enum OrderUsedStatus{
		
		/**未用*/
		UNUSED((byte)0),	
		/**已用*/
		USED((byte)1),	
		/**无效*/
		INVALID((byte)2);	
		private byte code ;
		OrderUsedStatus(byte code){
			this.code = code;
		}
		public byte getCode(){
			return this.code;
		}
	}
	
	public static enum OrderPaidStatus{
		/**待付款*/
		UNPAID(0),	
		/**已付款*/
		SUCCESS(1);	
		private int code ;
		OrderPaidStatus(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;
		}
	}
	
	public static enum OrderStatus{
		/**待付款*/
		UNPAID("UNPAID"),	
		/**已付款*/
		SUCCESS("SUCCESS"), 	
		/**待评价*/
		PENDING("PENDING"), 
		/**已评价*/
		FINISH("FINISH"),	
		/**已退款*/
		REFUND("REFUND"),	
		/**交易关闭*/
		CLOSED("CLOSED");
		private String code ;
		OrderStatus(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	public static enum GiftStatus{
		/**待付款*/
		UNPAID("UNPAID"),	
		/**已付款*/
		PAID("PAID"), 	
		/**已收货*/
		RECEIVED("RECEIVED"), 
		/**已退款*/
		REFUND("REFUND");
		private String code ;
		GiftStatus(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	public static enum RegionStatus{
		PROVINCE(1),	//省、直辖市
		CITY(2), 		//市、
		DISTRICTS(3), 	//区县级
		STREETS(4);		//街道
		private int code ;
		public static Map<String, RegionStatus> all = new HashMap<String, CommonConstants.RegionStatus>(RegionStatus.values().length);
		
		static{
			all.put("PROVINCE", PROVINCE);
			all.put("CITY",CITY );
			all.put("DISTRICTS", DISTRICTS);
			all.put("STREETS", STREETS);
		}
		RegionStatus(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;
		}
	}
	
	/**活动类型*/
	public static enum EventStatus{
		/**未开售*/
		PRESALE("PRESALE"), 	
		/**在售*/
		ONSALE("ONSALE"),	
		/**售完*/
		SOLDOUT("SOLDOUT"),	
		/**结束*/
		FINISH("FINISH");
		
		private String code ;
		EventStatus(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	/**活动类型*/
	public static enum EventType{
		/**团建活动*/
		COMPANY("1"), 	
		/**私人活动*/
		PRIVATE("2"),	
		/**公开活动*/
		PUBLIC("3");
		
		private String code ;
		EventType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	/**定制类型*/
	public static enum CustomType{
		/**团建活动*/
		COMPANY("COMPANY"), 	
		/**私人活动*/
		PRIVATE("PRIVATE");
		
		private String code ;
		CustomType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	/**发票类型*/
	public static enum InvoiceType{
		
		/**增值税普通发票*/
		TAXCOM("增值税普通发票"), 	
		/**个人发票*/
		PERSON("个人发票"), 	
		/**增值税专用发票*/
		TAXSPE("增值税专用发票");
		
		private String code ;
		InvoiceType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	/**优惠券类型*/
	public static enum CouponType{
		
		/**分享送礼*/
		SHARECOUPON("SHARECOUPON");
		
		private String code ;
		CouponType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	/**活动分享描述语模板*/
	public static String[] shareDescModels = {
		"我喜欢这个绘画活动,你也来一起画吧"
	};
	
	/**佣金获得者类型*/
	public static enum CommissionTollerType{
		/**画师*/
		ARTIST(1),	
		/**助教*/
		ASSISTANT(2), 	
		/**场地*/
		VENUE(3);
		
		private int code ;
		CommissionTollerType(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;
		}
	}
	
	/**佣金结算状态*/
	public static enum CommissionSettleStatus{
		/**未结算*/
		UNSETTLE(0),	
		/**已结算*/
		SETTLED(1);
		
		private int code ;
		CommissionSettleStatus(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;
		}
	}
	
	



}


