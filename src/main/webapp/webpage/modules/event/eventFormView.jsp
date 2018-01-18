<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
	<meta name="decorator" content="default"/>
	<style> 
		/* IE 6 不支持 max-height
		* 使用 height 代替，但是这会强制菜单总是显示为那个高度
		*/
		* html .ui-autocomplete {
		    height: 200px;
	  	}
		.ui-autocomplete{
		    max-height: 300px;
		    overflow-y: auto;
		    overflow-x: hidden; /* 防止水平滚动条 */
			list-style: none; 
			margin-left:0px; 
			padding-left:0px;}
	  	.ui-autocomplete  li a{ 
	  		display:block; 
	  		width: 86%; 
	  		background:#F5F5F5; 
	  		padding-top:2px; 
	  		padding-left:3px;
	  		padding-bottom:2px; 
	  		border:#09F solid 0.2px; 
	  		font-size:13px; }  
	  	.ui-autocomplete  li a:hover{ background: #E6E3FD }
	  	.font-highlight{color:red};
  
  
  		#freeService span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#freeService input , #isRefundSelect input{margin-top:0px;width:auto;}
  		#freeService label , #isRefundSelect label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		#isRefundSelect input{margin-top:-4px;}
  		.form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
    </style>
	<script type="text/javascript">
	
		$(document).ready(function() {
			var ue = UE.getEditor('content',{readonly:true});
			ue.ready(function() {
			});
		});

	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="event" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   	 <tr>
		         <td class="active"><label class="pull-right">活动名称:</label></td>
		         <td><span class=" form-control">${event.eventName}</span></td>
		         <td class="active"><label class="pull-right">活动类型:</label></td>
		         <td><span class=" form-control">${fns:getDictLabel(event.type,'event_type','')}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">价格:</label></td>
		         <td><span class=" form-control">${event.price}</span></td>
		         <td class="active"><label class="pull-right">允许退款:</label></td>
		         <td id="isRefundSelect"><span class=" form-control">
					<c:if test="${event.isRefund eq 0}">否</c:if>
			        <c:if test="${event.isRefund eq 1}">是</c:if></span>
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">活动人数:</label></td>
		         <td><span class=" form-control">${event.capacity}</span></td>
		         <td class="active"><label class="pull-right">活动状态:</label></td>
		         <td><span class=" form-control">${fns:getDictLabel(event.eventStatus,'event_status','')}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">城市:</label></td>
		         <td colspan="3"><span class=" form-control">
			         <c:forEach items="${cityInfo}" var="city">
			         	${city.value}
			         </c:forEach>
		         </span></td>
		      </tr>
		      <tr> 
		         <td class="active"><label class="pull-right">活动时间:</label></td>
		         <td><span class=" form-control"><fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
		         <td class="active"><label class="pull-right">报名截止时间:</label></td>
		         <td><span class=" form-control"><fmt:formatDate value='${event.closingTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
		      </tr>
 
		      <tr>
	      		 <td class="active"><label class="pull-right">免费服务:</label></td>
		         <td colspan=3 id="freeService"><span class=" form-control">
			         <c:forEach items="${event.freeServices}" var="freeService">
			         	${fns:getDictLabel(freeService,'free_service','')}, 
			         </c:forEach>
		         </span></td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right">作品:</label></td>
		         <td colspan=3><span class=" form-control">${art.artName}</span></td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right">画师:</label></td>
		         <td colspan=3><span class=" form-control">${artist.realname}</span></td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right">助教:</label></td>
		         <td colspan=3><span class=" form-control">${user.name}</span></td>
			  </tr> 
		      <tr>
	      		 <td class="active"><label class="pull-right">场所:</label></td>
		         <td colspan=3><span class=" form-control">${venue.venueName}</span></td>
			  </tr>  
			  <tr>
		         <td class="active"><label class="pull-right">商家佣金费率:</label></td>
		         <td><span class=" form-control">${event.venueFeeRate}</span></td>
		         <td class="active"><label class="pull-right">商家佣金:</label></td>
		         <td>	
		         	<span class=" form-control">
		         	<c:if test="${event.venueFee.unscaledValue() eq 0}">无</c:if>
		         	<c:if test="${event.venueFee.unscaledValue() != 0}">${event.venueFee}</c:if>
		         	</span>
		         </td>
		      </tr>
			  <tr>
	      		 <td class="active"><label class="pull-right">活动标签:</label></td>
		         <td colspan=3><span class=" form-control">${event.keywords}</span></td>
			  </tr> 
		      <tr>
		         <td class="active"><label class="pull-right">简介:</label></td>
		         <td colspan=3><span class=" form-control">${event.eventDesc}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">详情:</label></td>
		         <td colspan=3>
					<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${event.content}</script>	
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">分享标题:</label></td>
		         <td colspan=3><span class=" form-control">${event.shareDesc}</span></td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan=3><span class=" form-control">${event.remark}</span></td>
		      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>