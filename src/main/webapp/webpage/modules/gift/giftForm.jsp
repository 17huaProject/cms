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
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			$("#artName").focus();
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
			
			$("#note").tagsInput({
			   'height':'auto',
			   'width':'auto',
			   'interactive':true,
			   'defaultText':'添加标签',
			   //'onAddTag':onAddTag,
			   //'onRemoveTag':onRemoveTag,
			   onChange: function(elem, elem_tags){
				   var note = "";
					$('#note_tagsinput .tag').each(function(){
						note = note + $.trim($(this).find("span").text()) + ',' ;
					});
					$("#note").attr("type","hidden").removeAttr("display");
					$("#note").val(note);
				},
			   'delimiter': [',',';',' '],   
			   'removeWithBackspace' : true,
			   'minChars' : 2,
			   //'maxChars' : 0, // if not provided there is no limit
			   'placeholderColor' : '#666666'
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
	<form:form id="inputForm" action="${ctx}/wms/art/save" commandName="art" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>礼品卡名:</label></td>
			         <td><form:input path="artName" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>难易等级:</label></td>
			         <td><form:input path="easyLevel" htmlEscape="false" maxlength="5" class=" form-control  required"/></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>免费:</label></td>
			         <td>
						<form:select path="isFree"  class="form-control required">
							<form:option value="0" label="收费"/>
							<form:option value="1" label="免费"/>
						 </form:select>
					 </td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>类型:</label></td>
			         <td>
				         <form:select path="type"  class="form-control">
							<form:options items="${fns:getDictList('art_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
					 </td>
			    </tr>
			   <%--  
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>礼品卡大图:</label></td>
			         <td>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<sys:ckfinder input="bigImg" type="images" uploadPath="/art/bigImg" selectMultiple="false"/>
			         </td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>礼品卡小图:</label></td>
			         <td>
			         	<form:hidden path="smallImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<sys:ckfinder input="smallImg" type="images" uploadPath="/art/smallImg" selectMultiple="false"/>
			         </td>
			      </tr>
			       --%>
			       
				  <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>礼品卡图:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<img id="imageScanBig" src="${art.bigImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         	<img id="imageScanSmall" src="${art.bigImg}" width="80" />
			         </td>
			      </tr> 		      
			      
			      
			      
			      <tr>
			         <td class="active"><label class="pull-right">礼品卡标签:</label></td>
			         <td colspan=3>
			         	<form:input path="note" htmlEscape="false" maxlength="100" class="form-control tags"/>
			         	<span style="color:green; width:100%;">每个标签至少2个字,按空格键或enter键完成添加</span>
			         </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
			         <td colspan=3><form:textarea path="artDesc" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
			         <td colspan=3>
			         <%-- 
						<form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
						<sys:ckeditor replace="content" uploadPath="/art/content" />	
						 --%>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >
							  ${art.content}
						</script>
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