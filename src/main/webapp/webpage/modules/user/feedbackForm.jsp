<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>反馈管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	 .border-none{border:0px;height:auto;}
	 label{min-width: 60px;}
	</style>
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
	<form:form id="inputForm" action="${ctx}/wms/user/feedback/save" commandName="feedback" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">反馈信息</label></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">姓名:</label></td>
			         <td><span class=" form-control border-none">${user.realname}</span></td>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control border-none">${user.phone}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">反馈时间:</label></td>
			         <td><span class=" form-control border-none"><fmt:formatDate value='${feedback.issueTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">处理状态:</label></td>
			         <td><span class=" form-control border-none">${fns:getDictLabel(feedback.status,'feedback_status','')}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">标题:</label></td>
			         <td colspan="3"><span class=" form-control border-none">${feedback.title}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">内容:</label></td>
			         <td colspan="3"><span class=" form-control border-none">${feedback.content}</span></td>
			    </tr>
			    
			    <!-- 处理 情况 -->
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">处理情况</label></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理人:</label></td>
			         <td colspan="3"><span class=" form-control border-none">${feedback.currentUser.name}</span></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理时间:</label></td>
			         <td colspan="3">
			         	<input id="replyTime" name="replyTime"  maxlength="50" class=" form-control" 
						value="<fmt:formatDate value='${feedback.replyTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
					 </td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">回复:</label></td>
			         <td colspan="3">
						<form:textarea path="replyContent" htmlEscape="false" rows="3" maxlength="200" class="form-control required"/>
					</td>
			     </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>