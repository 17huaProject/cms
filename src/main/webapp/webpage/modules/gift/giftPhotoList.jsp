<%@page import="com.alibaba.druid.sql.visitor.functions.If"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>背景图管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="cms:giftPhoto:add">
				<table:addRow url="${ctx}/cms/giftPhoto/form" title="背景图" width="800px" height="620px" target="giftPhotoContent"></table:addRow>
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:giftPhoto:edit">
			    <table:editRow url="${ctx}/cms/giftPhoto/form" id="contentTable"  title="图片" width="800px" height="680px" target="giftPhotoContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:giftPhoto:del">
				<table:delRow url="${ctx}/cms/giftPhoto/deleteAll" id="contentTable"></table:delRow>
			</shiro:hasPermission>
		</div>
		<!-- <div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div> -->
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th>图片名</th>
				<th>图片</th>
				<th>排序值</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${giftPhotos}" var="giftPhoto">
			<tr>
				<td> <input type="checkbox" id="${giftPhoto.id}" class="i-checks"></td>
				<td>${giftPhoto.photoName}</td>
				<td><img id="imageScanBig" src="${giftPhoto.bigImg}" width="100"/></td> 
				<td>${giftPhoto.sort}</td>
				<td>
					<shiro:hasPermission name="cms:gift:edit">
						<a href="#" onclick="openDialog('修改图片', '${ctx}/cms/giftPhoto/form?id=${giftPhoto.id}','800px', '700px', 'giftPhotoContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:gift:del">
						<a href="${ctx}/cms/giftPhoto/delete?id=${giftPhoto.id}" onclick="return confirmx('确认要删除该图片吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>