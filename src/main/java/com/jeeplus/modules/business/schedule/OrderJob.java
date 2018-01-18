package com.jeeplus.modules.business.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.mail.MailSendUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.bo.OrderSummaryBO;
import com.jeeplus.modules.business.service.OrderService;
import com.jeeplus.modules.business.third.jfreeChart.OrderLineChart;
import com.jeeplus.modules.business.third.mailTemplet.MailContentHTMLTempletAbstract;
import com.jeeplus.modules.business.third.mailTemplet.MailContentOrders;
import com.jeeplus.modules.business.utils.DateTimeUtils;

/**
 * 订单通知
 * @author afanti
 *
 */
public class OrderJob {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	private OrderService orderService = (OrderService) SpringContextHolder.getApplicationContext().getBean("OrderService") ;
	private String emailSmtp = Global.getConfig("email.smtp");
	private String emailPort = Global.getConfig("email.port");
	private String emailSenderName = Global.getConfig("email.name");
	private String emailSenderPasswd = Global.getConfig("email.passwd");
	private String recievers = Global.getConfig("email.order.recievers");
	
	/**
	 * 昨日新订单通知 <br/>
	 * 时间：每日5点通知昨日新订单状况
	 */
	public void yesterdayOrdersNotify(){
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		OrderSummaryBO orderSummaryBO = orderService.getOrderSummaryInfo("TO_DAYS(NOW( )) - TO_DAYS(pay_time) = 1") ;
		String yesterday = DateTimeUtils.getDateTimeFormat("yyyy-MM-dd" ,DateTimeUtils.delayOrAheadCurrentTime(Calendar.DAY_OF_YEAR, -1) );
		String yesterdayDesc = DateTimeUtils.getDateTimeFormat("yyyy年MM月dd日" ,DateTimeUtils.delayOrAheadCurrentTime(Calendar.DAY_OF_YEAR, -1) );
		String title = "订单日报：" + yesterdayDesc + "订单情况" ;
		MailContentHTMLTempletAbstract contentTemplet = genOrdersContentTemplet(orderSummaryBO , yesterdayDesc , null) ;
		String content = null ;
		String querySqlTime = "DATE_FORMAT( op.pay_time, '%Y%m%d' ) = DATE_FORMAT('"+ yesterday +"' , '%Y%m%d' )" ;
		String[] attachFileNames = orderService.getOrderItemsExcel(yesterdayDesc, querySqlTime) ;
		
		String[] addresses = recievers.split(";");
		for(String address: addresses){
			String[] emailPair = address.split(":");
			content = contentTemplet.genContent(emailPair[0], null) ;
			boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
														emailPair[1],  title, content ,attachFileNames , 2);
			
			if(isSuccess){
				logger.info(emailPair[1] + " : yesterdayOrdersNotify email send successfully!");
			}else{
				logger.info(emailPair[1] + " : yesterdayOrdersNotify email failed!");
			}
		}
		
		
	}

	/**
	 * 每周订单汇总信息通知 <br/>
	 * 时间：周日24点
	 */
	public void weekOrdersNotify(){
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		OrderSummaryBO orderSummaryBO = orderService.getOrderSummaryInfo("YEARWEEK(date_format(pay_time,'%Y-%m-%d')) = YEARWEEK(now())") ;
		Date today = new Date() ; 
		String currentMonth = DateTimeUtils.getDateTimeFormat("yyyy年MM月" , today );
		int currentWeek = DateTimeUtils.getCurrentWeek(today) ;
		String datetimeDesc = currentMonth + "第" + currentWeek + "周" ;
		String title = "订单周报：" + datetimeDesc + "订单情况" ;
		MailContentHTMLTempletAbstract contentTemplet = genOrdersContentTemplet(orderSummaryBO , datetimeDesc , null) ;
		String content = null ;
		String querySqlTime = "YEARWEEK(date_format(pay_time,'%Y-%m-%d')) = YEARWEEK(now())" ;
		String[] attachFileNames = orderService.getOrderItemsExcel(datetimeDesc, querySqlTime) ;
		
		String[] addresses = recievers.split(";");
		for(String address: addresses){
			String[] emailPair = address.split(":");
			content = contentTemplet.genContent(emailPair[0], null) ;
			boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
														emailPair[1],  title, content ,attachFileNames , 2);
			
			if(isSuccess){
				logger.info(emailPair[1] + " : weekOrdersNotify email send successfully!");
			}else{
				logger.info(emailPair[1] + " : weekOrdersNotify email failed!");
			}
		}
		
	}
	
	/**
	 * 月订单汇总信息通知 <br/>
	 * 时间：每月最后一个工作日
	 */
	public void monthOrdersNotify(){
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		
		OrderSummaryBO orderSummaryBO = orderService.getOrderSummaryInfo("DATE_FORMAT( pay_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )") ;
		String currentMonth = DateTimeUtils.getDateTimeFormat("yyyy年MM月" , new Date() );
		String title = "订单月报：" + currentMonth + "订单情况" ;
		
		//订单折线图
		List<String> orderChartImages = new ArrayList<String>() ;
		OrderLineChart orderLineChart = new OrderLineChart(currentMonth) ;
		String orderChartImg = orderLineChart.saveImg() ;
		orderChartImages.add(orderChartImg) ;
		
		MailContentHTMLTempletAbstract contentTemplet = genOrdersContentTemplet(orderSummaryBO , currentMonth , orderChartImages) ;
		String content = null ;
		String querySqlTime = "DATE_FORMAT( pay_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )" ;
		String[] attachFileNames = orderService.getOrderItemsExcel(currentMonth, querySqlTime) ;
		
		String[] addresses = recievers.split(";");
		for(String address: addresses){
			String[] emailPair = address.split(":");
			content = contentTemplet.genContent(emailPair[0], null) ;
			boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
														emailPair[1],  title, content ,attachFileNames , 2);
			
			if(isSuccess){
				logger.info(emailPair[1] + " : monthOrdersNotify email send successfully!");
			}else{
				logger.info(emailPair[1] + " : monthOrdersNotify email failed!");
			}
		}
	}
	
	
	/**
	 * 订单内容模板
	 * @param orderSummaryBO	订单汇总信息
	 * @param datetimeDesc		时间描述语
	 * @return
	 */
	private MailContentHTMLTempletAbstract genOrdersContentTemplet(OrderSummaryBO orderSummaryBO , String datetimeDesc , List<String> orderChartImages) {
		String text = "以下是" + datetimeDesc + "订单汇总统计信息，订单明细请参见附件" ;
		List<String> tableHeader = ImmutableList.of("总订单数","总订单额(元)","微信支付总额(元)","账户扣款总额(元)","优惠券扣款总额(元)") ;
		List<String> orderData = null ;
		
		if (null == orderSummaryBO) {
			orderData = ImmutableList.of("无","无","无","无","无");
		} else {
			orderData = ImmutableList.of(  String.valueOf(orderSummaryBO.getSumNumber()),
													String.valueOf(orderSummaryBO.getSumOrderAmount()) ,
													String.valueOf(orderSummaryBO.getSumPaidAmount()),
													String.valueOf(orderSummaryBO.getSumBalanceAmount()),
													String.valueOf(orderSummaryBO.getSumCouponAmount()) );
		}
		
		List<List<String>> tableItems = new ArrayList<List<String>>() ;
		tableItems.add(orderData) ;
		
		MailContentHTMLTempletAbstract orderContent = new MailContentOrders(text , tableHeader , tableItems , orderChartImages);
		return orderContent;
	}
	
	
}
