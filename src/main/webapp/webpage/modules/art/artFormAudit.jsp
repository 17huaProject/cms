<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>作品管理</title>
	<meta name="decorator" content="default"/>
	
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
			$("#name").focus();
			$("#cityCode").attr("disabled" , "disabled");
			$("#address").attr("disabled" , "disabled");
			
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
			
			$("#cityCode").change(function(){
				if($.trim($(this).val()) == "") return ;
				var province = $("#provinceCode").find("option:selected").text() ;
				var city = $(this).find("option:selected").text() ;
				$("#city").val(city);
				$("#address").val(province+city);
				$("#address").removeAttr("disabled");
			});
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/art/audit" commandName="art" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>昵称:</label></td>
			         <td><form:input path="artistName" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>真实姓名:</label></td>
			         <td><form:input path="realname" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>出生日期:</label></td>
			          <td><form:input path="birthday" htmlEscape="false" maxlength="50" class=" form-control required" onFocus="WdatePicker()"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>性别:</label></td>
			         <td>
				         <form:select path="gender"  class="form-control">
							<form:option value="1" label="男"/>
							<form:option value="2" label="女"/>
						 </form:select>
					 </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>手机号:</label></td>
			         <td><form:input path="phone" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>email:</label></td>
			         <td><form:input path="email" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>学校:</label></td>
			         <td><form:input path="school" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>星级:</label></td>
			         <td><form:input path="commentLevel" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>身份证号:</label></td>
			         <td><form:input path="idcard" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>状态:</label></td>
			         <td>
				         <form:select path="status"  class="form-control">
							<form:option value="0" label="非公开"/>
							<form:option value="1" label="公开"  selected="selected"/>
						 </form:select>
					 </td>
			    </tr>
			       <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>画师头像:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<img id="imageScanBig" src="${artist.bigImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         	<img id="imageScanSmall" src="${artist.bigImg}" width="80" />
			         </td>
			      </tr> 
			      
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>详细地址:</label></td>
			         <td colspan="3">
						 <form:select path="provinceCode"  class=" required">
							<!-- <option selected="selected" value="">请选择省份</option> -->
							<form:options items="${fns:getProvinceList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
						 </form:select>
						 <form:hidden path="city"/>
						 <form:select path="cityCode"  class=" required">
							<option value="">请选择市</option>
							<form:options items="${cityInfo}"/>
						 </form:select>
						 <form:input path="address" htmlEscape="false" maxlength="80" class="required" style="width:50%"/>
					 </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
			         <td colspan=3>
			         	<form:textarea path="artistDesc" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/>
			        	<div style="color:green; width:100%;">
							注意：字数不超过50字
						</div>
			         </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
			         <td colspan=3>
						<%-- <form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
						<sys:ckeditor replace="content" uploadPath="/artist/content" />	 --%>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${artist.content}</script>
						<div style="color:green; width:100%;">注意：·图片&lt;2M, ·视频&lt;100MB, ·文件&lt;50MB</div>
					 </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><form:textarea path="remark" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
			      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>