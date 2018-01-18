<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新闻管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="article" action="${ctx}/wms/article/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>标题：</span>
				<form:input path="title" htmlEscape="false" maxlength="100" class=" form-control input-sm"/>
			<span class="querylistspan">作者：</span>
				<form:input path="author" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">类别：</span>
				<form:select path="catId"  class="form-control">
					<form:option value="" label="请选择"  selected="selected"/>
		         	<c:forEach items="${articleCats}" var="articleCat">
		         	<option value="${articleCat.catId }">${articleCat.catName }</option>
		         	</c:forEach>
	         	</form:select>
			<span class="querylistspan">打开方式：</span>
				 <form:select path="openType"  class="form-control">
					<form:option value="" label="请选择"  selected="selected"/>
					<form:options items="${fns:getDictList('article_openType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">状态：</span>
				 <form:select path="isOpen"  class="form-control" >
					<form:option value="" label="请选择"  selected="selected"/>
					<form:options items="${fns:getDictList('article_isOpen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="wms:article:add">
				<table:addRow url="${ctx}/wms/article/form" title="新闻" width="800px" height="620px" target="articleContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:article:edit">
			    <table:editRow url="${ctx}/wms/article/form" id="contentTable"  title="新闻" width="800px" height="680px" target="articleContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:article:del">
				<table:delRow url="${ctx}/wms/article/deleteAll" id="contentTable"></table:delRow>
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
				<th>作者</th>
				<th>打开方式</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td> <input type="checkbox" id="${article.articleId}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看新闻', '${ctx}/wms/article/viewForm?id=${article.articleId}','800px', '680px')">${article.title}</a></td>
				<td>${article.author}</td>
				<td>${fns:getDictLabel(article.openType,'article_openType','')} </td>
				<td>${fns:getDictLabel(article.isOpen,'article_isOpen','')} </td>
				<td>
					<shiro:hasPermission name="wms:article:view">
						<a href="#" onclick="openDialogView('查看新闻', '${ctx}/wms/article/viewForm?id=${article.articleId}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:article:edit">
						<a href="#" onclick="openDialog('修改新闻', '${ctx}/wms/article/form?id=${article.articleId}','800px', '700px', 'articleContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:article:del">
						<a href="${ctx}/wms/article/delete?id=${article.articleId}" onclick="return confirmx('确认要删除该新闻吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>