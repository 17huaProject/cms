<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>场所管理</title>
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
	<form:form id="searchForm" commandName="venue" action="${ctx}/wms/venue/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="venueName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">类型：</span>
				<form:select path="type"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('venue_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			<span class="querylistspan">人数：</span>
				 <form:select path="capacityScope"  class="form-control">
					<option selected="selected" value="">请选择人数</option>
					<form:options items="${fns:getDictList('venue_capacity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="wms:venues:add">
				<table:addRow url="${ctx}/wms/venue/form" title="场所" width="800px" height="620px" target="venuesContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:venues:edit">
			    <table:editRow url="${ctx}/wms/venue/form" id="contentTable"  title="场所" width="800px" height="680px" target="venuesContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:venues:del">
				<table:delRow url="${ctx}/wms/venue/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="wms:venues:import">
				<table:importExcel url="${ctx}/wms/venue/import"></table:importExcel>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:venues:export">
	       		<table:exportExcel url="${ctx}/wms/venue/export"></table:exportExcel>
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
				<th>场所名称</th>
				<th>场所类型</th>
				<th>顾客容量</th>
				<th>所属城市</th>
				<th>联系人</th>
				<th>手机号</th>
				<th>审核</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="venue">
			<tr>
				<td> <input type="checkbox" id="${venue.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看场所', '${ctx}/wms/venue/viewForm?id=${venue.id}','800px', '680px')">${venue.venueName}</a></td>
				<td>${(venue.type=="0") ? "私人定制 ": "普通"}</td> 
				<td>${venue.capacity}</td>
				<td>${venue.city}</td>
				<td>${venue.contact}</td>
				<td>
					<shiro:hasPermission name="wms:venue:phone">
					${venue.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:venue:phone">
					${fn:substring(venue.phone,0,3)}*****${fn:substring(venue.phone,8,11)}
					</shiro:lacksPermission>
				</td>
				<td>${(venue.isCheck=="0") ? "未审核 ": "审核通过"}</td>
				<td>
					<shiro:hasPermission name="wms:venues:view">
						<a href="#" onclick="openDialogView('查看场所', '${ctx}/wms/venue/viewForm?id=${venue.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:venues:edit">
						<a href="#" onclick="openDialog('修改场所', '${ctx}/wms/venue/form?id=${venue.id}','800px', '700px', 'venuesContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<c:if test="${venue.isCheck =='0' }">
					<shiro:hasPermission name="wms:venues:audit">
						<a href="#" onclick="openDialogAudit('审核场所', '${ctx}/wms/venue/formAudit?id=${venue.id}','800px', '700px', 'venuesContent')" class="btn btn-warning btn-xs" ><i class="fa fa-eye"></i> 审核</a>
					</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="wms:venues:del">
						<a href="${ctx}/wms/venue/delete?id=${venue.id}" onclick="return confirmx('确认要删除该场所吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:commissionSettle:view">
						<a href="#" onclick="openDialogView('查看佣金', '${ctx}/wms/settle/listSettlements4Venue?id=${venue.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 佣金</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>