package com.jeeplus.modules.business.third.mailTemplet;

import java.util.List;

/**
 * 发票邮件内容，带表格
 * @author afanti
 */
public class MailContentInvoices extends MailContentHTMLTempletAbstract {

	private List<String> tableHeader ;		//表头
	private List<List<String>> tableItems ; //表内容条目
	private String plainText ; 					//纯文本说明
	
	/**
	 * @param text 纯文本说明
	 * @param tableHeader 表头 使用ArrayList , 无表格数据,使用null
	 * @param tableItems  表内容条目 使用ArrayList , 无表格数据,使用null
	 */
	public MailContentInvoices(String text , List<String> tableHeader , List<List<String>> tableItems) {
		this.plainText = text ;
		this.tableHeader = tableHeader ;
		this.tableItems = tableItems ;
	}
	
	@Override
	protected String content() {
		StringBuilder sBuilder = new StringBuilder() ;
		
		//纯文本内容
		sBuilder.append("<div style='padding:10px 20px;'>"+ plainText +"</div>");
		
		//生成表格信息
		if (tableHeader != null && tableItems != null) {
			sBuilder.append("<div style='padding:10px 20px;'><table cellpadding='0' cellspacing='0' border='1px solid #000' >");
			sBuilder.append("<tr style='text-align:center;'>");
			for (String item : tableHeader) {
				sBuilder.append("<th style='padding:5px;'>"+item+"</th>");
			}
			sBuilder.append("</tr>");
			for (List<String> tableItem : tableItems) {
				sBuilder.append("<tr style='text-align:center;'>");
				for (String item : tableItem) {
					sBuilder.append("<td style='padding:5px;'>"+item+"</td>");
				}
				sBuilder.append("</tr>");
			}
			sBuilder.append("</table></div>");
		}
		
		
		return sBuilder.toString();
	}

}
