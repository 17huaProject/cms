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
  		.material_item_big{display:inline-block; min-width:180px;}
  		.material_title{display:inline-block; min-width:80px;font-weight: bold;}
  		.material_item{display:inline-block; min-width:80px;}
	</style>

</head>
<body>
	<form:form id="inputForm" action="" commandName="materialDeliveryDTO" method="post" class="form-horizontal">
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
									<span class="material_item" >${materialDelivery.inCount}</span>
									<span class="material_item" >
										${materialDelivery.outCount - materialDelivery.inCount}
									</span>
								</div>
			         		</c:forEach>
			         	 
			         	 </div>
					 </td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>

	<script type="text/javascript">
	$(document).ready(function() {
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






