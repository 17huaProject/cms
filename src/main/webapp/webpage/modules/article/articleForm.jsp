<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新闻管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#headerImg").val($("#imageScanBig").attr("src"));
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			$("#title").focus();
			
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
			
			$(".keywordsTags").tagsInput({
				   'height':'auto',
				   'width':'auto',
				   'interactive':true,
				   'defaultText':'添加标签',
				   'unique':true,
				   'delimiter': [',',';',' '],  
				   'removeWithBackspace' : true,
				   'minChars' : 2,
				   //'maxChars' : 0, // if not provided there is no limit
				   'placeholderColor' : '#666666',
				   //'onAddTag':onAddTag,
				   //'onRemoveTag':onRemoveTag,
	/* 			   onAdd: function(tag){
					   console.log(tag);
					   if ($('.keywordsTags').tagExist(tag)) return ;
				   },
	 */			   onChange: function(elem, elem_tags){
					   var keywords = "";
						$('#0_tagsinput .tag').each(function(){
							keywords = keywords + $.trim($(this).find("span").text()) + ',' ;
						});
						$(".keywordsTags").attr("type","hidden");
						$(".keywordsTags").removeAttr("display");
						$(".keywordsTags").val(keywords);
					}
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
	<form:form id="inputForm" action="${ctx}/wms/article/save" commandName="article" method="post" class="form-horizontal">
		<form:hidden path="articleId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>标题:</label></td>
			         <td  colspan=3><form:input path="title" htmlEscape="false" maxlength="100" class=" form-control  required"/></td>
			    </tr>
			   	<tr>
			        <td class="active"><label class="pull-right"><font color="red">*</font>作者:</label></td>
			        <td colspan=3>
			        	<form:input path="author" htmlEscape="false" maxlength="50" class=" form-control  required" value="一起画官网"/>
			        </td>
			    </tr>
			    <tr>
		      		 <td class="active"><label class="pull-right"><font color="red">*</font>关键字:</label></td>
			         <td colspan=3>
			         <input id="0" name="keywords" value="${article.keywords}" maxlength="50" class=" form-control keywordsTags required"/>
			         <span style="color:green; width:100%;">每个标签至少2个字,按空格键或enter键完成添加</span>
			         </td>
				</tr>
				<tr>
			         <td class="active"><label class="pull-right">头部展示图:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="headerImg" htmlEscape="false" class="form-control"/>
			         	<img id="imageScanBig" src="${article.headerImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>类别:</label></td>
			         <td>
			         	<form:select path="catId"  class="form-control required">
				         	<c:forEach items="${articleCats}" var="articleCat">
				         	<option value="${articleCat.catId }">${articleCat.catName }</option>
				         	</c:forEach>
			         	</form:select>
			         </td>
			         <td class="active"><label class="pull-right"><font color="red">*</font>状态:</label></td>
			         <td>
				         <form:select path="isOpen"  class="form-control required" >
				         	<form:options items="${fns:getDictList('article_isOpen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
					 </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>打开方式:</label></td>
			         <td colspan="3">
				         <form:select path="openType"  class="form-control required">
							<form:options items="${fns:getDictList('article_openType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
					 </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">外链:</label></td>
			         <td colspan=3><form:input path="link" htmlEscape="false" maxlength="100" class=" form-control"/></td>
			    </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
		         <td colspan=3>
		         	<form:textarea path="introduce" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/>
		         	<div style="color:green; width:100%;">
						注意：字数不超过50字
					</div>
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
		         <td colspan=3>
					<script id="content" name="content" type="text/plain" style="width:100%;height:300px;">${article.content}</script>
					<div style="color:green; width:100%;">
						注意：1、·图片&lt;2M, ·视频&lt;100MB, ·文件&lt;50MB &nbsp;&nbsp; 2、微信端图片设置：宽度：600, 高度勿设
					</div>
				 </td>
		      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>