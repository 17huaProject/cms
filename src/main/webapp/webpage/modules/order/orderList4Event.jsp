<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	   .sort-column {
		    color: #0663a2;
		    text-align:center;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		orderSummary() ;
		
		$("#order_status").change(function(){
			orderListDisplay() ;
			orderSummary() ;
		});
		
		$("#pay_way").change(function(){
			orderListDisplay() ;
			orderSummary() ;
		});
		
	})
	/*
	订单行的显示与隐藏
	*/
	function orderListDisplay(){
		var orderStatusVal = $("#order_status").find("option:selected").val();
		var payWayVal = $("#pay_way").find("option:selected").val();
		$("#order_list tr").css("display","");
		if (payWayVal != "") {
			$("#order_list tr:not(."+payWayVal+")").css("display","none");
		}
		if (orderStatusVal != "") {
			if (orderStatusVal == "buySuccess") {
				$("#order_list .UNPAID").css("display","none");
				$("#order_list .REFUND").css("display","none");
				$("#order_list .CLOSED").css("display","none");
			} else {
				$("#order_list tr:not(."+orderStatusVal+")").css("display","none");
			}
		}
	}
	/*
	订单统计数据
	*/
	function orderSummary(){
		var orderStatusVal = $("#order_status").find("option:selected").val();
		var orderSumNum = $("#order_list tr:visible").size();
		var ticketSumNum = 0 ;
		var paySumNum = 0 ;
		$("#order_list tr:visible").each(function(){
			ticketSumNum += parseInt($(this).find(".ticketNum").html()) ;
			paySumNum += parseFloat($(this).find(".payNum").html()) ;
		});
		
		var summaryInfo = "总订单数: " + orderSumNum + " , 总票数: " + ticketSumNum ;
		
		if (orderStatusVal != "") {
			if (orderStatusVal == "UNPAID") {
				summaryInfo += " , 待支付总额: ￥" + paySumNum.toFixed(2) ;
			} else if (orderStatusVal == "CLOSED") {
				summaryInfo += " , 关闭交易总额: ￥" + paySumNum.toFixed(2) ;
			} else if (orderStatusVal == "REFUND") {
				summaryInfo += " , 退款总额: ￥" + paySumNum.toFixed(2) ;
			} else {
				summaryInfo += " , 支付总额: ￥" + paySumNum.toFixed(2) ;
			}
		} else {
			summaryInfo += " , 总额: ￥" + paySumNum.toFixed(2) ;
		}
		
		$("#summaryInfo").html(summaryInfo);
	}
	
	
	</script>
</head>
<body>
	<div style="margin:5px 10px;">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<c:choose>
				<c:when test="${event.eventName eq null || event.eventName eq ''}">
				    <tbody>
				   	  <tr>
				         <td colspan="4"><label class="pull-left" style="color:red;">该订单未与活动关联</label></td>
				      </tr>
					</tbody>
				</c:when>
				<c:otherwise>
				   <tbody>
				   	 <tr>
				         <td class="active"><label class="pull-right">活动名称:</label></td>
				         <td colspan="3"><span class=" form-control">${event.eventName} ( ${fns:getDictLabel(event.type,'event_type','')} )</span></td>
				      </tr>
				      <tr>
				         <td class="active"><label class="pull-right">价格:</label></td>
				         <td><span class=" form-control">￥${event.price}</span></td>
				         <td class="active"><label class="pull-right">活动人数:</label></td>
				         <td><span class=" form-control">${event.capacity}</span></td>
				      </tr>
				      <tr>
				         <td class="active"><label class="pull-right">活动时间:</label></td>
				         <td><span class=" form-control"><fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
				         <td class="active"><label class="pull-right">活动状态:</label></td>
				         <td><span class=" form-control">${fns:getDictLabel(event.eventStatus,'event_status','')}</span></td>
				      </tr>
				      <tr>
			      		 <td class="active"><label class="pull-right">画师:</label></td>
				         <td colspan=3>
					         <span class=" form-control">
					         	${event.artistName}  ( ${event.artistCity} , ${event.artistPhone} )
					         </span>
				         </td>
					  </tr>  
				      <tr>
			      		 <td class="active"><label class="pull-right">作品:</label></td>
				         <td colspan=3>
				         	<span class=" form-control">
				         		<img alt="${art.artName}" src="${event.artImg}!80.40" width="80" height="40">
				         		${event.artName}
				         	</span>
				         </td>
					  </tr>  
				      <tr>
			      		 <td class="active"><label class="pull-right">场所:</label></td>
				         <td colspan=3>
					         <span class=" form-control">
					        	 <div> ${event.venueName} ( ${event.venueContact} , ${event.venuePhone} ) </div>
					        	 <div> ${event.venueAddress} </div>
					         </span>
				         </td>
					  </tr>  
				      <tr>
			      		 <td class="active"><label class="pull-right">助教:</label></td>
				         <td colspan=3><span class=" form-control">${event.assistantName} , ${event.assistantPhone}</span></td>
					  </tr> 
				 </tbody> 
				</c:otherwise>
			</c:choose>
		 </table> 

		<div  style="margin-bottom: 10px; margin-top: 30px; font-size: 16px;">
			筛选条件：
			<form action=""  style="display:inline-block ;">
				<span class="querylistspan">订单状态：</span>
				<form:select path="orderStatus" id="order_status">
					<option value="">全部</option>
					<option value="buySuccess">购买成功</option>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</form>
			<span class="querylistspan">支付方式：</span>
			<select id="pay_way">
				<option value="">全部</option>
				<option value="balanceWay">账户扣款</option>
				<option value="payMoneyWay">微信支付额</option>
				<option value="couponWay">优惠券</option>
				<option value="otherWay">其他</option>
			</select>
			<span  class="querylistspan" id="summaryInfo"></span>
		</div>

		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<thead>
				<tr>
					<th class="sort-column">订单编号</th>
					<th class="sort-column">订单名</th>
					<th class="sort-column">票数</th>
					<th class="sort-column">订单金额</th>
					<th class="sort-column">支付金额</th>
					<th class="sort-column">支付方式</th>
					<th class="sort-column">创建时间 </th>
					<th class="sort-column">购票人</th>
					<th class="sort-column">手机号</th>
					<th class="sort-column">状态</th>
				</tr>
			</thead>
		    <tbody id="order_list">
		    	<c:forEach items="${orders}" var="order">
				   	<tr class="${order.status} 
				   	 	<c:choose>
				        	<c:when test="${null != order.balanceAmount and order.balanceAmount gt 0}">balanceWay</c:when>
				        	<c:when test="${null != order.paidAmount and order.paidAmount gt 0}">payMoneyWay</c:when>
				        	<c:when test="${null != order.couponAmount and order.couponAmount gt 0}">couponWay</c:when>
				        	<c:otherwise>otherWay</c:otherwise>
				        </c:choose>
				   	">
				        <td><span class=" form-control">${order.orderId}</span></td>
				        <td><span class=" form-control">${order.orderName}</span></td>
				        <td><span class=" form-control ticketNum"><fmt:formatNumber value="${order.number}" pattern="###"/></span></td>
				        <td><span class=" form-control">￥${order.orderAmount}</span></td>
				        <td>
					       	 <span class=" form-control" style="height:auto;">
					       		 ￥<span class="payNum">${order.balanceAmount + order.paidAmount + order.couponAmount}</span>
					        </span>
				        </td>
				        <td>
					        <span class=" form-control" style="height:auto;">
					        <c:choose>
					        	<c:when test="${null != order.balanceAmount and order.balanceAmount gt 0}">账户扣款</c:when>
					        	<c:when test="${null != order.paidAmount and order.paidAmount gt 0}">微信支付额</c:when>
					        	<c:when test="${null != order.couponAmount and order.couponAmount gt 0}">优惠券</c:when>
					        	<c:otherwise>其他</c:otherwise>
					        </c:choose>
					        </span>
				        </td>
				        <td><span class=" form-control"><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
				        <td><span class=" form-control">${order.realname}</span></td>
				        <td><span class=" form-control">
					        <shiro:hasPermission name="wms:order:phone">
							${order.usePhone}
							</shiro:hasPermission>
							<shiro:lacksPermission name="wms:order:phone">
							${fn:substring(order.usePhone,0,3)}*****${fn:substring(order.usePhone,8,11)}
							</shiro:lacksPermission>
				        </span></td>
				        <td><span class=" form-control">${fns:getDictLabel(order.status,'order_status','')}</span></td>
				    </tr>
		    	</c:forEach>
		 </tbody> 
		 </table>
	</div> 
</body>
</html>