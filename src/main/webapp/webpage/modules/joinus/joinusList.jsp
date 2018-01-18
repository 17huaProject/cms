<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>加入我们</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="joinus" action="${ctx}/cms/joinus/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>申请时间：</span>
				<input id="stateTime" name="startTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value='${joinus.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="endTime"  name="endTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${joinus.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/>
			
			<span class="querylistspan">状态：</span>
				<form:select path="status"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('joinus_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="wms:user:joinus:del">
				<table:delRow url="${ctx}/cms/joinus/deleteAll" id="contentTable"></table:delRow>
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
				<th>职位</th>
				<th>申请时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="joinus">
			<tr>
				<td> <input type="checkbox" id="${joinus.id}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看申请', '${ctx}/cms/joinus/formView?id=${joinus.id}','800px', '680px')">
						<c:choose>
							<c:when test="${fn:length(joinus.position)>10}">${fn:substring(joinus.position, 0, 10)} ······</c:when>
							<c:otherwise>${fn:substring(joinus.position, 0, 10)}</c:otherwise>
						</c:choose>
					</a>
				</td>
				<td><fmt:formatDate value='${joinus.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${fns:getDictLabel(joinus.status,'joinus_status','')} </td> 
				<td>
					<shiro:hasPermission name="cms:joinus:view">
						<a href="#" onclick="openDialogView('查看申请', '${ctx}/cms/joinus/formView?id=${joinus.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:joinus:edit">
						<c:if test="${joinus.status != 2}">
						<a href="#" onclick="openDialog('处理申请', '${ctx}/cms/joinus/form?id=${joinus.id}','800px', '700px', 'joinusContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 处理</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:joinus:del">
						<a href="${ctx}/cms/joinus/delete?id=${joinus.id}" onclick="return confirmx('确认要删除该申请吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>