<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>画师管理</title>
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
	<form:form id="searchForm" commandName="artist" action="${ctx}/wms/artist/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名字：</span>
				<form:input path="realname" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">性别：</span>
				<form:select path="gender"  class="form-control">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="男"/>
					<form:option value="2" label="女"/>
				 </form:select>
			<span class="querylistspan">身份证号：</span>
				<form:input path="idcard" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span class="querylistspan">城市：</span>
				 <form:select path="provinceCode"  class="form-control">
					<option selected="selected" value="">请选择省份</option>
					<form:options items="${fns:getProvinceList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
				 </form:select>
				 <form:select path="cityCode"  class="form-control">
					<option selected="selected" value="">请选择市</option>
					<option value=""></option>
				 </form:select>
			<%-- <span class="querylistspan">星级：</span>
				 <form:select path="artistLevelScope"  class="form-control">
					<option selected="selected" value="">请选择星级</option>
					<form:options items="${fns:getDictList('artist_commentLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select> --%>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="wms:artist:add">
				<table:addRow url="${ctx}/wms/artist/form" title="画师" width="800px" height="620px" target="artistContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:artist:edit">
			    <table:editRow url="${ctx}/wms/artist/form" id="contentTable"  title="画师" width="800px" height="680px" target="artistContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:artist:del">
				<table:delRow url="${ctx}/wms/artist/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="wms:artist:import">
				<table:importExcel url="${ctx}/wms/artist/import"></table:importExcel>
			</shiro:hasPermission>
			<shiro:hasPermission name="wms:artist:export">
	       		<table:exportExcel url="${ctx}/wms/artist/export"></table:exportExcel>
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
				<th>昵称</th>
				<th>真实姓名</th>
				<th>性别</th>
				<th>手机号</th>
				<th>城市</th>
				<th>星级</th>
				<th>审核</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="artist">
			<tr>
				<td> <input type="checkbox" id="${artist.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看画师', '${ctx}/wms/artist/viewForm?id=${artist.id}','800px', '680px')">${artist.artistName}</a></td>
				<td>${artist.realname}</td> 
				<td>${(artist.gender=="1")?"男":"女"}</td>
				<td>
					<shiro:hasPermission name="wms:artist:phone">
					${artist.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:artist:phone">
					${fn:substring(artist.phone,0,3)}*****${fn:substring(artist.phone,8,11)}
					</shiro:lacksPermission>
				</td>
				<td>${artist.city}</td>
				<td>${artist.artistLevel}</td>
				<td>${(artist.isCheck=="0") ? "未审核 ": "审核通过"}</td>
				<td>
					<shiro:hasPermission name="wms:artist:view">
						<a href="#" onclick="openDialogView('查看画师', '${ctx}/wms/artist/viewForm?id=${artist.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="wms:artist:edit">
						<a href="#" onclick="openDialog('修改画师', '${ctx}/wms/artist/form?id=${artist.id}','800px', '700px', 'artistContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<c:if test="${artist.isCheck =='0' }">
					<shiro:hasPermission name="wms:artist:audit">
						<a href="#" onclick="openDialogAudit('审核画师', '${ctx}/wms/artist/formAudit?id=${artist.id}','800px', '700px', 'artistContent')" class="btn btn-warning btn-xs" ><i class="fa fa-eye"></i> 审核</a>
					</shiro:hasPermission>
					</c:if>
					<shiro:hasPermission name="wms:artist:del">
						<a href="${ctx}/wms/artist/delete?id=${artist.id}" onclick="return confirmx('确认要删除该画师吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
</body>
</html>