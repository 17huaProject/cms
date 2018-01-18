<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发票管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			
		});
	</script>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="invoice" action="${ctx}/cms/invoice/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		<%-- 	<span>客户：</span>
				<form:input path="userId"  placeholder="请输入姓名/手机号/身份证号" class="form-control " /> --%>
			<span>订单编号：</span>
				<form:input path="orderIds" class="form-control " />
			<span class="querylistspan">订单类型：</span>
				<form:select path="orderType"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('invoice_orderType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<div style="margin-top:10px;"></div>
			<span>开票抬头：</span>
				<form:input path="title" class="form-control " />
			<span class="querylistspan">发票类型：</span>
				<form:select path="invoiceType"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('invoice_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">发票实体：</span>
				 <form:select path="pattern"  class="form-control">
					<option selected="selected" value="">请选择</option>
					<form:options items="${fns:getDictList('invoice_pattern')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">状态：</span>
				 <form:select path="invoiceStatus"  class="form-control">
					<option selected="selected" value="">请选择</option>
					<form:options items="${fns:getDictList('invoice_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">提取方式：</span>
				 <form:select path="shippingMode"  class="form-control">
					<option selected="selected" value="">请选择</option>
					<form:options items="${fns:getDictList('invoice_shippingMode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="cms:invoice:add">
				<table:addRow url="${ctx}/cms/invoice/form" title="发票" width="800px" height="620px" target="invoiceContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:invoice:edit">
			    <table:editRow url="${ctx}/cms/invoice/form" id="contentTable"  title="发票" width="800px" height="680px" target="invoiceContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:invoice:del">
				<table:delRow url="${ctx}/cms/invoice/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
			<%-- 
			<shiro:hasPermission name="cms:invoice:import">
				<table:importExcel url="${ctx}/cms/invoice/import"></table:importExcel>
			</shiro:hasPermission>
			 --%>
			<%-- <shiro:hasPermission name="cms:invoice:export">
	       		<table:exportExcel url="${ctx}/cms/invoice/export"  id="contentTable"></table:exportExcel>
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
				<th>开票抬头</th>
				<th>发票类型</th>
				<th>发票实体</th>
				<th>提取方式</th>
				<th>订单编号</th>
				<th>订单类型</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="invoice">
			<tr>
				<td> <input type="checkbox" id="${invoice.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看发票', '${ctx}/cms/invoice/viewForm?id=${invoice.id}','800px', '680px')">${invoice.title}</a></td>
				<td>${fns:getDictLabel(invoice.invoiceType,'invoice_type','')}</td>
				<td>${fns:getDictLabel(invoice.pattern,'invoice_pattern','')}</td>
				<td>${fns:getDictLabel(invoice.shippingMode,'invoice_shippingMode','')}</td>
				<td>${invoice.orderIds}</td>
				<td>${fns:getDictLabel(invoice.orderType,'invoice_orderType','')}</td>
				<td>${fns:getDictLabel(invoice.invoiceStatus,'invoice_status','')}</td>
				<td>
					<shiro:hasPermission name="cms:invoice:view">
						<a href="#" onclick="openDialogView('查看发票', '${ctx}/cms/invoice/viewForm?id=${invoice.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:invoice:edit">
						<a href="#" onclick="openDialog('修改发票', '${ctx}/cms/invoice/form?id=${invoice.id}','800px', '700px', 'invoiceContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:invoice:del">
						<a href="${ctx}/cms/invoice/delete?id=${invoice.id}" onclick="return confirmx('确认要删除该发票吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:invoice:export">
						<a href="${ctx}/cms/invoice/export?ids=${invoice.id}"  class="btn btn-warning btn-xs"><i class="fa fa-file-excel-o"></i> 导出</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>