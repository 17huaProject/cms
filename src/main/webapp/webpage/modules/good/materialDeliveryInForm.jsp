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
  		.material_title_big{display:inline-block; min-width:180px;font-weight: bold;}
  		.material_item_big{display:inline-block; width:180px;}
  		.material_title{display:inline-block; width:80px;font-weight: bold;}
  		.material_item{display:inline-block; width:80px;}
	</style>

</head>
<body>
	<form:form id="inputForm" action="${ctx}/cms/materialDelivery/saveIn" commandName="materialDeliveryDTO" method="post" class="form-horizontal">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">物料单名:</label></td>
			         <td colspan="3" id="material-delivery-title">
			         		物料单：${event.eventName} - <fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">活动:</label></td>
			         <td colspan="3"> ${event.eventName} </td>
			    </tr>
			    <tr>
			         <td class="active">
			         	<label class="pull-right">物料列表:</label>
			         	<form:hidden path="goodsInfo" htmlEscape="false"  class="form-control" />
			         </td>
			         <td colspan=3>
			         	 <div style='margin-bottom:10px;'>
							<span class="material_title_big">物料名</span> 
							<span class="material_title">单位量</span> 
							<span class="material_title">出库量</span> 
							<span class="material_title">入库量</span> 
							<span class="material_title">使用量</span>
						 </div>
			         	 <div  id="materialList">
			         		<c:forEach items="${materialDeliverys}" var="materialDelivery">
					      		<div style='margin-bottom:10px;' id="item${materialDelivery.id}" >
									<span class="material_item_big">${materialDelivery.goodsNo} - ${materialDelivery.goodName} </span> 
									<span class="material_item" id="material_unit_${materialDelivery.goodId}"></span>
									<span class="material_item" >${materialDelivery.outCount}</span>
					         	 	<input class="material_item required" type='text' 
											data-type='in-num' 
											data-id='${materialDelivery.id}' 
											data-goodId='${materialDelivery.goodId}' 
											data-usedCount='' 
											placeholder='入库量' 
											value="${materialDelivery.inCount}">
									<span class="material_item" ></span>
								</div>
			         		</c:forEach>
			         	 </div>
					 </td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>

	<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  
         	var resultJson = "" ;
         	$("#materialList input[data-type='in-num']").each(function(i,item){
         		var inNum = $.trim($(item).val()) ;
	         	resultJson += $(item).attr("data-id") + ":" 
	         				+ inNum +  "," ;
         	});
         	var jsonLength = resultJson.length ;
         	if (jsonLength > 1) {
         		resultJson = resultJson.substr(0,resultJson.length-1);
         	}
		  
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
		
		$("#materialList input[data-type='in-num']").focusout(function() {
			var inNum = parseInt( $(this).val() ) ;
			var outNum = parseInt( $(this).prev().html() );
			var usedCount = outNum - inNum ;
			if ( usedCount > 0) {
				$(this).next().html( usedCount );
				$(this).prev().attr("data-usedCount" , usedCount);
			} else {
				$(this).val("");
				$(this).next().html("<span style='color:red'>错误!结果为："+ usedCount +"</span>");
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
						}
			   		}else{
			   			console.log("查询出错！");
			   		}
		   });
		}
		//:~
		
		
	});
	</script>

</body>
</html>






