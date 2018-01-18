<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>场所管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  var longitude = $.trim($("#longitude").val());
			  var latitude  = $.trim($("#latitude").val());
			  if (longitude == "" || latitude == "") {
				  alert("地址不够详细,请继续添加");
				  $("#address").focus();
				  return false;
			  }
			  var imageScanBig =  $.trim($("#imageScanBig").attr("src")) ;
			  if (imageScanBig == "") {
				  alert("请添加场所图");
				  return false;
			  }
			  $("#bigImg").val(imageScanBig);
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
			
			$.post("${ctx}/wms/region/cityAndDistrict/list", { provinceCode: '31'},
			   function(data){
					var cityObj = jQuery.parseJSON(data);
			   		if(cityObj.code == 0){
				   		var cities = cityObj.data;
					   	$("#cityCode").empty();
				   		if(cities.length == 0){
				   			$("#cityCode").append("<option selected=\"selected\" value=\"\">无开通城市</option>");
				   		}else{
				   			$("#cityCode").append("<option selected=\"selected\" value=\"\">请选择市\/区</option>");
					   		for(var i=0; i<cities.length ;i++){
					   			$("#cityCode").append("<option value=\""+cities[i].code+"\">"+cities[i].name+"</option>");
					   		}
					   		$("#cityCode").removeAttr("disabled");
				   		}
			   		}else{
			   			console.log("城市查询出错！");
			   		}
			});
			
			$("#provinceCode").change(function(){
				var provinceCode = $(this).val();
				$("#address").val("");
				$("#address").attr("disabled","disabled");
				$.post("${ctx}/wms/region/cityAndDistrict/list", { provinceCode: provinceCode},
				   function(data){
						var cityObj = jQuery.parseJSON(data);
				   		if(cityObj.code == 0){
					   		var cities = cityObj.data;
						   	$("#cityCode").empty();
					   		if(cities.length == 0){
					   			$("#cityCode").append("<option selected=\"selected\" value=\"\">无开通城市</option>");
					   		}else{
					   			$("#cityCode").append("<option selected=\"selected\" value=\"\">请选择市\/区</option>");
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
			
			$("#address").focusout(function(){
				var province = $("#provinceCode").find("option:selected").text() ;
				var city = $("#cityCode").find("option:selected").text() ;
				var address = province + city + $(this).val();
				/* http://lbs.amap.com/api/webservice/guide/api/georegeo */
				$.post("http://restapi.amap.com/v3/geocode/geo", 
				   { key: "eb42bb9bae52df0d8be547ffa30016dd", address: address, city:city},
				   function(data){
					   console.log(data);
					   var location = data.geocodes[0].location ;
					   console.log(location);
					   var lngLat = location.split(",");
						$("#longitude").val(lngLat[0]);
						$("#latitude").val(lngLat[1]);
			   });
				//$("#address").val( address );
			});

			  //图片上传
			  $("#selectImage").click( function(){ 
				  var url = "${ctxStatic}/cropper-master/imageCropper.html";
				  windowOpen(url,"文件管理",1100,600);
				  
			  });
				//:~ 图片上传
				//实例化编辑器ueditor
				var ue = UE.getEditor('content');
				//对编辑器的操作最好在编辑器ready之后再做
				ue.ready(function() {
				    //设置编辑器的内容
				   // ue.setContent("");
			  /*   uParse('#content', {
				    rootPath: "${ctxStatic}/ueditor143/"
				}) */
				});
			
			
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/venue/save" commandName="venue" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>场所名称:</label></td>
			         <td><form:input path="venueName" htmlEscape="false" minlength="2" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>场所类型:</label></td>
			         <td>
				         <form:select path="type"  class="form-control required">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('venue_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
					 </td>
			      </tr>
			   	<%-- <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>场所大图:</label></td>
			         <td>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<sys:ckfinder input="bigImg" type="images" uploadPath="/venues/bigImg" selectMultiple="false"/>
			         </td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>场所小图:</label></td>
			         <td>
			         	<form:hidden path="smallImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<sys:ckfinder input="smallImg" type="images" uploadPath="/venues/smallImg" selectMultiple="false"/>
			         </td>
			      </tr>
			        --%>
			     <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>场所图:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<img id="imageScanBig" src="${venue.bigImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         	<img id="imageScanSmall" src="${venue.bigImg}" width="80" />
			         </td>
			      </tr> 
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>顾客容量:</label></td>
		         <td><form:input path="capacity" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>联系人:</label></td>
		         <td><form:input path="contact" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>手机号:</label></td>
		         <td><form:input path="phone" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		         <td class="active"><label class="pull-right">email:</label></td>
		         <td><form:input path="email" htmlEscape="false" maxlength="50" class=" form-control"/></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>详细地址:</label></td>
		         <td colspan="3">
					 <form:select path="provinceCode"  class=" required">
						<option value="31" selected="selected">上海市</option>
						<form:options items="${fns:getProvinceList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					 </form:select>
					 <form:hidden path="city"/>
					 <form:select path="cityCode"  class="required">
						<option value="">请选择市</option>
						<form:options items="${cityInfo}"/>
					 </form:select>
					 <form:input path="address" maxlength="80" class="required" style="width:50%"/>
					 <%-- <form:hidden path="address"/>	 --%>	
					 <form:hidden path="latitude"/>		
					 <form:hidden path="longitude"/>		
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
		         <td colspan=3>
		         	<form:textarea path="venueDesc" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/>
		         	<div style="color:green; width:100%;">
						注意：字数不超过50字
					</div>	
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
		         <td colspan=3>
					<%-- <form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
					<sys:ckeditor replace="content" uploadPath="/venues/content" />	
					  --%>
					<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${venue.content}</script>					 
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