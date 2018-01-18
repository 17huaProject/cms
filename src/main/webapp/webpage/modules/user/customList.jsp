<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定制管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="custom" action="${ctx}/wms/custom/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">类型：</span>
				<form:select path="customType"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('custom_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">状态：</span>
				<form:select path="status"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('custom_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="wms:custom:del">
				<table:delRow url="${ctx}/wms/custom/deleteAll" id="contentTable"></table:delRow>
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
				<th>姓名</th>
				<th>手机号</th>
				<th>城市</th>
				<th>预计时间</th>
				<th>预计人数</th>
				<th>创建时间</th>
				<th>类型</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="custom">
			<tr>
				<td> <input type="checkbox" id="${custom.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看定制', '${ctx}/wms/custom/viewForm?id=${custom.id}','800px', '680px')">${custom.contact}</a></td>
				<td>
					<shiro:hasPermission name="wms:custom:phone">
					${custom.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:custom:phone">
					${fn:substring(custom.phone,0,3)}*****${fn:substring(custom.phone,8,11)}
					</shiro:lacksPermission>
				</td>
				<td>${custom.city}</td>
				<td><fmt:formatDate value='${custom.estDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${custom.estNum}</td>
				<td><fmt:formatDate value='${custom.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${fns:getDictLabel(custom.customType,'custom_type','')} </td> 
				<td>${fns:getDictLabel(custom.status,'custom_status','')} </td> 
				<td>
					<shiro:hasPermission name="wms:custom:view">
						<a href="#" onclick="openDialogView('查看定制', '${ctx}/wms/custom/viewForm?id=${custom.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${custom.status eq 0}">
					<shiro:hasPermission name="wms:custom:edit">
						<a href="#" onclick="openDialog('处理定制', '${ctx}/wms/custom/form?id=${custom.id}','800px', '700px', 'customContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 处理</a>
					</shiro:hasPermission>
					</c:if>
					<c:if test="${custom.status eq 1 and custom.eventId eq 0}">
					<shiro:hasPermission name="wms:event:add">
						<a href="#" onclick="openDialog('添加活动', '${ctx}/wms/event/form?customId=${custom.id}','800px', '700px', 'customContent')" class="btn btn-success btn-xs" ><i class="fa fa-plus"></i> 添加活动</a>
					</shiro:hasPermission>
					</c:if>
					<c:if test="${custom.eventId ne 0}">
					<shiro:hasPermission name="wms:event:edit">
						<a href="#" onclick="openDialog('修改活动', '${ctx}/wms/event/form?id=${custom.eventId}','800px', '700px', 'customContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改活动</a>
					</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="wms:custom:del">
						<a href="${ctx}/wms/custom/delete?id=${custom.id}" onclick="return confirmx('确认要删除该定制吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>