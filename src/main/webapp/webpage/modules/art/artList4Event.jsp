<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>作品查询</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#art-item-submit {
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
			$("#art-item-submit").click(function(){
				var artId = $('input:radio[name="art-item"]:checked').val();
				var artName = $('input:radio[name="art-item"]:checked').attr("data-artName");
				var artNote = $('input:radio[name="art-item"]:checked').attr("data-note");
	            if(artId == null || artName == null){
	                alert("请选择一幅作品!");
	                return false;
	            }
	            else{
	            	$("#art-autocomplete",window.opener.document).prevAll("span").remove();
	            	$("#art-autocomplete",window.opener.document).nextAll("label").remove();
	            	$("#art-autocomplete",window.opener.document).removeClass("error");
					$("#art-autocomplete",window.opener.document).val(artName);	//子窗口给父窗口元素赋值
					$("#artId",window.opener.document).val(artId);
					opener.fillTags(artNote) ;
					window.close();
	            }
			});
		})
	</script>
</head>
<body style="padding:10px;">
	<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" commandName="art" action="${ctx}/wms/art/artList4Event" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
				<th>作品图</th>
				<th>作品名</th>
				<th>类型</th>
				<th>难易等级</th>
				<th>免费</th>
				<th>作品标签</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="art">
			<tr>
				<td> <input type="radio" name="art-item" value="${art.id}" data-artName="${art.artName}" data-note="${art.note}" class="i-checks"></td>
				<td><img alt="${art.artName}" src="${art.bigImg}!80.40" width="80" height="40"></td>
				<td><a  href="#" onclick="openDialogView('查看作品', '${ctx}/wms/art/viewForm?id=${art.id}','800px', '680px')">${art.artName}</a></td>
				<td>${fns:getDictLabel(art.type,'art_type','')} </td> 
				<td>${art.easyLevel}</td>
				<td>${(art.isFree=="0")?"收费":"免费"}</td>
				<td>${art.note}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	
	<div class="row">
		<div class="col-sm-12">
			<div class="pull-center">
				<a id="art-item-submit" >确定</a>
			</div>
		</div>
	</div>
	
</body>
</html>



