<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务核销管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
		
			$("#verifyButtons > a").click(function(){
				$(this).html("核销中").attr("href",'javascript:void(0)').attr("disabled",'true');
				var id = $(this).attr("data-id");
				var orderId = $(this).attr("data-orderId");
				$.post("${ctx}/wms/order/verify", { id: id, orderId : orderId},
				   function(data){
				   		if(data.code == 0){
					   		var id = data.data; 				
					   		$("#verifyButtons a[data-id='"+id+"']").html("核销成功").css("background-color",'black').css("border-color",'black');
					   		$("#ticket_"+id).html("<i class='fa fa-check text-primary'></i>");
				   		}else{
				   			$("#verifyButtons a[data-id='"+id+"']").html("核销").attr("href",'#').removeAttr("disabled");
				   			console.log("核销出错啦！");
				   		}
			   });
				
			});
			
		});
	</script>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="ticket" action="${ctx}/wms/order/listTickets" method="post" class="form-inline">
		<div class="form-group">
			<span class="querylistspan">活动：</span>
				<form:select path="eventId" items="${eventList}"  class="form-control"></form:select>  
			<%-- 	
			<span class="querylistspan">活动编号：</span>
				<form:input path="eventId" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			 --%>	
			<span class="querylistspan">票码：</span>
				<form:input path="ticketCode" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">手机号：</span>
				<form:input path="phone" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="wms:order:del">
				<table:verifyRow url="${ctx}/wms/order/verifyMulti" id="contentTable" label="核销"></table:verifyRow>
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
				<th>票码</th>
				<!-- <th>活动码</th> -->
				<th>活动名称</th>
				<!-- <th>票价</th> -->
				<th>活动时间</th>
				<th>姓名</th>
				<th>手机号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${tickets}" var="ticket">
			<tr>
				<td id="ticket_${ticket.id}"> 
					<c:if test="${ticket.usedFlag eq 0 }"><input type="checkbox" id="${ticket.id}" data-orderId="${ticket.orderId}" class="i-checks"></c:if>
					<c:if test="${ticket.usedFlag eq 1 }"><i class="fa fa-check text-primary"></i></c:if>
					<c:if test="${ticket.usedFlag eq 2 }"><i class="fa fa-ban text-danger"></i></c:if>
				</td>
				<td><span style="font-size: 16px;">${ticket.ticketCode}</span></td>
				<%-- <td>${ticket.eventId}</td> --%>
				<td>${ticket.eventName}</td>
				<%-- <td>￥${ticket.orderAmount}</td> --%>
				<td><fmt:formatDate value='${ticket.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>  
				<td>${ticket.name}</td>
				<td>${ticket.phone}</td>
				<td id="verifyButtons">
					<shiro:hasPermission name="wms:ticket:verify">
						<c:if test="${ticket.usedFlag eq 0 }">
							<a href="#" data-id="${ticket.id}" data-orderId="${ticket.orderId}"  class="btn btn-info btn-xs" >核销</a>
						</c:if>
						<c:if test="${ticket.usedFlag eq 1 }">
							<a href="javascript:void(0)" data-id="${ticket.id}"  class="btn btn-info btn-xs" style="background-color:black;border-color:black" disabled="true" >核销成功</a>
						</c:if>
						<c:if test="${ticket.usedFlag eq 2 }">
							<a href="javascript:void(0)" data-id="${ticket.id}"  class="btn btn-info btn-xs" style="background-color:red;border-color:red" disabled="true" >失效</a>
						</c:if>
						
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>















