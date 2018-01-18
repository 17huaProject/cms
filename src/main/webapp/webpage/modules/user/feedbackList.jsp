<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>反馈管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="feedback" action="${ctx}/wms/user/feedback/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>反馈时间：</span>
				<input id="stateTime" name="stateTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value='${event.stateTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="endTime"  name="endTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${event.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/>
			
			<span class="querylistspan">状态：</span>
				<form:select path="status"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('feedback_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="wms:user:feedback:del">
				<table:delRow url="${ctx}/wms/user/feedback/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th>标题</th>
				<th>反馈时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="feedback">
			<tr>
				<td> <input type="checkbox" id="${feedback.id}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看反馈', '${ctx}/wms/user/feedback/formView?id=${feedback.id}','800px', '680px')">
						<c:choose>
							<c:when test="${fn:length(feedback.title)>10}">${fn:substring(feedback.title, 0, 10)} ······</c:when>
							<c:otherwise>${fn:substring(feedback.title, 0, 10)}</c:otherwise>
						</c:choose>
					</a>
				</td>
				<td><fmt:formatDate value='${feedback.issueTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${fns:getDictLabel(feedback.status,'feedback_status','')} </td> 
				<td>
					<shiro:hasPermission name="wms:user:feedback:view">
						<a href="#" onclick="openDialogView('查看反馈', '${ctx}/wms/user/feedback/formView?id=${feedback.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:user:feedback:edit">
						<c:if test="${feedback.status eq 0}">
						<a href="#" onclick="openDialog('处理反馈', '${ctx}/wms/user/feedback/form?id=${feedback.id}','800px', '700px', 'feedbackContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 处理</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:user:feedback:del">
						<a href="${ctx}/wms/user/feedback/delete?id=${feedback.id}" onclick="return confirmx('确认要删除该反馈吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>