<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>礼品卡管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="gift" method="post" class="form-horizontal">
		<form:hidden path="giftId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
			        <td colspan="2" rowspan="5">
			        	<img id="imageScanBig" src="" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/>
<%-- 			        	<img id="imageScanBig" src="${gift.bigImg}" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/> --%>
			        </td>
			    </tr> 		      
			   	<tr>
			         <td class="active"><label class="pull-right">礼品卡名:</label></td>
			         <td><span class=" form-control">${gift.giftName}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">寄送者:</label></td>
			         <td><span class=" form-control">${gift.sender}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">接收者:</label></td>
			         <td><span class=" form-control">${gift.receiver}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">状态:</label></td>
			         <td><span class=" form-control" style="color:red;">${fns:getDictLabel(gift.giftStatus,'gift_status','')}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">祝福语:</label></td>
			         <td colspan="3"><span class=" form-control">${gift.content}</span></td>
			    </tr>
			      <tr>
			         <td class="active"><label class="pull-right">创建时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${gift.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">支付时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${orderPaid.payTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">收货时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${gift.receivedTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">过期时间:</label></td>
			         <td><span class=" form-control"><fmt:formatDate value='${gift.expiryTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			      </tr>
			      
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">购卡者信息</label></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">购卡人姓名:</label></td>
			        <td><span class=" form-control">${user.realname}</span></td>
			        <td class="active"><label class="pull-right">购卡人手机号:</label></td>
			        <td><span class=" form-control">
				        <shiro:hasPermission name="wms:user:phone">
						${user.phone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="wms:user:phone">
						${fn:substring(user.phone,0,3)}*****${fn:substring(user.phone,8,11)}
						</shiro:lacksPermission>
			        </span></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">受赠人姓名:</label></td>
			        <td><span class=" form-control">${receiver.realname}</span></td>
			        <td class="active"><label class="pull-right">受赠人手机号:</label></td>
			        <td><span class=" form-control">
				        <shiro:hasPermission name="wms:user:phone">
						${receiver.phone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="wms:user:phone">
						${fn:substring(receiver.phone,0,3)}*****${fn:substring(receiver.phone,8,11)}
						</shiro:lacksPermission>
			        </span></td>
			    </tr>
			    
			      
			      <c:if test="${gift.giftStatus eq 'PAID' or gift.giftStatus eq 'REFUND' or gift.giftStatus eq 'RECEIVED'}">
				      <tr>
			        	<td colspan="4" class="active"><label class="pull-left">支付信息</label></td>
				     </tr>
				      <tr>
				         <td class="active"><label class="pull-right">支付信息:</label></td>
				         <td colspan=3>
				         	<span class=" form-control">
				         	共支付：￥${gift.orderAmount} (  <span style="color:red;">${gift.number}</span> 张, 每张面额：${gift.price} ) <br>
				         	其中：(账户扣款：￥${orderPaid.balanceAmount}  , 
						       		支付金额：￥${orderPaid.paidAmount}	, 
						       		优惠券金额：￥${orderPaid.couponAmount} ,
						       		手续费：￥${orderPaid.feeAmount}) 
				         	</span>
				         </td>
				      </tr>
					    <tr>
					        <td class="active"><label class="pull-right">支付交易号:</label></td>
					        <td><span class=" form-control">${orderPaid.payTraceNo}</span></td>
					        <td class="active"><label class="pull-right">支付行:</label></td>
					        <td><span class=" form-control">${orderPaid.payBank}</span></td>
					    </tr>
					    <tr>
					        <td class="active"><label class="pull-right">支付渠道:</label></td>
					        <td><span class=" form-control">${orderPaid.payChannel}</span></td>
					        <td class="active"><label class="pull-right">支付时间:</label></td>
					        <td><span class=" form-control"><fmt:formatDate value='${orderPaid.payTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
					    </tr>
			      </c:if>
			      
			      <c:if test="${gift.giftStatus eq 'REFUND'}">
			       	<tr>
			        	<td colspan="4" class="active"><label class="pull-left">退款信息</label></td>
				    </tr>
				     <tr>
				        <td class="active"><label class="pull-right">退款交易号:</label></td>
				        <td><span class=" form-control">${orderRefund.refundTraceNo}</span></td>
				        <td class="active"><label class="pull-right">退款金额:</label></td>
				        <td><span class=" form-control">${orderRefund.refundAmount}</span></td>
				    </tr>
				     <tr>
				        <td class="active"><label class="pull-right">退款渠道:</label></td>
				        <td><span class=" form-control">${orderRefund.payChannel}</span></td>
				        <td class="active"><label class="pull-right">退款时间:</label></td>
				        <td><span class=" form-control"><fmt:formatDate value='${orderRefund.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
				    </tr>
			      </c:if>
			      <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><span class=" form-control">${gift.remark}</span></td>
			      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>