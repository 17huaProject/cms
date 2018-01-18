<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>物料单管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	  	#cate_attrib span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#cate_attrib input , #materialModelsSaleStatus input{margin-top:0px;width:auto;}
  		#cate_attrib label , #materialModelsSaleStatus label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		#materialModelsSaleStatus input{margin-top:-4px;}
  		#colorpickerdiv{display:inline-block; width:14px; height:14px; margin-right:5px;margin-bottom: -3px;}
  		.margin_left10{margin-left:10px;}
  		.material_title{display:inline-block; min-width:180px;font-weight: bold;}
  		.material_item{display:inline-block; min-width:180px;}
	</style>

</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/materialDelivery/save" commandName="materialDeliveryDTO" method="post" class="form-horizontal">
		<form:hidden path="eventId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">物料单名:</label></td>
			         <td colspan="3" id="material-delivery-title">
			         	<c:if test="${ materialDeliveryDTO.eventId ne null and materialDeliveryDTO.eventId gt 0  }">
			         		物料单：${event.eventName} - <fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
			         	</c:if>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>活动:</label></td>
			         <td> 
			         	<c:choose>
			         		<c:when test="${ materialDeliveryDTO.eventId ne null and materialDeliveryDTO.eventId gt 0  }">
			         		${event.eventName}
			         		</c:when>
			         		<c:otherwise>
					         	<select id="event-list"  class=" required">
							     	<option value="">请选择活动</option>
					         		<c:forEach items="${events}" var="event"> 
								     	<option value="${event.id}">
								     		${event.eventName} - <fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
								     	</option>  
					         		</c:forEach>
								</select>
			         		</c:otherwise>
			         	</c:choose>
			         </td>
			         <td class="active"><label class="pull-right">模板:</label></td>
			         <td> 
			         	<form:hidden path="modelId"  class=" required"/>
			         	<span id="modelName"  class=" required"></span>
			         	<%-- 
			         	<select id="model-list"  class="">
					     	<option value="">请选择模板</option>
			         		<c:forEach items="${materialModels}" var="materialModel">
						     	<option value="${materialModel.id}">${materialModel.name}</option>  
			         		</c:forEach>
						</select>
						 --%>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">人数:</label></td>
			         <td><input type='text' id="customer-num" value="${event.capacity }"> </td>
			         <td class="active"><label class="pull-right">附加:</label></td>
			         <td><input type='text' id="append-percentage"> % </td>
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
							<span class="material_title">出库量</span> 
						 </div>
			         	 <div  id="materialList">
			         
			         		<c:forEach items="${materialDeliverys}" var="materialDelivery">
					      		<div style='margin-bottom:10px;' id="item${materialDelivery.id}" >
									<span class="material_item">${materialDelivery.goodsNo} - ${materialDelivery.goodName} </span> 
									<span class="material_item" id="material_unit_${materialDelivery.goodId}"></span>
									<input type='text' id="material_outnum_${materialDelivery.goodId}" 
											data-type='out-num' 
											data-id='${materialDelivery.id}' 
											data-goodId='${materialDelivery.goodId}' 
											data-flag='3' 
											placeholder='出库量' 
											data-unitNum="" 
											value="${materialDelivery.outCount}">
									<input type='button' value='删除' id='itemDelete${materialDelivery.id}' onclick='itemDelete(${materialDelivery.id})' class='margin_left10'>
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

	<div style="color: green; padding-left:10px;">
		提醒：可选的活动条件：活动状态为销售中或售完，活动开始前24小时内，且未创建物料单的活动
	</div>

	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  
	         	var resultJson = "" ;
	         	$("#materialList input[data-type='out-num']").each(function(i,item){
	         		var outNum = $.trim($(item).val()) ;
	         		//丢弃空值
	         		if (outNum != '') {
		         		resultJson += $(item).attr("data-id") + ":" 
		         					+ $(item).attr("data-goodId") + ":" 
		         					+ outNum + ":" 
		         					+ $(item).attr("data-flag") 
		         					+ "," ;
	         		}
	         	});
	         	var jsonLength = resultJson.length ;
	         	if (jsonLength > 1) {
	         		resultJson = resultJson.substr(0,resultJson.length-1);
	         	}
			  
			  $("#goodsInfo").val(resultJson);
			  
			  if ( $("#eventId").val() == "" ) {
				  $("#eventId").val($("#event-list").val());
			  }
			  if ( $("#modelId").val() == "" || $("#modelId").val() == '0' ) {
				 // $("#modelId").val($("#model-list").val());
				  alert($("#modelName").html());
				  return ;
			  }
			  
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
			
			
			//:修改：获取商品单位量
			var modelId = ${materialDeliveryDTO.modelId} ;
			if( parseInt( modelId ) > 0 ){
				$.post("${ctx}/cms/materialModel/getMaterialModelInfo", { id: modelId},
				   function(data){
				   		if(data.code == 0){
					   		var goods = data.data;
					   		
					   		var goodArray = jQuery.parseJSON(goods);
					   		var good ;
							for(var i =0 ; i < goodArray.length ; i++){
								good = goodArray[i] ;
								
								$("#material_unit_"+good.id).html(good.unitNum);
								$("#material_outnum_"+good.id).attr("data-unitNum" , good.unitNum);
								
							}
				   		}else{
				   			console.log("查询出错！");
				   		}
			   });
			}
			
			//:~
			
			
			
			
			$("#event-list").change(function(){
				var $evnetInfo = $(this).find("option:selected");
				var eventId = $("#event-list").val() ;
				$("#material-delivery-title").html("物料单：" + $evnetInfo.text());
				$.post("${ctx}/cms/materialDelivery/genBaseInfo", { eventId: eventId},
				   function(data){
				   		if(data.code == 0){
				   			var baseInfo = data.data ;
							$("#modelName").html(baseInfo.materialModelName);
				   			$("#modelId").val(baseInfo.materialModelId);
				   			//根据物料模板生成物料列表
				   			genMaterialList(baseInfo.materialJson);
				   			
				   			//: 出库数量计算
				   			$("#customer-num").val(baseInfo.capacity);
				   			setOutNum(baseInfo.capacity) ;
				   			//:~ 出库数量计算
				   		}else{
				   			if (data.code == 3001) {
				   				$("#modelName").html(data.message);
				   				$("#modelName").css("color","red");
				   			}else{
				   				alert(data.message);
				   			}
				   		}
			   });
				
				
				
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
			
			$("#model-list").change(function(){
				var id = $(this).val();
				$.post("${ctx}/cms/materialModel/getMaterialModelInfo", { id: id},
				   function(data){
				   		if(data.code == 0){
				   			$("#materialList").empty();
					   		var goods = data.data;
					   		//根据物料模板生成物料列表
				   			genMaterialList(goods);
				   		}else{
				   			console.log("查询出错！");
				   		}
			   });
			});
			
			$("#customer-num").keyup(function(){
				var customerNumber = $.trim( $(this).val() );
				//: 出库数量计算
	   			setOutNum(customerNumber) ;
			});
			
			//增加比例
			$("#append-percentage").keyup(function(){
				var percentage = $.trim( $(this).val() );
				var customerNumber = $.trim( $("#customer-num").val() ) ;
			
				$("#materialList input[data-type='out-num']").each(function(i,item){
					var sum ;
					if(percentage == "0" || percentage == ""){
						sum = parseInt($(item).attr("data-unitNum")) * customerNumber ;
					}else{
		         		sum = Math.round(parseInt($(item).val()) * (1 + percentage/100)) ;
					}
		         	$(item).val(sum) ;
					
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
										"<span class='material_item'>" + goodInfo + ": </span>" + 
										"<span class='material_item'></span>" + 
										"<input type='text' data-type='out-num'  data-flag='2' data-id='' data-goodId='" + goodId + "' placeholder='出库量' >" +
										"<input type='button' value='删除' id='itemDelete" + itemNum + "' onclick='itemDelete("+itemNum+")' class='margin_left10'>" +
									"</div>";
				
				$("#materialList").append(htmlContent);
				itemNum	++ ;
			});
			
			
			
			
		});
		function itemDelete(itemNum){
			var flag = $("#itemDelete"+ itemNum).prev().attr("data-flag");
			//元素为新增，则直接移除，否则，flag设为0 删除
			if (flag == "2") {
				$("#item"+ itemNum).remove();
			}else{
				$("#itemDelete"+ itemNum).prev().attr("data-flag",'1');
				$("#item"+ itemNum).css({top:'-999px' , position:'absolute'});
			}
		}
		
		function genMaterialList(goods){
			var goodArray = jQuery.parseJSON(goods);
	   		var good , htmlContent ;
			for(var i =0 ; i < goodArray.length ; i++){
				good = goodArray[i] ;
				htmlContent = 	"<div style='margin-bottom:10px;' id='item" + good.id + "' >" + 
				"<span style='display:inline-block; min-width:180px;'>" + good.goodsNo + " - " + good.name + ": </span>" + 
				"<span style='display:inline-block; min-width:180px;'>" + good.unitNum + "</span>" + 
				"<input type='text' data-type='out-num' data-flag='2' data-id='' data-goodId='" + good.id + "' data-unitNum='" + good.unitNum + "' placeholder='出库量' >" +
				"<input type='button' value='删除' id='itemDelete" + good.id + "' onclick='itemDelete("+good.id+")' class='margin_left10'>" +
				"</div>";

				$("#materialList").append(htmlContent);
			}
		}
		
		function setOutNum(capacity){
			$("#materialList input[data-type='out-num']").each(function(i,item){
         		var sum = parseInt($(item).attr("data-unitNum")) * capacity ;
         		$(item).val(sum) ;
         	});
		}
		
	</script>
</body>
</html>






