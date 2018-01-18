<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
	<title>作品管理</title>
	<meta name="decorator" content="default"/>
	<!-- 
	jQuery万能浮动框插件
	只能放在所需页面才能生效
	http://www.zhangxinxu.com/study/201012/jquery-power-float-demo.html
	 -->
	<style>
		.shadow{-moz-box-shadow:1px 1px 3px rgba(0,0,0,.4); -webkit-box-shadow:1px 1px 3px rgba(0,0,0,.4); box-shadow:1px 1px 3px rgba(0,0,0,.4);}
		.target_box{width:500px; padding:10px; border:1px solid #aaa; background-color:#fff;}
		.target_list{padding:4px; border-bottom:1px dotted #ddd; overflow:hidden; _zoom:1;}
		.color_star{color:red;}
	</style>	
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/jquery-powerFloat/jquery-powerFloat-min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-powerFloat/powerFloat.css" />
	<!-- jQuery万能浮动框插件-->
	
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
			
			//作品关键词筛选
			$("#note_tags").click( function(){ 
				var url = "${ctxStatic}/jquery-type-search/multiSelectTags.html";
				windowOpen(url,"作品关键词",705,610);
			});
		  
			$("#note").tagsInput({
			   'height':'auto',
			   'width':'auto',
			   'interactive':true,
			   'defaultText':'添加标签',
			   'unique':true,
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
			   'minChars' : 1,
			   //'maxChars' : 0, // if not provided there is no limit
			   'placeholderColor' : '#666666'
			});
			    
			  //图片上传
			  $("#selectImage").click( function(){ 
				  var url = "${ctxStatic}/cropper-master/imageCropper.html";
				  windowOpen(url,"文件管理",900,800);
				  
			  });
			//:~ 图片上传
			
			//实例化编辑器ueditor
			var ue = UE.getEditor('content');
			//对编辑器的操作最好在编辑器ready之后再做
			ue.ready(function() {});
			
			$("#easyLevelTips").powerFloat({
			    target: $("#targetBox"),
			    position: "7"
			});
			
		});
		
		//标签、关键字 自动填充，供子窗口调用
	    function fillTags(tags){
	    	$("input[name='note']").importTags(tags);
	    };
		
		    
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/art/save" commandName="art" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>作品名:</label></td>
			         <td>
			         	<form:input path="artName" htmlEscape="false" minlength="2" maxlength="15" class=" form-control  required"/>
			         	<div style="color:green; width:100%;">
							注意：字数限制为2-15字
						</div>
			         </td>
			         <td class="active">
			         	<label class="pull-right">
				         	<font color="red">*</font>难易等级:
				         	<i id="easyLevelTips" class="fa fa-question-circle color_star"  aria-hidden="true"></i>
			         	</label>
			         </td>
			         <td>
			         	<form:select path="easyLevel"  class="form-control required">
							<form:options items="${fns:getDictList('art_easy')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
			         	<%-- <form:input path="easyLevel" htmlEscape="false" maxlength="5" class=" form-control  required"/> --%>
			         </td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>是否收费:</label></td>
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
				  <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>作品图:</label></td>
			         <td colspan="3">
			         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
			         	<form:hidden path="bigImg" htmlEscape="false" maxlength="255" class="form-control"/>
			         	<img id="imageScanBig" src="${art.bigImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         	<img id="imageScanSmall" src="${art.bigImg}" width="80" />
			         </td>
			      </tr> 		      
			      <tr>
			         <td class="active"><label class="pull-right">作品标签:</label></td>
			         <td colspan=3>
			         	<div id="note_tags">
			         	<form:input path="note" htmlEscape="false" maxlength="100" class="form-control tags" data-value=""/>
			         	<!-- <span style="color:green; width:100%;">每个标签至少2个字,按空格键或enter键完成添加</span> -->
			         	</div>
			         </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
			         <td colspan=3>
			         	<form:textarea path="artDesc" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/>
			         	<div style="color:green; width:100%;">
							注意：字数不超过50字
						</div>
			         </td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
			         <td colspan=3>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${art.content}</script>
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
	
<div id="targetBox" class="shadow target_box dn">
	<div class="target_list">
		<span class="color_star">2.5</span>星：
		①了解绘画材料与绘画技法。
		②基本用笔方式。
		③如何调色，如颜色的干湿变化，了解黑白颜色对其他颜色的影响。
		④养成良好的绘画习惯，例如如何蘸取颜色。    	
    </div>
    <div class="target_list">
	    <span class="color_star">3</span>星：
	    ①熟悉绘画材料和绘画技法。
		②作品以简单的线条及色彩配合作画。
		③会简单构图。
		④色彩纯度较高，色块感强，可以调出简单的邻近色。 	
    </div>
    <div class="target_list">
    	<span class="color_star">3.5</span>星：
    	①熟悉绘画材料和绘画技法。
		②用流畅的线条表现形体结构，注意大致的比列和形象。
		③涉及一点透视。
		④简单使用绘画技法，如点彩，滴洒颜色。
		⑤部分细节刻画，有视觉中心。
    </div>
    <div class="target_list">
    	<span class="color_star">4</span>星：
    	①精通绘画材料和绘画技法。
		②用笔上，区分厚涂与薄涂画法。
		③了解构图。透视上运用近大远小，近实远虚的方法。
		④颜色过渡均匀，能简单运用二次色，三次色。
		⑤可以在视觉中心塑造细节。
    </div>
    <div class="target_list">
    	<span class="color_star">4.5</span>星：
    	①精通绘画材料和绘画技法。
		②会使用简单的用笔。如提，点，扫。
		③学会运用水平式构图、三角形构图的基础构图。特别的透视，画面的比例均衡。
		④运用冷暖色调，在同类色中找到层次感，比如柠檬黄，淡黄，中黄，土黄。
		⑤局部用颜色堆积，用厚涂法做出画面的机理效果。
		⑥在细节中塑造细节。
    </div>
    <div class="target_list">
    	<span class="color_star">5</span>星：
    	①探索绘画材料和绘画技法。
		②尝试用其他工具代替画笔。
		③熟练运用复杂的构图形式，如三七律构图，视点构图以及S形构图。
		④高级灰的使用，区分固有色，同类色，环境色。熟练适用三次色，四次色。
		⑤在多个细节中塑造出层次感，丰富画面的视觉中心。
		⑥画面富有寓意，使其更具感染力。
    </div>
    <div class="target_list" style="border-bottom:0;">
      	 说明：后面的星级内容是前面星级内容的叠加。
    </div>
</div>


</body>
</html>