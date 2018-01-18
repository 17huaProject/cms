<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新闻管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var ue = UE.getEditor('content',{readonly:true});
			ue.ready(function() {
			});
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="" commandName="article" method="post" class="form-horizontal">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">标题:</label></td>
			         <td  colspan=3>${article.title}</td>
			    </tr>
			   	<tr>
			        <td class="active"><label class="pull-right">作者:</label></td>
			        <td colspan=3>${article.author}</td>
			    </tr>
			    <tr>
		      		 <td class="active"><label class="pull-right">关键字:</label></td>
			         <td colspan=3>${article.keywords}</td>
				</tr>
				<tr>
			         <td class="active"><label class="pull-right">头部展示图:</label></td>
			         <td colspan="3">
			         <img id="imageScanBig" src="${article.headerImg}" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/>
			         </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">类别:</label></td>
			         <td>${articleCat.catName}</td>
			         <td class="active"><label class="pull-right">状态:</label></td>
			         <td>${fns:getDictLabel(article.isOpen,'article_isOpen','')}</td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">打开方式:</label></td>
			         <td colspan="3">${fns:getDictLabel(article.openType,'article_openType','')}</td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">外链:</label></td>
			         <td colspan=3><a href="${article.link}" target="_blank">${article.link}</a></td> 
			    </tr>
		      <tr>
		         <td class="active"><label class="pull-right">简介:</label></td>
		         <td colspan=3>${article.introduce}</td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">详情:</label></td>
		         <td colspan=3>
					<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${article.content}</script>	
				 </td>
		      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>