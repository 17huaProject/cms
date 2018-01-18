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
import com.jeeplus.modules.business.dto.FeedbackDTO;
import com.jeeplus.modules.business.service.FeedbackService;
import com.jeeplus.modules.business.third.mailTemplet.MailContentFeedbacks;
import com.jeeplus.modules.business.third.mailTemplet.MailContentHTMLTempletAbstract;
import com.jeeplus.modules.business.utils.DateTimeUtils;

/**
 * 意见反馈 邮件通知
 * @author afanti
 *
 */
public class FeedbackJob {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	private FeedbackService feedbackService = (FeedbackService) SpringContextHolder.getApplicationContext().getBean("FeedbackService") ;
	private String emailSmtp = Global.getConfig("email.smtp");
	private String emailPort = Global.getConfig("email.port");
	private String emailSenderName = Global.getConfig("email.name");
	private String emailSenderPasswd = Global.getConfig("email.passwd");
	
	/**
	 * 未处理的意见反馈通知 <br/>
	 * 时间：每日16:00
	 */
	public void undealFeedbacksNotify(){
		String recievers = Global.getConfig("email.feedback.recievers");
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		
		List<FeedbackDTO> undealFeedbacks = feedbackService.listUndealFeedbacks() ;
		if (undealFeedbacks != null && undealFeedbacks.size() > 0) {
			String currentMonth = DateTimeUtils.getDateTimeFormat("yyyy年MM月dd日" , new Date() );
			String title = currentMonth + "前未处理的客户意见反馈情况";
			String content = null ;
			MailContentHTMLTempletAbstract contentTemplet = genFeedbacksContentTemplet(undealFeedbacks) ;
			//发送邮件
			String[] recieversArr = recievers.split(";");
			for(String reciever: recieversArr){
				String[] emailPair = reciever.split(":");
				content = contentTemplet.genContent(emailPair[0], null) ;
				boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
						emailPair[1],  title, content ,null , 2);

				if(isSuccess){
					logger.info(emailPair[1] + " : undealFeedbacksNotify email send successfully!");
				}else{
					logger.info(emailPair[1] + " : undealFeedbacksNotify email failed!");
				}
			}
		}
	}
	
	/**
	 * 意见反馈内容模板
	 * @param undealInvoices 未处理的发票
	 * @return
	 */
	private MailContentHTMLTempletAbstract genFeedbacksContentTemplet(List<FeedbackDTO> undealFeedbacks ) {
		String text = "以下是客户意见反馈汇总信息，请及时前往 <a href='http://cms.17hua.me/yqhCMS' target='_blank'>后台管理系统</a> 进行处理。" ;
		List<String> tableHeader = ImmutableList.of("标题","反馈内容","反馈时间","状态") ;
		List<String> feedback = null ;
		
		List<List<String>> tableItems = new ArrayList<List<String>>() ;
		if (undealFeedbacks != null && undealFeedbacks.size() > 0) {
			for (FeedbackDTO undealFeedback : undealFeedbacks) {
				feedback = ImmutableList.of( undealFeedback.getTitle(),
											undealFeedback.getContent(),
											DateTimeUtils.getDateTimeFormat("yyyy-MM-dd HH:mm", undealFeedback.getIssueTime()),
											"未处理");
				tableItems.add(feedback) ;
				
			}
		} else {
			feedback = ImmutableList.of("无","无","无","无","无");
			tableItems.add(feedback) ;
		}
		
		
		MailContentHTMLTempletAbstract feedbackContent = new MailContentFeedbacks(text , tableHeader , tableItems);
		return feedbackContent;
	}
	
	
	
}
