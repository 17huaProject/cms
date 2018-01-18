<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模板管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	  	#cate_attrib span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#cate_attrib input , #materialModelsSaleStatus input{margin-top:0px;width:auto;}
  		#cate_attrib label , #materialModelsSaleStatus label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		#materialModelsSaleStatus input{margin-top:-4px;}
  		#colorpickerdiv{display:inline-block; width:14px; height:14px; margin-right:5px;margin-bottom: -3px;}
  		.margin_left10{margin-left:10px;}
  		.material_title{display:inline-block; min-width:180px;font-weight: bold;}
	</style>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  
	         	var resultJson = "{" ;
	         	$("#materialList input[type!='button']").each(function(i,item){
	         		if ($.trim($(item).val()) != "") {
		         		resultJson += "\"" + $(item).attr("data-value") + "\":" + $(item).val() + "," ;
	         		}
	         	});
	         	var jsonLength = resultJson.length ;
	         	if (jsonLength > 1) {
	         		resultJson = resultJson.substr(0,resultJson.length-1);
	         	}
	         	resultJson += "}" ;
			  
			  $("#goodsInfo").val(resultJson);
			  
			  
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
			
			
			
			$("#goodCategory").change(function(){
				var goodCategoryId = $(this).val();
				$.post("${ctx}/cms/good/listGoodsByCategoryId", { categoryId: goodCategoryId},
				   function(data){
				   		if(data.code == 0){
				   			$("#goods").empty();
					   		var goods = data.data;
					   		if(goods.length == 0){
					   			$("#goods").append("<option selected=\"selected\" value=\"\">暂无商品</option>");
					   		}else{
					   			$("#goods").append("<option selected=\"selected\" value=\"\">请选择商品</option>");
						   		for(var i=0; i<goods.length ;i++){
						   			$("#goods").append(
						   					"<option value=\""+goods[i].id+"\" data-img=\""+goods[i].goodsImg+"\">"+ 
						   					 goods[i].goodsNo +" - "+ goods[i].name +
						   					"</option>");
						   		}
					   		}
				   		}else{
				   			console.log("商品查询出错！");
				   		}
			   });
			});
			
			var itemNum = 10000000;
			$("#goods").change(function(){
				if($.trim($(this).val()) == "") return ;
				var $optionSelected = $(this).find("option:selected") ;
				var goodInfo = $optionSelected.text() ;
				var goodImg = $optionSelected.attr("data-img") ;
				var goodId = $optionSelected.val() ;
				
				var htmlContent = 	"<div style='margin-bottom:10px;' id='item" + itemNum + "' >" + 
										"<span style='display:inline-block; min-width:180px;'>" + goodInfo + ": </span>" + 
										"<input type='text' data-value='" + goodId + "' placeholder='单位数量' >" +
										"<input type='button' value='删除' id='itemDelete" + itemNum + "' onclick='itemDelete("+itemNum+")' class='margin_left10'>" +
									"</div>";
				
				$("#materialList").append(htmlContent);
				itemNum	++ ;
			});
			
			//远程校验
			$("#art-autocomplete").focusout(function(){
				removeVerify("art");
			});
			$("#art-autocomplete").autocomplete({
				 source: function( request, response ) {
					 $.post("${ctx}/wms/art/list4AutoComplete", { searchParam: request.term},
					    function(data){
					   		if(data.code == 0){
					   			var arts = data.data ;
					   			if(arts != null){
					   				response( arts );	
					   			}else{
					   				
					   			}
					   		}
					   		if(data.code == 1101){
					   			console.log("参数不可为空");
					   		}
					  },"json");
				 },
				 minLength: 2,
				 focus: function( event, ui ) {
					return false;
				 },
			     select: function( event, ui ) {
					$( "#art-autocomplete" ).val( ui.item.artName );
			    	$( "#artId" ).val( ui.item.id );
			    	$( "#name" ).val( ui.item.artName + "-物料" );
			    	$(".ui-helper-hidden-accessible").text("");
			    	return false;
			     },
			}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
				  var searchParam = $("#art-autocomplete").val();
				  var searchReg = new RegExp(searchParam, 'g') ;
				  var valReplaced = "<span class='font-highlight'>"+searchParam+"</span>" ;
				  
				  return $( "<li>" ).append(  
			        	  	 "<a>"+ 
			        	  	 	"<img src='"+ item.bigImg +"!80.40' class='art-img'>"+
			        	  	 	"<div class='art-div-contain'>"+ 
				        	  	 	"<div>"+ 
				        	  	 		item.artName.replace(searchReg,valReplaced) +
				        	  	 	"</div>"+
				        	  	 	"<div>"+ 
			        					"类型：" + item.type + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
			        					"难度：" + item.easyLevel +
		        					"</div>"+
	        					"</div>"+
	        					" <div style='clear:both'></div>"+
			        		 "</a>"
							)
			        .appendTo( ul );
			    };
			    
			
		});
		function itemDelete(itemNum){
			$("#item"+ itemNum).remove();
		}
		  //远程校验
		function removeVerify(type){
			var id = $.trim( $( "#"+type+"Id" ).val() );
			var searchParam = $.trim( $("#"+type+"-autocomplete").val() );
			if (id == "" || searchParam == "") {
	   			addErrorHint(type) ;
	   			return ;
			}
			
			var requestUrl = "${ctx}/wms/"+type+"/verifyRemote4EventBind";
			
			$.post(requestUrl, {id:id , searchParam:searchParam },
			    function(data){
			   		if(data == false){
			   			$( "#"+type+"Id" ).val("");
			   			addErrorHint(type) ;
			   		} else {
			   			removeErrorHint(type) ;
			   		}
			  },"json");
		};
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/materialModel/save" commandName="materialModel" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="artId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			    <c:if test="${empty materialModel.artId}">
			   	<tr>
			         <td class="active"><label class="pull-right">作品:</label></td>
			         <td colspan="3">
			         	<input id="art-autocomplete" placeholder="请输入作品名" maxlength="50" class="form-control">
			         </td>
			    </tr>
			    </c:if>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>模板名:</label></td>
			         <td colspan="3">
				      <c:choose>
				      	<c:when test="${!empty materialModel.artId}">
				      		<form:input path="name" class=" form-control  required" value="${art.artName}-物料" disabled="disabled"/>
				      	</c:when>
				      	<c:otherwise><form:input path="name" class=" form-control  required" disabled="disabled" title="请先选择作品"/></c:otherwise>
				      </c:choose>
			         </td>
			    </tr>
			    <tr>
			         <td class="active">
			         	<label class="pull-right">物料列表:</label>
			         	<form:hidden path="goodsInfo" htmlEscape="false"  class="form-control" />
			         </td>
			         <td colspan=3>
			         	 <div style='margin-bottom:10px;'>
							<span class="material_title">物料名</span> 
							<span class="material_title">单位量</span> 
						 </div>
			         	 <div  id="materialList">
			         		<c:forEach items="${goods}" var="good">
					      		<div style='margin-bottom:10px;' id="item${good.id}" >
									<span style='display:inline-block; min-width:180px;'>${good.goodsNo} - ${good.name} </span> 
									<input type='text' data-value='${good.id}' placeholder='单位数量' value="${goodsInfoMap[fn:trim(good.id)]}">
									<input type='button' value='删除' id='itemDelete${good.id}' onclick='itemDelete(${good.id})' class='margin_left10'>
								</div>
			         		</c:forEach>
			         	 </div>
					     <div style="margin-top:15px;">
					     	 <span style='display:inline-block; min-width:60px;'>添加物料：</span> 
						     <select id="goodCategory"  class="">
						     	<option value="">请选择类别</option>
						     	<c:forEach items="${goodCategorys}" var="goodCategory">
						     		<option  value="${goodCategory.id}">${goodCategory.name}</option>
						     	 </c:forEach>
							 </select>
						     <select id="goods"  class="">
								<option value="">请选择商品</option>
							 </select>   
					     </div>  
					 </td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>