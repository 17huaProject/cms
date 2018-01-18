<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>作品管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//实例化编辑器ueditor
			var ue = UE.getEditor('content',{readonly:true});
			//对编辑器的操作最好在编辑器ready之后再做
			ue.ready(function() {});
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="art" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">作品名:</label></td>
			         <td><span class=" form-control">${art.artName}</span></td>
			         <td class="active"><label class="pull-right">难易等级:</label></td>
			         <td><span class=" form-control">${art.easyLevel}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">是否收费:</label></td>
			         <td><span class=" form-control">
			         	<c:if test="${art.isFree eq 0}">收费</c:if>
			         	<c:if test="${art.isFree eq 1}">免费</c:if>
			         </span></td>
			         <td class="active"><label class="pull-right">类型:</label></td>
			         <td><span class=" form-control">${fns:getDictLabel(art.type,'art_type','')}</span></td>
			    </tr>
			       
				  <tr>
			         <td class="active"><label class="pull-right">作品图:</label></td>
			         <td colspan="3">
			         	<img id="imageScanBig" src="${art.bigImg}" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/>
			         </td>
			      </tr> 		      
			      
			      <tr>
			         <td class="active"><label class="pull-right">作品标签:</label></td>
			         <td colspan=3><span class=" form-control">${art.note}</span></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">简介:</label></td>
			         <td colspan=3><span class=" form-control">${art.artDesc}</span></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">详情:</label></td>
			         <td colspan=3>
			         <%-- 
						<form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
						<sys:ckeditor replace="content" uploadPath="/art/content" />	
						 --%>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${art.content}</script>
						
					 </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><span class=" form-control">${art.remark}</span></td>
			      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>