<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
	<title>多图上传</title>
	<style>
		.multi-images-upload .pblock{ display: inline-block; width:80px; float:left; margin-left:10px;}
		.multi-images-upload .tools{ display: inline-block; width:80px; text-align:center; }
		.multi-images-upload .tools i{display: inline-block;}
		.multi-images-upload .pblock img{ width:80px; height:80px; display: inline-block; margin-bottom:2px;}
		.multi-images-upload .delete-icon .arrow-right{ margin-left:5px;}
	</style>	
	<script type="text/javascript">
		$(document).ready(function() {
			
		
			//实例化编辑器ueditor
			var multiImagesUpload = UE.getEditor('multiImagesUpload', {
			    		toolbars: [['insertimage']],
			    		initialFrameWidth: 30,
			    		initialFrameHeight:0.01,
			    		elementPathEnabled:false,
			    		wordCount:false,
			    		autoHeightEnabled:false,
			    		scaleEnabled:false,
			    		minFrameWidth: 30,
			    		minFrameHeight: 30,
			    		focus:false
			       });
			//对编辑器的操作最好在编辑器ready之后再做
			multiImagesUpload.ready(function() {
				multiImagesUpload.addListener( 'contentChange', function( editor ) {
					var content = multiImagesUpload.getContent() ;
					content = content.replace(/<p><br\/><\/p>/g,"");
					content = content.replace(/<p>/g,"<div class='pblock' style='display: inline-block; width:80px; float:left; margin-left:10px;'>");
					content = content.replace(/<\/p>/g,"<span class='tools'>" +
										    	"<a href='#'><i class='fa fa-arrow-left' style='color:#3A00FF;'></i></a>" +
										        "<a href='#'><i class='fa fa-arrow-right arrow-right' style='margin-left: 10px;color:#3A00FF;'></i></a>" +
										        "<a href='#'><i class='fa fa-trash delete-icon'  style='margin-left: 10px;color:red;'></i></a>" +
										   "</span>" +
										"</div>");
					$("#multiImagesList").html(content);
					
					multiImagesUpload.execCommand('cleardoc'); //清空文档 
					
					$(".pblock .tools a").click(function(){
						var pblock = $(this).parentsUntil(".multi-images-upload")[1] ;
						if ($(this).find("i").hasClass("fa-arrow-left")) {	//前移
							$(pblock).insertBefore($(pblock).prev());
						} else if ($(this).find("i").hasClass("fa-arrow-right")) { //后移
							$(pblock).insertAfter($(pblock).next());
						} else if ($(this).find("i").hasClass("fa-trash")) { //删除
							$(pblock).remove();
						}
					});	
				 });
			});
		
			
		});
		
		    
	</script>
</head>
<body>
   	<tr>
         <td class="active"><label class="pull-right"><font color="red">*</font>多图上传:</label></td>
         <td colspan=3>
			<script id="multiImagesUpload" type="text/plain" style="width:100%;height:300px;margin-bottom: 8px; margin-left: 8px;" ></script>
			<div id="multiImagesList" class="multi-images-upload"></div>
		 </td>
    </tr>

</body>
</html>