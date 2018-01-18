<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<form:form id="inputForm" action="" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">订单信息</label></td>
			    </tr>
			   	<tr>
			        <td class="active"><label class="pull-right">订单编号:</label></td>
			        <td><span class=" form-control">${order.orderId}</span></td>
			        <td class="active"><label class="pull-right">订单名:</label></td>
			        <td><span class=" form-control">${order.orderName}</span></td>
			    </tr>
			   	<tr>
			        <td class="active"><label class="pull-right">订单状态:</label></td>
			        <td><span class=" form-control">${fns:getDictLabel(order.status,'order_status','')}</span></td>
			        <td class="active"><label class="pull-right">创建时间:</label></td>
			        <td><span class=" form-control"><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			    </tr> 	
			   	<tr>
			        <td class="active"><label class="pull-right">原价:</label></td>
			        <td><span class=" form-control">￥${order.costPrice}</span></td>
			        <td class="active"><label class="pull-right">售价:</label></td>
			        <td><span class=" form-control">￥${order.salePrice}</span></td>
			    </tr>
			   	<tr>
			        <td class="active"><label class="pull-right">订单总额:</label></td>
			        <td><span class=" form-control">￥${order.orderAmount}</span></td>
			        <td class="active"><label class="pull-right">所购票数:</label></td>
			        <td><span class=" form-control"><fmt:formatNumber value="${order.number}" pattern="###"/></span></td>
			    </tr>
			    
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">购票者信息</label></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">姓名:</label></td>
			        <td><span class=" form-control">${user.realname}</span></td>
			        <td class="active"><label class="pull-right">手机号:</label></td>
			        <td><span class=" form-control">
				        <shiro:hasPermission name="wms:order:phone">
						${user.phone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="wms:order:phone">
						${fn:substring(user.phone,0,3)}*****${fn:substring(user.phone,8,11)}
						</shiro:lacksPermission>
			        </span></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">受让人姓名:</label></td>
			        <td><span class=" form-control">${order.realname}</span></td>
			        <td class="active"><label class="pull-right">受让人手机号:</label></td>
			        <td><span class=" form-control">
				        <shiro:hasPermission name="wms:order:phone">
						${order.usePhone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="wms:order:phone">
						${fn:substring(order.usePhone,0,3)}*****${fn:substring(order.usePhone,8,11)}
						</shiro:lacksPermission>
			        </span></td>
			    </tr>
			    
			    <c:if test="${order.status != 'UNPAID' and order.status != 'CLOSED'}">
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">支付信息</label></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">支付交易号:</label></td>
			        <td colspan="3"><span class=" form-control">${orderPaid.payTraceNo}</span></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">支付总额:</label></td>
			        <td  colspan="3">
				        <span class=" form-control" style="height:auto;">
				       		 ￥${orderPaid.balanceAmount + orderPaid.paidAmount + orderPaid.couponAmount}  <br/>
				       		(账户扣款：￥${orderPaid.balanceAmount}  + 
				       		微信支付额：￥${orderPaid.paidAmount}	+ 
				       		优惠券金额：￥${orderPaid.couponAmount} +
				       		手续费：￥${orderPaid.feeAmount}) 
				        </span>
			        </td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">支付行:</label></td>
			        <td colspan="3"><span class=" form-control">${orderPaid.payBank}</span></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">支付渠道:</label></td>
			        <td colspan="3"><span class=" form-control">${orderPaid.payChannel}</span></td>
			    </tr>
			    <tr>
			        <td class="active"><label class="pull-right">支付时间:</label></td>
			        <td colspan="3"><span class=" form-control"><fmt:formatDate value='${orderPaid.payTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			    </tr>
			    </c:if>
			    
			    <c:if test="${order.status eq 'REFUND'}">
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
			    
			    <c:if test="${orderPaid.status eq 1}">
			    <tr>
			        <td colspan="4" class="active">
			        	<label class="pull-left">
			        		票务信息    ( 共 <span style="color:red;">${fn:length(tickets)}</span> 张票 )
			        	</label>
			        </td>
			    </tr>
			    <c:forEach items="${tickets}" var="ticket">
				    <tr>
				        <td class="active"><label class="pull-right" style="color:blue;">票码:</label></td>
				        <td colspan="3"><span class=" form-control" style="color:blue;">${ticket.ticketCode}</span></td>
				    </tr>
				    <tr>
				        <td class="active"><label class="pull-right">状态:</label></td>
				        <td ><span class=" form-control">${fns:getDictLabel(ticket.usedFlag,'ticket_status','')}</span></td>
				        <td class="active"><label class="pull-right">使用时间:</label></td>
				        <td><span class=" form-control"><fmt:formatDate value='${ticket.usedTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
				    </tr>
				    <tr>
				        <td class="active"><label class="pull-right">核销员:</label></td>
				        <td ><span class=" form-control">${ticket.name}</span></td>
				        <td class="active"><label class="pull-right">电话:</label></td>
				        <td><span class=" form-control">${ticket.phone}</span></td>
				    </tr>
			    </c:forEach>
			    </c:if>
			    
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>