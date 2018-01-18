<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>礼品卡管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#bigImg").val($("#imageScanBig").attr("src"));
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
			
			
			  //图片上传
			  $("#selectImage").click( function(){ 
				  var url = "${ctxStatic}/cropper-master/imageCropper.html";
				  windowOpen(url,"文件管理",1100,600);
				  
			  });
			//:~ 图片上传
			
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/giftPhoto/save" commandName="giftPhoto" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			  <tr>
			      <td class="active"><label class="pull-right"><font color="red">*</font>图片名:</label></td>
			      <td colspan="3"><form:input path="photoName" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			  </tr>
			       
			  <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>背景图:</label></td>
		         <td colspan="3">
		         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
		         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
		         	<img id="imageScanBig" src="${giftPhoto.bigImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
		         	<img id="imageScanSmall" src="${giftPhoto.bigImg}" width="80" />
		         </td>
		      </tr> 		      
			  <tr>
		         <td class="active"><label class="pull-right">状态:</label></td>
		         <td colspan="3">
			         <form:select path="status"  class="form-control">
						<form:option value="1" label="有效"/>
						<form:option value="0" label="无效"/>
					 </form:select>
				 </td>
			 </tr>    
			 <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>排序号:</label></td>
		         <td colspan="3"><form:input path="sort" htmlEscape="false" class=" form-control  required"/></td>
		      </tr>     
			      
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>