<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>场所结算</title>
	<meta name="decorator" content="default"/>
	<style> 
    </style>
	<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  $("#certImg").val($("#imageScanBig").attr("src"));
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
		
		$("#feeRate").focusout(function(){
			var venueFeeRate = $(this).val();
			var price = parseInt($("#price").html()) ;
			var capacity = parseInt($("#capacity").html()) ;
			
			if (venueFeeRate > 0 && venueFeeRate < 1) {
				if (price > 0 && capacity > 0) {
					var venueFee = capacity * price * venueFeeRate  ;
					$("#fee").val(venueFee);
				} else {
					alert("价格和人数不能为空");
				}
			}
		});
		
		 //图片上传
		  $("#selectImage").click( function(){ 
			  var url = "${ctxStatic}/cropper-master/imageCropper.html";
			  windowOpen(url,"文件管理",1100,630);
			  
		  });
		//:~ 图片上传
		
	});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/settle/venueSettle" commandName="commissionSettle" method="post" class="form-horizontal">
		<input type="hidden" name="eventId" value="${event.id}">
		<input type="hidden" name="tollerId" value="${event.venueId}">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			  <tr>
		         <td class="active"><label class="pull-right">活动名称:</label></td>
		         <td>${event.eventName}</td>
		         <td class="active"><label class="pull-right">活动类型:</label></td>
		         <td>${fns:getDictLabel(event.type,'event_type','')}</td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">价格:</label></td>
		         <td><span id="price">${event.price}</span></td>
		         <td class="active"><label class="pull-right">允许退款:</label></td>
		         <td id="isRefundSelect">
					<c:if test="${event.isRefund eq 0}">否</c:if>
			        <c:if test="${event.isRefund eq 1}">是</c:if>
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">活动人数:</label></td>
		         <td><span id="capacity">${event.capacity}</span></td>
		         <td class="active"><label class="pull-right">活动状态:</label></td>
		         <td>${fns:getDictLabel(event.eventStatus,'event_status','')}</td>
		      </tr>
			  <tr>
	      		 <td class="active"><label class="pull-right">场所:</label></td>
		         <td colspan=3>${venue.venueName}</td>
			  </tr> 
		      <tr>
				 <td class="active"><label class="pull-right">联系人:</label></td>
			     <td>${venue.contact}</td>
		         <td class="active"><label class="pull-right">手机号:</label></td>
		         <td>
		         	<shiro:hasPermission name="wms:venue:phone">
					${venue.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:venue:phone">
					${fn:substring(venue.phone,0,3)}*****${fn:substring(venue.phone,8,11)}
					</shiro:lacksPermission>
		         </td>
		      </tr>   
		      <tr>
		         <td class="active"><label class="pull-right">商家佣金费率:</label></td>
		         <td>
		         	<input id="feeRate" name="feeRate" number='true' min="0" max="1"  class=" form-control" value="${event.venueFeeRate}"/>
		         	<span style="color:green; width:100%;">范围(0,1], 1:固定金额 , (0,1):佣金费率</span>
		         </td>
		         <td class="active"><label class="pull-right">商家佣金:</label></td>
		         <td>
		         	<input id="fee" name="fee" number='true' class=" form-control" value="${event.venueFee}"/>
		         	<span style="color:green; width:100%;">费率:1, 填写固定金额 ; 费率:(0,1), 佣金=价格*人数*费率</span>
		         </td>
		      </tr>	
		      <tr>
		      	<td class="active"><label class="pull-right">证明文件</label></td>
		      	<td colspan="3">
		         	<button type="button" id="selectImage" class="btn btn-primary" >选择</button>
		         	<input type="hidden" name="certImg"  maxlength="255" class="form-control"/>
		         	<img id="imageScanBig" src="" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
		         </td>
		      </tr>		  
		 </tbody> 
		 </table> 
	</form:form>
</body>

	
</html>