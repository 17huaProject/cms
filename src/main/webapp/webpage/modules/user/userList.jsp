<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>基本信息</title>
	<meta name="decorator" content="default"/>
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="user" action="${ctx}/wms/user/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>姓名：</span>
				<form:input path="realname" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">昵称：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">身份证号：</span>
				<form:input path="idcard" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">顾客类型：</span>
				<form:select path="userType"  class="form-control">
					<form:option value="" label="全部"/>		
					<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="wms:user:del">
				<table:delRow url="${ctx}/wms/user/deleteAll" id="contentTable"></table:delRow>
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
				<th>姓名/昵称/微信名</th>
				<th>手机号</th>
				<!-- <th>身份证号</th> -->
				<th>余额</th>
				<th>积分</th>
				<th>类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.userId}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看客户信息', '${ctx}/wms/user/form?id=${user.userId}','800px', '680px')">
						${user.realname} / ${user.name} / ${user.nickname}
					</a>
				</td>
				<td>
					<shiro:hasPermission name="wms:user:phone">
					${user.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:phone">
					${fn:substring(user.phone,0,3)}*****${fn:substring(user.phone,8,11)}
					</shiro:lacksPermission>
				</td>
				<%-- <td>
					<shiro:hasPermission name="wms:user:idcard">
					${user.idcard}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:idcard">
					${fn:substring(user.idcard,0,3)}*****${fn:substring(user.idcard,fn:length(user.idcard)-4,fn:length(user.idcard))}
					</shiro:lacksPermission>
				</td> --%>
				<td>￥${user.balance}</td>
				<td>${user.score}</td>
				<td>${fns:getDictLabel(user.userType,'customer_type','')}</td> 
				<td>
					<shiro:hasPermission name="wms:user:view">
						<a href="#" onclick="openDialogView('查看客户信息', '${ctx}/wms/user/form?id=${user.userId}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:user:edit">
						<a href="#" onclick="openDialog('修改客户信息', '${ctx}/wms/user/editForm?id=${user.userId}','800px', '700px', 'userContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:user:del">
						<a href="${ctx}/wms/user/delete?id=${user.userId}" onclick="return confirmx('确认要删除该客户信息吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>