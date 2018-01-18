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
import com.jeeplus.modules.business.entity.Joinus;
import com.jeeplus.modules.business.service.JoinusService;
import com.jeeplus.modules.business.third.mailTemplet.MailContentHTMLTempletAbstract;
import com.jeeplus.modules.business.third.mailTemplet.MailContentJoinus;
import com.jeeplus.modules.business.utils.DateTimeUtils;

public class JoinusJob {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	private JoinusService joinusService = (JoinusService) SpringContextHolder.getApplicationContext().getBean("JoinusService") ;
	private String emailSmtp = Global.getConfig("email.smtp");
	private String emailPort = Global.getConfig("email.port");
	private String emailSenderName = Global.getConfig("email.name");
	private String emailSenderPasswd = Global.getConfig("email.passwd");
	
	/**
	 * 未处理的加盟合作通知 <br/>
	 * 时间：每日16:05
	 */
	public void undealJoinusNotify(){
		String recievers = Global.getConfig("email.joinus.recievers");
		if (StringUtils.isBlank(recievers)) {
			logger.error("未设置发票邮件接收者");
			return ;
		}
		
		List<Joinus> undealJoinus = joinusService.listUndealJoinus() ;
		if (undealJoinus != null && undealJoinus.size() > 0) {
			String currentMonth = DateTimeUtils.getDateTimeFormat("yyyy年MM月dd日" , new Date() );
			String title = currentMonth + "前未处理的加盟合作情况";
			String content = null ;
			MailContentHTMLTempletAbstract contentTemplet = genJoinusContentTemplet(undealJoinus) ;
			//发送邮件
			String[] recieversArr = recievers.split(";");
			for(String reciever: recieversArr){
				String[] emailPair = reciever.split(":");
				content = contentTemplet.genContent(emailPair[0], null) ;
				boolean isSuccess = MailSendUtils.sendEmail(emailSmtp, emailPort, emailSenderName, emailSenderPasswd ,
						emailPair[1],  title, content ,null , 2);

				if(isSuccess){
					logger.info(emailPair[1] + " : undealJoinusNotify email send successfully!");
				}else{
					logger.info(emailPair[1] + " : undealJoinusNotify email failed!");
				}
			}
		}
	}
	
	/**
	 * 加盟合作内容模板
	 * @param undealInvoices 未处理的发票
	 * @return
	 */
	private MailContentHTMLTempletAbstract genJoinusContentTemplet(List<Joinus> undealJoinuses ) {
		String text = "以下是客户加盟合作汇总信息，请及时前往 <a href='http://cms.17hua.me/yqhCMS' target='_blank'>后台管理系统</a> 进行处理。" ;
		List<String> tableHeader = ImmutableList.of("申请人","手机号","申请时间","职位","描述","处理状态") ;
		List<String> joinus = null ;
		
		List<List<String>> tableItems = new ArrayList<List<String>>() ;
		if (undealJoinuses != null && undealJoinuses.size() > 0) {
			for (Joinus undealJoinus : undealJoinuses) {
				joinus = ImmutableList.of( undealJoinus.getName(),
										   undealJoinus.getPhone(),
										   DateTimeUtils.getDateTimeFormat("yyyy-MM-dd HH:mm", undealJoinus.getCreateTime()),
										   undealJoinus.getPosition(),
										   undealJoinus.getDescription(),
											"未处理");
				tableItems.add(joinus) ;
				
			}
		} else {
			joinus = ImmutableList.of("无","无","无","无","无");
			tableItems.add(joinus) ;
		}
		
		
		MailContentHTMLTempletAbstract feedbackContent = new MailContentJoinus(text , tableHeader , tableItems);
		return feedbackContent;
	}
}
