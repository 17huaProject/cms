<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>加入我们</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	 .form-control{border:0px;height:auto;} 
	 label{min-width: 60px;}
	</style>
</head>
<body>
	<form:form id="inputForm" action="" commandName="joinus" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">基本信息</label></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">申请人:</label></td>
			         <td><span class=" form-control">${joinus.name}</span></td>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control">
				        <shiro:hasPermission name="cms:joinus:phone">
						${joinus.phone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="cms:joinus:phone">
						${fn:substring(joinus.phone,0,3)}*****${fn:substring(joinus.phone,8,11)}
						</shiro:lacksPermission>
			         </span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">申请时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${joinus.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">处理状态:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(joinus.status,'joinus_status','')}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">职位:</label></td>
			         <td colspan="3"><span class=" form-control">${joinus.position}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">描述:</label></td>
			         <td colspan="3"><span class=" form-control">${joinus.description}</span></td>
			    </tr>
			    
			    <!-- 处理 情况 -->
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">处理情况</label></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理人:</label></td>
			         <td colspan="3">
			         	<span class=" form-control">
							<c:if test="${joinus.status eq 1}">${joinus.currentUser.name}</c:if>
						</span>
					 </td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理时间:</label></td>
			         <td colspan="3"><fmt:formatDate value='${joinus.replyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">回复:</label></td>
			         <td colspan="3"><span class=" form-control">${joinus.suggestion}</span></td>
			     </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>