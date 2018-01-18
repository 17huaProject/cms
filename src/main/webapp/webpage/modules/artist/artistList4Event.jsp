<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>画师查询</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#artist-item-submit {
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
			$("#artist-item-submit").click(function(){
				var artistId = $('input:radio[name="artist-item"]:checked').val();
				var artistName = $('input:radio[name="artist-item"]:checked').attr("data-artistName");
	            if(artistId == null || artistName == null){
	                alert("请选择一幅作品!");
	                return false;
	            }
	            else{
	            	$("#artist-autocomplete",window.opener.document).prevAll("span").remove();
	            	$("#artist-autocomplete",window.opener.document).nextAll("label").remove();
	            	$("#artist-autocomplete",window.opener.document).removeClass("error");
					$("#artist-autocomplete",window.opener.document).val(artistName);//子窗口给父窗口元素赋值
					$("#artistId",window.opener.document).val(artistId);//子窗口给父窗口元素赋值
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
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="artist" action="${ctx}/wms/artist/artistList4Event" method="post" class="form-inline">
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
				<th>昵称</th>
				<th>真实姓名</th>
				<th>性别</th>
				<th>手机号</th>
				<th>城市</th>
				<th>星级</th>
				<th>审核</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="artist">
			<tr>
				<td>  <input type="radio" name="artist-item" value="${artist.id}" data-artistName="${artist.realname}" class="i-checks"></td>
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
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
		<div class="row">
		<div class="col-sm-12">
			<div class="pull-center">
				<a id="artist-item-submit" >确定</a>
			</div>
		</div>
	</div>
</body>
</html>