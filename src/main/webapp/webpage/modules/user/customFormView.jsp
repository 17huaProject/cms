<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定制管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>	
</head>
<body>
	<form:form id="inputForm" action="" commandName="custom" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">定制信息</label></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">姓名:</label></td>
			         <td><span class=" form-control">${custom.contact}</span></td>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control">
				         <shiro:hasPermission name="wms:custom:phone">
						 ${custom.phone}
						 </shiro:hasPermission>
						 <shiro:lacksPermission name="wms:custom:phone">
						 ${fn:substring(custom.phone,0,3)}*****${fn:substring(custom.phone,8,11)}
						 </shiro:lacksPermission>
			         </span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">城市:</label></td>
			         <td><span class=" form-control">${custom.city}</span></td>
			         <td class="active"><label class="pull-right">类型:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(custom.customType,'custom_type','')}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">预计时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${custom.estDate}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">预计人数:</label></td>
			         <td><span class=" form-control">${custom.estNum}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">创建时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${custom.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">状态:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(custom.status,'custom_status','')}</span></td>
			    </tr>
			    
			    <!-- 处理 情况 -->
			    <c:if test="${custom.status ne 0}">
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">处理情况</label></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理人:</label></td>
			         <td colspan="3"><span class=" form-control">${custom.currentUser.name}</span></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">意见:</label></td>
			         <td colspan="3"><span class=" form-control">${fns:getDictLabel(custom.status,'custom_status','')}</span></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">处理时间:</label></td>
			         <td colspan="3"><span class=" form-control"><fmt:formatDate value='${custom.transTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			     </tr>
			     <tr>
		         	<td class="active"><label class="pull-right">备注:</label></td>
		         	<td colspan=3><span class=" form-control">${custom.transDesc}</span></td>
		     	 </tr>
		     	 </c:if>
			    <!-- 活动情况 -->
			     <c:if test="${custom.eventId ne 0}">
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">定制活动</label></td>
			    </tr>
			     <tr>
			         <td colspan="2" rowspan="3"> <img src="${art.bigImg}" style="max-width:100%; max-height:100%;"/> </td>
			         <td class="active"><label class="pull-right">名称:</label></td>
			         <td><span class=" form-control">${event.eventName}</span></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">画师:</label></td>
			         <td><span class=" form-control">${artist.realname} - ${artist.phone}</span></td>  
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right">地点:</label></td>
			         <td><span class=" form-control">${venue.venueName}</span></td>
			     </tr>
			     </c:if>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>