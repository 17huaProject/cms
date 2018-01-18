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
   		.form-control{border:0px;height:auto;} 
	</style>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="materialModel" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">模板名:</label></td>
			         <td colspan="3"><span class=" form-control">${materialModel.name}</span></td>
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
									<span style='display:inline-block; min-width:180px;'>${goodsInfoMap[fn:trim(good.id)]} </span> 
								</div>
			         		</c:forEach>
			         	 </div>
					 </td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>