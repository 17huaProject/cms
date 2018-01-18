package com.jeeplus.modules.business.third.mailTemplet;

import java.util.Date;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.utils.DateTimeUtils;

/**
 * 邮件 HTML模板
 * @author afanti
 *
 */
public abstract class MailContentHTMLTempletAbstract {
	
	/**
	 * 
	 * @param reciever 	接收者称呼
	 * @param sender	发送者称呼 ，为null时，默认：系统邮件
	 * @return
	 */
	public String genContent(String reciever , String sender){
		StringBuilder sBuilder = new StringBuilder() ;
		sBuilder.append(call(reciever));
		sBuilder.append(content());
		sBuilder.append(signature(sender));
		sBuilder.append(notice());
		return sBuilder.toString() ;
	}
	
	protected String call(String name) {
		return "<div>"+ name +" , 您好:</div>" ;
	}
	
	protected abstract String content();
	
	protected String signature(String sender){
		if (StringUtils.isBlank(sender)) {
			sender = "系统邮件,请勿回复,谢谢" ;
		}
		String currentTime = DateTimeUtils.getDateTimeFormat("yyyy-MM-dd HH:mm:ss" , new Date()) ;
		String signature = 	"<div  style='padding:10px 5px;'>" +
						currentTime +
						"<br/>"+sender+"</div>";
		return signature ;
	}
	
	protected String notice(){
		String notice = "<div style='font-size:12px; color:#999;'>" +
				"<hr style='border:1px solid #CCC;'>" +
				"本邮件及其附件为保密信息，专为指定收件人阅读。如您非指定收件人，请立即通知本人，并请不要进行转发、复制、下载、保存、打印、披露本邮件及其内容。请保守本邮件信息，维护公司权益，若因信息泄露对公司造成的损失或不利影响，本公司将依据《中华人民共和国保密法》及相关条例，和本公司相关保密规定进行处理。" +
				"</div>" ;
		return notice ;
	}
	
	
	
}
