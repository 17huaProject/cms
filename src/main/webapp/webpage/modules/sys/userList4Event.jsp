<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户查询</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#user-item-submit {
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
			$("#user-item-submit").click(function(){
				var userId = $('input:radio[name="user-item"]:checked').val();
				var userName = $('input:radio[name="user-item"]:checked').attr("data-userName");
	            if(userId == null || userName == null){
	                alert("请选择一幅作品!");
	                return false;
	            }
	            else{
	            	$("#assistant-autocomplete",window.opener.document).prevAll("span").remove();
	            	$("#assistant-autocomplete",window.opener.document).nextAll("label").remove();
	            	$("#assistant-autocomplete",window.opener.document).removeClass("error");
					$("#assistant-autocomplete",window.opener.document).val(userName);//子窗口给父窗口元素赋值
					$("#assistantId",window.opener.document).val(userId);//子窗口给父窗口元素赋值
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
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/userList4Event" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
			<span>登录名：</span>
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span>归属部门：</span>
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>姓&nbsp;&nbsp;&nbsp;名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
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
				<th>登录名</th>
				<th>姓名</th>
				<th>电话</th>
				<th>手机</th>
				<th>归属公司</th>
				<th >归属部门</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td><input type="radio" name="user-item" value="${user.id}" data-userName="${user.name}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	<div class="row">
		<div class="col-sm-12">
			<div class="pull-center">
				<a id="user-item-submit" >确定</a>
			</div>
		</div>
	</div>
</body>
</html>