<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>礼品卡管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="gift" action="${ctx}/cms/gift/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>礼品卡名：</span>
				<form:input path="giftName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">状态：</span>
				<form:select path="giftStatus"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('gift_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">创建时间：</span>
				<input id="createStateTime" name="createStateTime"  maxlength="50" class=" form-control" 
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="createEndTime"  name="createEndTime"  maxlength="50" class=" form-control" 
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/>	 
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%-- 
			<shiro:hasPermission name="cms:gift:add">
				<table:addRow url="${ctx}/cms/gift/form" title="礼品卡" width="800px" height="620px" target="giftContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:gift:edit">
			    <table:editRow url="${ctx}/cms/gift/form" id="contentTable"  title="礼品卡" width="800px" height="680px" target="giftContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			 --%>
			<shiro:hasPermission name="cms:gift:del">
				<table:delRow url="${ctx}/cms/gift/deleteAll" id="contentTable"></table:delRow>
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
				<th>礼品卡名</th>
				<th>寄送者</th>
				<th>接收者</th>
				<th>创建时间</th>
				<th>过期时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gift">
			<tr>
				<td> <input type="checkbox" id="${gift.giftId}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看礼品卡', '${ctx}/cms/gift/viewForm?id=${gift.giftId}','800px', '680px')">${gift.giftName}</a></td>
				<td>${gift.sender}</td> 
				<td>${gift.receiver}</td>
				<td><fmt:formatDate value='${gift.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td><fmt:formatDate value='${gift.expiryTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${fns:getDictLabel(gift.giftStatus,'gift_status','')}</td>
				<td>
					<shiro:hasPermission name="cms:gift:view">
						<a href="#" onclick="openDialogView('查看礼品卡', '${ctx}/cms/gift/viewForm?id=${gift.giftId}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<%-- <shiro:hasPermission name="cms:gift:edit">
						<a href="#" onclick="openDialog('修改礼品卡', '${ctx}/cms/gift/form?id=${gift.giftId}','800px', '700px', 'giftContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission> --%>
					<shiro:hasPermission name="cms:gift:del">
						<a href="${ctx}/cms/gift/delete?id=${gift.giftId}" onclick="return confirmx('确认要删除该礼品卡吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>