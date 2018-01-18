<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	  	#cate_attrib span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#cate_attrib input , .radio-button-group input{margin-top:0px;width:auto;}
  		#cate_attrib label , .radio-button-group label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		.radio-button-group input{margin-top:-4px;}
  		#colorpickerdiv{display:inline-block; width:14px; height:14px; margin-right:5px;margin-bottom: -3px;}
	</style>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#goodsImg").val($("#imageScanBig").attr("src"));
			  
	         	var resultJson = "{" ;
	         	$("#goodCateAttribList input").each(function(i,item){
	         		if ($.trim($(item).val()) != "") {
		         		resultJson += "\"" + $(item).attr("data-value") + "\":\"" + $(item).val() + "\"" + "," ;
	         		}
	         	});
	         	var jsonLength = resultJson.length ;
	         	if (jsonLength > 1) {
	         		resultJson = resultJson.substr(0,resultJson.length-1);
	         	}
	         	resultJson += "}" ;
			  
			  $("#attribute").val(resultJson);
			  
			  
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
			
			$("#categoryId").change(function(){
				$("#goodCateAttribList").empty();
				var id = $.trim($(this).val()) ;
				if(id == "") return ;
				$.post("${ctx}/cms/goodCategory/getAttr", { id: id},
				    function(data){
				   		if(data.code == 0){
				   			var attrs = data.data ;
				   			if(attrs != null){
				   				var htmlContent = "<div style='margin-bottom:5px;'><span style='display:inline-block; min-width:80px;'>";
				   				var attrArray = jQuery.parseJSON(attrs);
				   				$.each(attrArray, function(i, n){
				   					if (i == 4) { //颜色
					   					htmlContent +=  "<div style='margin-bottom:5px;'><span style='display:inline-block; min-width:80px;'>" + n + "</span>" +
					   						": <input type='hidden' data-value='" + i + "' >" +
					   						"<div id='colorpickerdiv'></div>" +
				   							"<img style='cursor: pointer; color: rgb(153, 51, 51);' id='colorpicker' src='${ctxStatic}/jquery-colorpicker/colorpicker.png'></div>";
				   					}else{
					   					htmlContent += "<div style='margin-bottom:5px;'><span style='display:inline-block; min-width:80px;'>" + n + "</span>" + 
					   						": <input type='text' data-value='" + i + "'></div>";
				   					}
				   				});
				   				$("#goodCateAttribList").html(htmlContent);
				   				
				   				$("#colorpicker").colorpicker({
			   					    fillcolor:true,
			   					    success:function(o,color){
			   					    	$("input[data-value='4']").val(color);
			   					        $("#colorpickerdiv").css("background-color",color);
			   					    }
			   					});
				   			}
				   		}
				  },"json");

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
			ue.ready(function() {});
		
			$("#colorpicker").colorpicker({
			    fillcolor:true,
			    success:function(o,color){
			    	$("input[data-value='4']").val(color);
			        $("#colorpickerdiv").css("background-color",color);
			    }
			});
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/good/save" commandName="good" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>商品名:</label></td>
			         <td><form:input path="name" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>商品编号:</label></td>
			         <td><form:input path="goodsNo" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>进价:</label></td>
			         <td><form:input path="salePrice" htmlEscape="false" class=" form-control  required" number="ture"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>入库数量:</label></td>
			         <td><form:input path="count" htmlEscape="false" class=" form-control  required" number="ture"/></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">品牌名:</label></td>
			         <td><form:input path="brand" htmlEscape="false" maxlength="50" class=" form-control"/></td>
			         <td class="active"><label class="pull-right">品牌型号:</label></td>
			         <td><form:input path="modelNo" htmlEscape="false" maxlength="50" class=" form-control"/></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>是否可售:</label></td>
			         <td class="radio-button-group">
			         	<form:radiobutton path="goodsStatus" value="1" checked="checked"/><label>是</label>
	               		<form:radiobutton path="goodsStatus" value="0"/><label>否</label>
			         </td>
			         <td class="active"><label class="pull-right">是否展示:</label></td>
			         <td class="radio-button-group">
			         	<form:radiobutton path="display" value="1"  checked="checked"/><label>是</label>
	               		<form:radiobutton path="display" value="0"/><label>否</label>
	               		<div style="color:green; width:100%;">注意：是:将在商城中显示，否：不显示</div>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">售价:</label></td>
			         <td>
			         	<form:input path="price" htmlEscape="false" class=" form-control" number="ture"/>
			         	<div style="color:red; width:100%;">可售时,则必填</div>
			         </td>
			         <td class="active"><label class="pull-right">已售数量:</label></td>
			         <td><form:input path="saleCount" htmlEscape="false" class=" form-control" number="ture"/></td>
			    </tr>
				<tr>
			         <td class="active"><label class="pull-right">已使用量:</label></td>
			         <td colspan="3"><form:input path="usedCount" htmlEscape="false" class=" form-control" number="ture"/></td>
			    </tr>
				<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>商品图:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="goodsImg" htmlEscape="false" class="form-control required"/>
			         	<img id="imageScanBig" src="${good.goodsImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         </td>
			    </tr> 		      
			    <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>类型:</label></td>
			         <td colspan=3>
			         	<form:select path="categoryId"  class="form-control required">
			         		<option selected="selected" value="">请选择</option>
							<form:options items="${categorys}" itemLabel="name" itemValue="id" />
						</form:select>
					 </td>
			    </tr>
			    <tr>
			         <td class="active">
			         	<label class="pull-right">属性:</label>
			         	<form:hidden path="attribute" htmlEscape="false"  class="form-control"/>
			         </td>
			         <td colspan=3 id="goodCateAttribList">
			         	
						<c:forEach items="${attrMap}" var="cateAttrib">
							<div  style="margin-bottom:5px;">
						    <span style="display:inline-block; min-width:80px;">${fns:getDictLabel(cateAttrib.key,'goodCategory_attr','')} :</span> 
							<c:choose>
								<c:when test='${cateAttrib.key eq "4" }'>
						   			<input type='hidden' data-value='${cateAttrib.key}' >
						   			<div id='colorpickerdiv' style="background-color:${cateAttrib.value}"></div>
					   				<img style='cursor: pointer; color: rgb(153, 51, 51);' id='colorpicker' src='${ctxStatic}/jquery-colorpicker/colorpicker.png'>
								</c:when>
								<c:otherwise>
									<input type='text' data-value='${cateAttrib.key }' value="${cateAttrib.value}">
								</c:otherwise>
							</c:choose>
							</div>
				        </c:forEach> 
					 </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
			         <td colspan=3>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${good.content}</script>
						<div style="color:green; width:100%;">注意：·图片&lt;2M, ·视频&lt;100MB, ·文件&lt;50MB</div>
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