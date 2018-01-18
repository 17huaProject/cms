<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品类型管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	  	#cate_attrib span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#cate_attrib input{margin-top:0px;width:auto;}
  		#cate_attrib label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
	</style>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/goodCategory/save" commandName="goodCategory" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>类别名:</label></td>
			         <td colspan="3"><form:input path="name" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>属性:</label></td>
			         <td colspan="3" id="cate_attrib">
						 <form:checkboxes path="cateAttribs" items="${fns:getDictList('goodCategory_attr')}" itemLabel="label" itemValue="value" class=" form-control required"/>
					 </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>