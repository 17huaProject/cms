<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.radio-button-group{display:inline-block;}
		.radio-button-group input{margin-top:0px;width:auto;}
  		.radio-button-group label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		.radio-button-group input{margin-top:-4px;}
	</style>
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="good" action="${ctx}/cms/good/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>商品名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span  class="querylistspan">商品编号：</span>
				<form:input path="goodsNo" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span  class="querylistspan">类型：</span>
				 <form:select path="categoryId"  class="form-control">
					<option selected="selected" value="">请选择</option>
					<form:options items="${goodCategorys}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				 </form:select>
			<span  class="querylistspan">可售：</span>
				 <div class="radio-button-group">
					 <form:radiobutton path="goodsStatus" value=""/><label>全部</label>
					 <form:radiobutton path="goodsStatus" value="1"/><label>是</label>
		             <form:radiobutton path="goodsStatus" value="0"/><label>否</label>
		         </div>
		</div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="cms:good:add">
				<table:addRow url="${ctx}/cms/good/form" title="商品" width="800px" height="620px" target="goodContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:good:edit">
			    <table:editRow url="${ctx}/cms/good/form" id="contentTable"  title="商品" width="800px" height="680px" target="goodContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:good:del">
				<table:delRow url="${ctx}/cms/good/deleteAll" id="contentTable"></table:delRow>
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
				<th>商品编号</th>
				<th>商品名</th>
				<th>售价</th>
				<th>库存量</th>
				<th>可售</th>
				<th>类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="good">
			<tr>
				<td> <input type="checkbox" id="${good.id}" class="i-checks"></td>
				<td>${good.goodsNo}</td>
				<td><a  href="#" onclick="openDialogView('查看商品', '${ctx}/cms/good/viewForm?id=${good.id}','800px', '680px')">${good.name}</a></td>
				<td>￥${good.price}</td>
				<td> <fmt:formatNumber value="${good.count - good.saleCount - good.usedCount}" pattern="###" /></td>
				<td>
					<c:if test="${good.goodsStatus eq 1}">是</c:if>
					<c:if test="${good.goodsStatus eq 0}">否</c:if>
				</td>
				<td>${good.categoryName}</td>
				<td>
					<shiro:hasPermission name="cms:good:view">
						<a href="#" onclick="openDialogView('查看商品', '${ctx}/cms/good/viewForm?id=${good.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:good:edit">
						<a href="#" onclick="openDialog('修改商品', '${ctx}/cms/good/form?id=${good.id}','800px', '700px', 'goodContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:good:del">
						<a href="${ctx}/cms/good/delete?id=${good.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>