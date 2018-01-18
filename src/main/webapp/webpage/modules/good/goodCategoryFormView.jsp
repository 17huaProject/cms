<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品类型管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control{border:0px;height:auto;} 
	   label{min-width: 60px;}
	</style>
</head>
<body>
	<form:form id="inputForm" action="" commandName="goodCategory" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">类别名:</label></td>
			         <td colspan="3"><span class=" form-control">${goodCategory.name}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">属性:</label></td>
			         <td colspan="3">
				         <c:forEach items="${goodCategory.cateAttribs}" var="cateAttrib">
				         	${fns:getDictLabel(cateAttrib,'goodCategory_attr','')}, 
				         </c:forEach>
			         </td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><span class=" form-control">${goodCategory.remarks}</span></td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>