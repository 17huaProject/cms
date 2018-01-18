<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	  	#cate_attrib span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#cate_attrib input , #goodsSaleStatus input{margin-top:0px;width:auto;}
  		#cate_attrib label , #goodsSaleStatus label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		#goodsSaleStatus input{margin-top:-4px;}
  		#colorpickerdiv{display:inline-block; width:14px; height:14px; margin-right:5px;margin-bottom: -3px;}
  		.form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			//实例化编辑器ueditor
			var ue = UE.getEditor('content',{readonly:true});
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="good" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">商品名:</label></td>
			         <td><span class=" form-control">${good.name}</span></td>
			         <td class="active"><label class="pull-right">商品编号:</label></td>
			         <td><span class=" form-control">${good.goodsNo}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">进价:</label></td>
			         <td><span class=" form-control">￥${good.salePrice}</span></td>
			         <td class="active"><label class="pull-right">入库数量:</label></td>
			         <td><span class=" form-control"> <fmt:formatNumber value="${good.count}" pattern="###" /></span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">品牌名:</label></td>
			         <td><span class=" form-control">${good.brand}</span></td>
			         <td class="active"><label class="pull-right">品牌型号:</label></td>
			         <td><span class=" form-control">${good.modelNo}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">是否可售:</label></td>
			         <td><span class=" form-control">
			         	<c:if test="${good.goodsStatus eq 0}">否</c:if>
			        	<c:if test="${good.goodsStatus eq 1}">是</c:if></span>
			         </td>
			         <td class="active"><label class="pull-right">是否展示:</label></td>
			         <td><span class=" form-control">
			         	<c:if test="${good.display eq 0}">否</c:if>
			        	<c:if test="${good.display eq 1}">是</c:if></span>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">售价:</label></td>
			         <td><span class=" form-control">￥${good.price}</span></td>
			         <td class="active"><label class="pull-right">已售数量:</label></td>
			         <td><span class=" form-control">${good.saleCount}</span></td>
			    </tr>
				<tr>
			         <td class="active"><label class="pull-right">已使用量:</label></td>
			         <td colspan="3"><span class=" form-control">${good.usedCount}</span></td>
			    </tr>
				<tr>
			         <td class="active"><label class="pull-right">商品图:</label></td>
			         <td colspan="3">
			         	<img id="imageScanBig" src="${good.goodsImg}" width="100" style="display:inline-block; margin-left:50px;margin-right:10px;"/>
			         </td>
			    </tr> 		      
			    <tr>
			         <td class="active"><label class="pull-right">类型:</label></td>
			         <td colspan=3><span class=" form-control">${categoryName}</span></td>
			    </tr>
			    <tr>
			         <td class="active">
			         	<label class="pull-right">属性:</label>
			         </td>
			         <td colspan=3 id="goodCateAttribList">
			         	
						<c:forEach items="${attrMap}" var="cateAttrib">
							<div  style="margin-bottom:5px;">
						   	<span style="display:inline-block; min-width:80px;">${fns:getDictLabel(cateAttrib.key,'goodCategory_attr','')} :</span> 
							<c:choose>
								<c:when test='${cateAttrib.key eq "4" }'>
						   			<div id='colorpickerdiv' style="background-color:${cateAttrib.value}"></div>
								</c:when>
								<c:otherwise>
									${cateAttrib.value}
								</c:otherwise>
							</c:choose>
							</div>
				        </c:forEach> 
					 </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">详情:</label></td>
			         <td colspan=3>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${good.content}</script>
						<div style="color:green; width:100%;">注意：·图片&lt;2M, ·视频&lt;100MB, ·文件&lt;50MB</div>
					 </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><span class=" form-control">${good.remarks}</span></td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>