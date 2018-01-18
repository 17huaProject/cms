<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="order" action="${ctx}/wms/order/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span class="querylistspan">订单编号：</span>
				<form:input path="orderId" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">活动名称：</span>
				<form:input path="orderName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">状态：</span>
				<form:select path="status"  class="form-control">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span  class="querylistspan">创建时间：</span>
				<input id="eventStateTime" name="startTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value='${order.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="eventEndTime"  name="endTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<%-- <shiro:hasPermission name="wms:order:add">
				<table:addRow url="${ctx}/wms/order/form" title="订单" width="800px" height="620px" target="orderContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:order:edit">
			    <table:editRow url="${ctx}/wms/order/form" id="contentTable"  title="订单" width="800px" height="680px" target="orderContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="wms:order:del">
				<table:delRow url="${ctx}/wms/order/deleteAll" id="contentTable"></table:delRow>
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
				<th>订单编号</th>
				<th>活动名</th>
				<th>售价</th>
				<th>票数</th>
				<th>订单金额</th>
				<th>支付金额</th>
				<th>支付方式</th>
				<th>创建时间</th>
				<th>购票人</th>
				<th>手机号</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td> <input type="checkbox" id="${order.orderId}" class="i-checks"></td>
				<td><a href="#" onclick="openDialogView('查看订单', '${ctx}/wms/order/form?id=${order.orderId}','800px', '680px')">${order.orderId}</a></td>
				<td><a href="#" onclick="openDialogView('查看订单 ', '${ctx}/wms/order/listByEvent?eventId=${order.eventId}','1300px', '700px')">${order.orderName}</a></td>
				<td>￥${order.salePrice}</td>
				<td><fmt:formatNumber value="${order.number}" pattern="###"/></td>
				<td>￥${order.orderAmount}</td>
				<td>￥${order.balanceAmount + order.paidAmount + order.couponAmount}</td>
				<td>
			        <c:choose>
			        	<c:when test="${null != order.balanceAmount and order.balanceAmount gt 0}">账户扣款</c:when>
			        	<c:when test="${null != order.paidAmount and order.paidAmount gt 0}">微信支付</c:when>
			        	<c:when test="${null != order.couponAmount and order.couponAmount gt 0}">优惠券</c:when>
			        	<c:otherwise>其他</c:otherwise>
			        </c:choose>
		        </td>
				<td><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${order.realname}</td>
				<td>
					<shiro:hasPermission name="wms:order:phone">
					${order.usePhone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:order:phone">
					${fn:substring(order.usePhone,0,3)}*****${fn:substring(order.usePhone,8,11)}
					</shiro:lacksPermission>
				</td>
				<td>${fns:getDictLabel(order.status,'order_status','')} </td> 
				<td>
					<shiro:hasPermission name="wms:order:view">
						<a href="#" onclick="openDialogView('查看订单', '${ctx}/wms/order/form?id=${order.orderId}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
				<%-- 	<shiro:hasPermission name="wms:order:edit">
						<a href="#" onclick="openDialog('修改订单', '${ctx}/wms/order/form?id=${order.id}','800px', '700px', 'orderContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission> --%>
					<shiro:hasPermission name="wms:order:del">
						<a href="${ctx}/wms/order/delete?id=${order.orderId}" onclick="return confirmx('确认要删除该订单吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>