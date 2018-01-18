<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>场所查询</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#venue-item-submit {
			display:block;
			width:600px; 
			height: 30px; 
			border-radius: 3px;
			padding: 1px 5px; 
			background-color: #2e8ded; 
			border-color: #4898d5;
			color: #fff;
			font-size: 18px; 
			text-align: center;
			margin:0 auto;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#venue-item-submit").click(function(){
				var venueId = $('input:radio[name="venue-item"]:checked').val();
				var venueName = $('input:radio[name="venue-item"]:checked').attr("data-venueName");
	            if(venueId == null || venueName == null){
	                alert("请选择一幅作品!");
	                return false;
	            }
	            else{
	            	$("#venue-autocomplete",window.opener.document).prevAll("span").remove();
	            	$("#venue-autocomplete",window.opener.document).nextAll("label").remove();
	            	$("#venue-autocomplete",window.opener.document).removeClass("error");
					$("#venue-autocomplete",window.opener.document).val(venueName);//子窗口给父窗口元素赋值
					$("#venueId",window.opener.document).val(venueId);//子窗口给父窗口元素赋值
					window.close();
	            }
			});
		})
	</script>
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
<body style="padding:10px;">
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="venue" action="${ctx}/wms/venue/venueList4Event" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
			<div class="pull-right" style="margin-left:50px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th></th>
				<th>场所名称</th>
				<th>场所类型</th>
				<th>顾客容量</th>
				<th>所属城市</th>
				<th>联系人</th>
				<th>手机号</th>
				<th>审核</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="venue">
			<tr>
				<td> <input type="radio" name="venue-item" value="${venue.id}" data-venueName="${venue.venueName}" class="i-checks"></td>
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
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	<div class="row">
		<div class="col-sm-12">
			<div class="pull-center">
				<a id="venue-item-submit" >确定</a>
			</div>
		</div>
	</div>
	
</body>
</html>