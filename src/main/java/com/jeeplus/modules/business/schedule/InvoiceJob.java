package com.jeeplus.modules.business.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableList;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.mail.MailSendUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dto.InvoiceInfoDTO;
import com.jeeplus.modules.business.service.InvoiceService;
import com.jeeplus.modules.business.third.mailTemplet.MailContentHTMLTempletAbstract;
import com.jeeplus.modules.business.third.mailTemplet.MailContentInvoices;
import com.jeeplus.modules.business.utils.DateTimeUtils;

/**
 * 发票通知
 * @author afanti
 *
 */
public class InvoiceJob {

	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	private InvoiceService invoiceService = (InvoiceService) SpringContextHolder.getApplicationContext().getBean("InvoiceService") ;
	private String emailSmtp = Global.getConfig("email.smtp");
	private String emailPort = Global.getConfig("email.port");
	private String emailSenderName = Global.getConfig("email.name");
	private String emailSenderPasswd = Global.getConfig("email.passwd");
	
	/**
	 * 未处理的发票信息通知 <br/>
	 * 时间：每日5点
	 */
	public void undealInvoiceNotify() {
		String recievers = Global.getConfig("email.invoice.recievers");
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		
		List<InvoiceInfoDTO> undealInvoices = invoiceService.listUndealInvoices() ;
		if (undealInvoices != null && undealInvoices.size() > 0) {
			String currentMonth = DateTimeUtils.getDateTimeFormat("yyyy年MM月dd日" , new Date() );
			String title = currentMonth + "前未处理的发票情况";
			String content = null ;
			MailContentHTMLTempletAbstract contentTemplet = genInvoicesContentTemplet(undealInvoices) ;
			//生成excle
			String[] attachFileNames = invoiceService.getInvoiceItemsExcel(currentMonth , undealInvoices) ;
			//发送邮件
			String[] recieversArr = recievers.split(";");
			for(String reciever: recieversArr){
				String[] emailPair = reciever.split(":");
				content = contentTemplet.genContent(emailPair[0], null) ;
				boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
						emailPair[1],  title, content ,attachFileNames , 2);

				if(isSuccess){
					logger.info(emailPair[1] + " : undealInvoiceNotify email send successfully!");
				}else{
					logger.info(emailPair[1] + " : undealInvoiceNotify email failed!");
				}
			}
		}
	}
	
	
	/**
	 * 发票内容模板
	 * @param undealInvoices 未处理的发票
	 * @return
	 */
	private MailContentHTMLTempletAbstract genInvoicesContentTemplet(List<InvoiceInfoDTO> undealInvoices ) {
		String text = "以下是发票汇总信息，发票明细请参见附件。若已处理，请及时前往 <a href='http://cms.17hua.me/yqhCMS' target='_blank'>后台管理系统</a> ，修改发票处理状态。" ;
		List<String> tableHeader = ImmutableList.of("发票类型","品名","单价","数量","金额","抬头") ;
		List<String> invoice = null ;
		
		List<List<String>> tableItems = new ArrayList<List<String>>() ;
		if (undealInvoices != null && undealInvoices.size() > 0) {
			for (InvoiceInfoDTO undealInvoice : undealInvoices) {
				invoice = ImmutableList.of( undealInvoice.getInvoiceType(),
											undealInvoice.getOrderName(),
											undealInvoice.getSalePrice(),
											undealInvoice.getNumber(),
											undealInvoice.getAmount(),
											undealInvoice.getTitle());
				tableItems.add(invoice) ;
				
			}
		} else {
			invoice = ImmutableList.of("无","无","无","无","无");
			tableItems.add(invoice) ;
		}
		
		
		MailContentHTMLTempletAbstract invoiceContent = new MailContentInvoices(text , tableHeader , tableItems);
		return invoiceContent;
	}
	
	
	
	
	
}
