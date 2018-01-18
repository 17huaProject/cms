<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>作品管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="art" action="${ctx}/wms/art/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>作品名：</span>
				<form:input path="artName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">类型：</span>
				<form:select path="type"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('art_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">免费：</span>
				<form:select path="isFree"  class="form-control required">
					<form:option value="" label="全部"/>
					<form:option value="0" label="收费"/>
					<form:option value="1" label="免费"/>
				 </form:select>
			<span class="querylistspan">难易等级：</span>
				<form:select path="easyLevelScope"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('art_easyLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="wms:art:add">
				<table:addRow url="${ctx}/wms/art/form" title="作品" width="800px" height="620px" target="artContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:art:edit">
			    <table:editRow url="${ctx}/wms/art/form" id="contentTable"  title="作品" width="800px" height="680px" target="artContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:art:del">
				<table:delRow url="${ctx}/wms/art/deleteAll" id="contentTable"></table:delRow>
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
				<th>作品图</th>
				<th>作品名</th>
				<th>类型</th>
				<th>难易等级</th>
				<th>免费</th>
				<th>作品标签</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="art">
			<tr>
				<td> <input type="checkbox" id="${art.id}" class="i-checks"></td>
				<td><img alt="${art.artName}" src="${art.bigImg}!80." width="80" height="40"></td>
				<td><a  href="#" onclick="openDialogView('查看作品', '${ctx}/wms/art/viewForm?id=${art.id}','800px', '680px')">${art.artName}</a></td>
				<td>${fns:getDictLabel(art.type,'art_type','')} </td> 
				<td>${art.easyLevel}</td>
				<td>${(art.isFree=="0")?"收费":"免费"}</td>
				<td>${art.note}</td>
				<td>
					<shiro:hasPermission name="wms:art:view">
						<a href="#" onclick="openDialogView('查看作品', '${ctx}/wms/art/viewForm?id=${art.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:art:edit">
						<a href="#" onclick="openDialog('修改作品', '${ctx}/wms/art/form?id=${art.id}','800px', '700px', 'artContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:art:del">
						<a href="${ctx}/wms/art/delete?id=${art.id}" onclick="return confirmx('确认要删除该作品吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<c:choose>
						<c:when test="${empty art.materialModelId}">
							<shiro:hasPermission name="cms:materialModel:add">
								<a href="#" onclick="openDialog('配置物料', '${ctx}/cms/materialModel/form?artId=${art.id}','800px', '700px', 'artContent')" class="btn btn-warning btn-xs" ><i class="fa fa-plus"></i>配置物料</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<shiro:hasPermission name="cms:materialModel:edit">
								<a href="#" onclick="openDialog('修改物料', '${ctx}/cms/materialModel/form?id=${art.materialModelId}','800px', '700px', 'artContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改物料</a>
							</shiro:hasPermission>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>