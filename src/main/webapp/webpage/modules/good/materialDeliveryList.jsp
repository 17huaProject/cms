<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title></title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="materialDelivery" action="${ctx}/cms/materialDelivery/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>活动名：</span>
				<form:input path="eventName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		</div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="cms:materialDelivery:add">
				<table:addRow url="${ctx}/cms/materialDelivery/form" title="出库单" width="800px" height="620px" target="materialDeliveryContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:materialDelivery:del">
				<table:delRow url="${ctx}/cms/materialDelivery/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="cms:materialDelivery:export">
				<table:exportExcel url="${ctx}/cms/materialDelivery/export"  id="contentTable"></table:exportExcel>
			</shiro:hasPermission> --%>
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
				<th>活动名</th>
				<th>最近修改时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="materialDelivery">
			<tr>
				<td> <input type="checkbox" id="${materialDelivery.eventId}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看物料单', '${ctx}/cms/materialDelivery/viewForm?eventId=${materialDelivery.eventId}','800px', '680px')">${materialDelivery.eventName}</a></td>
				<td><fmt:formatDate value="${materialDelivery.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<shiro:hasPermission name="cms:materialDelivery:view">
						<a href="#" onclick="openDialogView('查看物料单', '${ctx}/cms/materialDelivery/viewForm?eventId=${materialDelivery.eventId}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${materialDelivery.status eq 0}">
					<shiro:hasPermission name="cms:materialDelivery:edit">
						<a href="#" onclick="openDialog('修改出库物料单', '${ctx}/cms/materialDelivery/form?eventId=${materialDelivery.eventId}','800px', '700px', 'materialDeliveryContent')" class="btn btn-warning btn-xs" ><i class="fa fa-edit"></i> 修改出库单</a>
					</shiro:hasPermission>
					</c:if>
					<c:if test="${materialDelivery.status eq 0 or materialDelivery.status eq 1}">
					<shiro:hasPermission name="cms:materialDelivery:edit">
						<a href="#" onclick="openDialog('添加/修改入库物料单', '${ctx}/cms/materialDelivery/formIn?eventId=${materialDelivery.eventId}','800px', '700px', 'materialDeliveryContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 添加/修改入库单</a>
					</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="cms:materialDelivery:del">
						<a href="${ctx}/cms/materialDelivery/delete?eventId=${materialDelivery.eventId}" onclick="return confirmx('确认要删除该物料单吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>