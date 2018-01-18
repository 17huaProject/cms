<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发票管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" action="" commandName="invoice" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">发票信息</label></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">开票抬头:</label></td>
		         <td>${invoice.title}</td>
		         <td class="active"><label class="pull-right">发票金额:</label></td>
		         <td>${invoice.amount}</td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">公司统一信用编号:</label></td>
		         <td>${invoice.companyCode}</td>
		         <td class="active"><label class="pull-right">公司信息:</label></td>
		         <td>${invoice.companyInfo} </td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">发票内容:</label></td>
		         <td colspan="3">${invoice.content}</td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">发票类型:</label></td>
		         <td> ${fns:getDictLabel(invoice.invoiceType,'invoice_type','')}</td>
		         <td class="active"><label class="pull-right">发票实体:</label></td>
		         <td> ${fns:getDictLabel(invoice.pattern,'invoice_pattern','')}</td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">提取方式:</label></td>
		         <td> ${fns:getDictLabel(invoice.shippingMode,'invoice_shippingMode','')} </td>
		         <td class="active"><label class="pull-right">发票状态:</label></td>
		         <td>${fns:getDictLabel(invoice.invoiceStatus,'invoice_status','')}</td>
		      </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">银行名称:</label></td>
		         <td>${invoice.bankName}</td>
		         <td class="active"><label class="pull-right">银行帐号:</label></td>
		         <td>${invoice.bankCard}</td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">创建时间:</label></td>
		         <td><fmt:formatDate value='${invoice.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
		         <td class="active"><label class="pull-right">更新时间:</label></td>
		         <td><fmt:formatDate value='${invoice.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
			  </tr>
		      
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">订单信息</label></td>
			  </tr>
		      <tr>
		         <td class="active"><label class="pull-right">订单编号:</label></td>
		         <td>${invoice.orderIds} </td>
		         <td class="active"><label class="pull-right">订单类型:</label></td>
		         <td>${fns:getDictLabel(invoice.orderType,'invoice_orderType','')} </td>
		      </tr>
		      
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">客户信息</label></td>
			  </tr>
		      <tr>
		         <td ><label class="pull-right">姓名:</label></td>
		         <td>${user.realname}</td>
		         <td ><label class="pull-right">昵称:</label></td>
		         <td>${userProfile.nickname}</td>
		    </tr>
		   	<tr>
		         <td ><label class="pull-right">手机号:</label></td>
		         <td>
		         	<shiro:hasPermission name="wms:user:phone">
					${user.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:phone">
					${fn:substring(user.phone,0,3)}*****${fn:substring(user.phone,8,11)}
					</shiro:lacksPermission>
		         </td>
		         <td ><label class="pull-right">身份证号:</label></td>
		         <td>
		         	<shiro:hasPermission name="wms:user:idcard">
					${user.idcard}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:idcard">
					${fn:substring(user.idcard,0,3)}*****${fn:substring(user.idcard,fn:length(user.idcard)-4,fn:length(user.idcard))}
					</shiro:lacksPermission>
		         </td>
		    </tr>
		      
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>