<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//：城市
			$("#provinceCode").change(function(){
				var provinceCode = $(this).val();
				$("#address").val("");
				$("#address").attr("disabled","disabled");
				$.post("${ctx}/wms/region/city/list", { provinceCode: provinceCode},
				   function(data){
						var cityObj = jQuery.parseJSON(data);
				   		console.log(data);
				   		if(cityObj.code == 0){
					   		var cities = cityObj.data;
						   	$("#cityCode").empty();
					   		if(cities.length == 0){
					   			$("#cityCode").append("<option selected=\"selected\" value=\"\">无开通城市</option>");
					   		}else{
					   			//$("#cityCode").append("<option selected=\"selected\" value=\"\">请选择市</option>");
						   		for(var i=0; i<cities.length ;i++){
						   			$("#cityCode").append("<option value=\""+cities[i].code+"\">"+cities[i].name+"</option>");
						   		}
						   		$("#cityCode").removeAttr("disabled");
					   		}
				   		}else{
				   			console.log("城市查询出错！");
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
	<form:form id="searchForm" commandName="event" action="${ctx}/wms/event/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>活动名称：</span>
				<form:input path="eventName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">类型：</span>
				<form:select path="type"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('event_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">人数：</span>
				 <form:select path="capacityScope"  class="form-control">
					<option selected="selected" value="">请选择人数</option>
					<form:options items="${fns:getDictList('event_capacity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">状态：</span>
				 <form:select path="eventStatus"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('event_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">城市：</span>
				 <form:select path="provinceCode"  class="form-control">
					<option selected="selected" value="">请选择省份</option>
					<form:options items="${fns:getProvinceList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				 </form:select>
				 <form:select path="cityCode"  class="form-control">
					<option selected="selected" value="">请选择市</option>
					<option value=""></option>
				 </form:select>
		    <br/>
		    <div style="height:8px;"></div>
			<span>活动时间：</span>
				<input id="eventStateTime" name="eventStateTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value='${event.eventStateTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="eventEndTime"  name="eventEndTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${event.eventEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/>
			<span class="querylistspan">报名时间：</span>
				<input id="closingStateTime" name="closingStateTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${event.closingStateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
				至  
				<input id="closingEndTime" name="closingEndTime"  maxlength="50" class=" form-control" 
				value="<fmt:formatDate value="${event.closingEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
			<shiro:hasPermission name="wms:event:add">
				<table:addRow url="${ctx}/wms/event/form" title="活动" width="800px" height="620px" target="eventContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:event:edit">
			    <table:editRow url="${ctx}/wms/event/form" id="contentTable"  title="活动" width="800px" height="680px" target="eventContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:event:del">
				<table:delRow url="${ctx}/wms/event/deleteAll" id="contentTable"></table:delRow>
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
				<th>活动编号</th>
				<th>名称</th>
				<th>类型</th>
				<th>人数</th>
				<th>价格</th>
				<th>退款</th>
				<th>状态</th>
				<!-- <th>城市</th> -->
				<th>活动时间</th>
				<!-- <th>报名截止时间</th> -->
				<th>审核</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="event">
			<tr>
				<td> <input type="checkbox" id="${event.id}" class="i-checks"></td>
				<td>${event.id}</td>
				<td><a  href="#" onclick="openDialogView('查看活动', '${ctx}/wms/event/viewForm?id=${event.id}','800px', '680px')">${event.eventName}</a></td>
				<td>${fns:getDictLabel(event.type,'event_type','')}</td> 
				<td>${event.capacity}</td>
				<td>￥${event.price}</td>
				<td>${(event.isRefund==0)?"否":"是"}</td>
				<td>${fns:getDictLabel(event.eventStatus,'event_status','')}</td>
				<%-- <td>${event.cityCode}</td> --%>
				<td><fmt:formatDate value="${event.eventTime}" pattern="yyyy-MM-dd HH:mm"/></td> 
				<%-- <td><fmt:formatDate value="${event.closingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
				<td>${(event.isCheck=="0") ? "未审核 ": "通过"}</td>
				<td>
					<shiro:hasPermission name="wms:event:view">
						<a href="#" onclick="openDialogView('查看活动', '${ctx}/wms/event/viewForm?id=${event.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${event.eventStatus != 'FINISH'}">
					<shiro:hasPermission name="wms:event:edit">
						<a href="#" onclick="openDialog('修改活动', '${ctx}/wms/event/form?id=${event.id}','800px', '700px', 'eventContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					</c:if>
					<c:if test="${event.isCheck =='0' }">
					<shiro:hasPermission name="wms:event:audit">
						<a href="#" onclick="openDialogAudit('审核活动', '${ctx}/wms/event/formAudit?id=${event.id}','800px', '700px', 'eventContent')" class="btn btn-warning btn-xs" ><i class="fa fa-eye"></i> 审核</a>
					</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="wms:event:del">
						<a href="${ctx}/wms/event/delete?id=${event.id}" onclick="return confirmx('确认要删除该活动吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<c:if test="${event.isCheck eq '1'}">
						<shiro:hasPermission name="wms:event:copy">
							<a href="${ctx}/wms/event/copy?id=${event.id}" onclick="return confirmx('确认要复制该活动吗？', this.href)" class="btn btn-success btn-xs"><i class="fa fa-clone"></i> 复制</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${event.isCheck eq '1' and event.eventStatus !='PRESALE'}">
						<shiro:hasPermission name="wms:order:view">
							<a href="#" onclick="openDialogView('查看订单', '${ctx}/wms/order/listByEvent?eventId=${event.id}','1300px', '700px')"  class="btn btn-primary btn-xs"><i class="fa fa-ticket"></i>订单</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${event.eventStatus eq 'FINISH' and event.vunueSettleStatus != 1}">
						<shiro:hasPermission name="wms:commissionSettle:save">
							<a href="#" onclick="openDialogSettle('场所结算', '${ctx}/wms/settle/venueSettleForm?id=${event.id}','800px', '530px', 'eventContent')"  class="btn btn-primary btn-xs"><i class="fa fa-ticket"></i>场所结算</a>
						</shiro:hasPermission>
					</c:if>
				
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
	<div style="text-align: left;color: green;clear:both;">
		提醒：请将微信预览地址发送至微信中进行预览,活动保存后方可预览, 微信预览地址:${previewUrl} + 活动编号 
	</div>
</body>
</html>





