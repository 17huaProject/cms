<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>类别管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="goodCategory" action="${ctx}/cms/goodCategory/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>类别名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="cms:goodCategory:add">
				<table:addRow url="${ctx}/cms/goodCategory/form" title="类别" width="800px" height="620px" target="goodCategoryContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:goodCategory:edit">
			    <table:editRow url="${ctx}/cms/goodCategory/form" id="contentTable"  title="类别" width="800px" height="680px" target="goodCategoryContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:goodCategory:del">
				<table:delRow url="${ctx}/cms/goodCategory/deleteAll" id="contentTable"></table:delRow>
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
				<th>类别名</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodCategory">
			<tr>
				<td> <input type="checkbox" id="${goodCategory.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看类别', '${ctx}/cms/goodCategory/viewForm?id=${goodCategory.id}','800px', '680px')">${goodCategory.name}</a></td>
				<td><fmt:formatDate value="${goodCategory.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<shiro:hasPermission name="cms:goodCategory:view">
						<a href="#" onclick="openDialogView('查看类别', '${ctx}/cms/goodCategory/viewForm?id=${goodCategory.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:goodCategory:edit">
						<a href="#" onclick="openDialog('修改类别', '${ctx}/cms/goodCategory/form?id=${goodCategory.id}','800px', '700px', 'goodCategoryContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:goodCategory:del">
						<a href="${ctx}/cms/goodCategory/delete?id=${goodCategory.id}" onclick="return confirmx('确认要删除该类别吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>