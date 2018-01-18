<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>佣金明细</title>
	<meta name="decorator" content="default"/>
	<style> 
    </style>
	<script type="text/javascript">
	</script>
</head>
<body style="margin: 10px;">

	<div style="font-size:14px"> 佣金总额： <span style="color:green">${feeSum} </span>元</div>


	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>活动名</th>
				<th>价格</th>
				<th>人数</th>
				<th>费率</th>
				<th>佣金</th>
				<th>结算状态</th>
				<th>结算时间</th>
				<th>结算人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${settlements}" var="settlement">
			<tr>
				<td>${settlement.eventName}</td>
				<td>${settlement.price}</td>
				<td>${settlement.capacity}</td> 
				<td>${settlement.feeRate}</td>
				<td>${settlement.fee}</td>
				<td>${settlement.status}</td>
				<td><fmt:formatDate value="${settlement.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${settlement.name}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>





</body>
</html>