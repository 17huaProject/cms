<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>反馈管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	 .form-control{border:0px;height:auto;} 
	 label{min-width: 60px;}
	</style>
</head>
<body>
	<form:form id="inputForm" action="" commandName="feedback" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">反馈信息</label></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">反馈人:</label></td>
			         <td><span class=" form-control">${user.realname}</span></td>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control">${user.phone}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">反馈时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${feedback.issueTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">处理状态:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(feedback.status,'feedback_status','')}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">反馈标题:</label></td>
			         <td colspan="3"><span class=" form-control">${feedback.title}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">反馈内容:</label></td>
			         <td colspan="3"><span class=" form-control">${feedback.content}</span></td>
			    </tr>
			    
			    <!-- 处理 情况 -->
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">处理情况</label></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理人:</label></td>
			         <td colspan="3">
			         	<span class=" form-control">
							<c:if test="${feedback.status eq 1}">${feedback.currentUser.name}</c:if>
						</span>
					 </td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理时间:</label></td>
			         <td colspan="3"><fmt:formatDate value='${feedback.replyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">回复:</label></td>
			         <td colspan="3"><span class=" form-control">${feedback.replyContent}</span></td>
			     </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>