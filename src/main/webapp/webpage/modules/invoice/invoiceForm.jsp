<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发票管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/invoice/save" commandName="invoice" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">发票信息</label></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">开票抬头:</label></td>
		         <td><form:input path="title"  class=" form-control"/></td>
		         <td class="active"><label class="pull-right">发票金额:</label></td>
		         <td><form:input path="amount" class=" form-control"/></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">公司统一信用编号:</label></td>
		         <td><form:input path="companyCode" class=" form-control"/></td>
		         <td class="active"><label class="pull-right">公司信息:</label></td>
		         <td><form:input path="companyInfo" class=" form-control"/></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">发票内容:</label></td>
		         <td colspan="3"><form:input path="content" class=" form-control"/></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">发票类型:</label></td>
		         <td>
			         <form:select path="invoiceType"  class="form-control required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('invoice_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
				 </td>
		         <td class="active"><label class="pull-right">发票实体:</label></td>
		         <td>
			         <form:select path="pattern"  class="form-control required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('invoice_pattern')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
				 </td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">提取方式:</label></td>
		         <td>
			         <form:select path="shippingMode"  class="form-control required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('invoice_shippingMode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
		         <td class="active"><label class="pull-right">发票状态:</label></td>
		         <td>
			         <form:select path="invoiceStatus"  class="form-control required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('invoice_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
				 </td>
		      </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">银行名称:</label></td>
		         <td><form:input path="bankName" class=" form-control"/></td>
		         <td class="active"><label class="pull-right">银行帐号:</label></td>
		         <td><form:input path="bankCard" class=" form-control"/></td>
			  </tr>
		   	  <tr>
		         <td class="active"><label class="pull-right">创建时间:</label></td>
		         <td> <fmt:formatDate value='${invoice.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
		         <td class="active"><label class="pull-right">更新时间:</label></td>
		         <td> <fmt:formatDate value='${invoice.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
			  </tr>
		      
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">订单信息</label></td>
			  </tr>
		      <tr>
		         <td class="active"><label class="pull-right">订单编号:</label></td>
		         <td> ${invoice.orderIds} </td>
		         <td class="active"><label class="pull-right">订单类型:</label></td>
		         <td> ${fns:getDictLabel(invoice.orderType,'invoice_orderType','')} </td>
		      </tr>
		      
		      <tr>
			     <td colspan="4" class="active"><label class="pull-left">客户信息</label></td>
			  </tr>
		      <tr>
		         <td  class="active"><label class="pull-right">姓名:</label></td>
		         <td>${user.realname}</td>
		         <td  class="active"><label class="pull-right">昵称:</label></td>
		         <td>${userProfile.nickname}</td>
		    </tr>
		   	<tr>
		         <td  class="active"><label class="pull-right">手机号:</label></td>
		         <td>${user.phone}</td>
		         <td  class="active"><label class="pull-right">身份证号:</label></td>
		         <td>${user.idcard}</td>
		    </tr>
		      
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>