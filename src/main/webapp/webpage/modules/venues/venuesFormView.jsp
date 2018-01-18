<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>场所管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>	
	<script type="text/javascript">
	$(document).ready(function() {
		var ue = UE.getEditor('content',{readonly:true});
		ue.ready(function() {
		});
	});
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="venue" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">场所名称:</label></td>
			         <td><span class=" form-control">${venue.venueName}</span></td>
			         <td class="active"><label class="pull-right">场所类型:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(venue.type,'venue_type','')}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">场所图:</label></td>
			         <td colspan="3">
			         	<img id="imageScanBig" src="${venue.bigImg}" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/>
			         </td>
			      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">顾客容量:</label></td>
		         <td><span class=" form-control">${venue.capacity}</span></td>
		         <td class="active"><label class="pull-right">联系人:</label></td>
		         <td><span class=" form-control">${venue.contact}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">手机号:</label></td>
		         <td><span class=" form-control">
		         	<shiro:hasPermission name="wms:venue:phone">
					${venue.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:venue:phone">
					${fn:substring(venue.phone,0,3)}*****${fn:substring(venue.phone,8,11)}
					</shiro:lacksPermission>
		         </span></td>
		         <td class="active"><label class="pull-right">email:</label></td>
		         <td><span class=" form-control">${venue.email}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">详细地址:</label></td>
		         <td colspan="3"><span class=" form-control">${venue.address}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">简介:</label></td>
		         <td colspan=3><span class=" form-control">${venue.venueDesc}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">详情:</label></td>
		         <td colspan=3>
				<%-- 	<form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
					<sys:ckeditor replace="content" uploadPath="/venues/content" />	 --%>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${venue.content}</script>	
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan=3><span class=" form-control">${venue.remark}</span></td>
		      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>