<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>画师管理</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
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
	<form:form id="inputForm" action="" commandName="artist" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   	<tr>
			         <td class="active"><label class="pull-right">昵称:</label></td>
			         <td><span class=" form-control">${artist.artistName}</span></td>
			         <td class="active"><label class="pull-right">真实姓名:</label></td>
			         <td><span class=" form-control">${artist.realname}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">出生日期:</label></td>
			         <td><span class=" form-control">${artist.birthday}</span></td>
			         <td class="active"><label class="pull-right">性别:</label></td>
			         <td><span class=" form-control">
			         	<c:if test="${artist.gender eq 1}">男</c:if>
			         	<c:if test="${artist.gender eq 2}">女</c:if>
			         	</span> </td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control">
						<shiro:hasPermission name="wms:artist:phone">
						${artist.phone}
						</shiro:hasPermission>
						<shiro:lacksPermission name="wms:artist:phone">
						${fn:substring(artist.phone,0,3)}*****${fn:substring(artist.phone,8,11)}
						</shiro:lacksPermission>
			         </span></td>
			         <td class="active"><label class="pull-right">email:</label></td>
			         <td><span class=" form-control">${artist.email}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">学校:</label></td>
			         <td><span class=" form-control">${artist.school}</span></td>
			         <td class="active"><label class="pull-right">星级:</label></td>
			         <td><span class=" form-control">${artist.artistLevel}</span></td>
			    </tr>
			    <tr>
			         <td class="active"><label class="pull-right">身份证号:</label></td>
			         <td><span class=" form-control">
				         <shiro:hasPermission name="wms:artist:idcard">
						 ${artist.idcard}
						 </shiro:hasPermission>
						 <shiro:lacksPermission name="wms:artist:idcard">
						 ${fn:substring(artist.idcard,0,3)}*****${fn:substring(artist.idcard,fn:length(artist.idcard)-4,fn:length(artist.idcard))}
						 </shiro:lacksPermission>
			         </span></td>
			         <td class="active"><label class="pull-right">状态:</label></td>
			         <td><span class=" form-control">
						<c:if test="${artist.gender eq 0}">非公开</c:if>
			         	<c:if test="${artist.gender eq 1}">公开</c:if>
					</span></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right">画师头像:</label></td>
			         <td colspan="3">
			         	<img id="imageScanBig" src="${artist.bigImg}" width="100" style="display:inline-block; margin-left:10px;margin-right:10px;"/>
			         </td>
			      </tr> 

			      <tr>
			         <td class="active"><label class="pull-right">详细地址:</label></td>
			         <td colspan="3"><span class=" form-control">${artist.address}</span></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">简介:</label></td>
			         <td colspan=3><span class=" form-control">${artist.artistDesc}</span></td>
			      </tr>
			      <tr>
			         <td class="active"><label class="pull-right">详情:</label></td>
			         <td colspan=3>
						<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${artist.content}</script>					 
					 
					 </td>
			      </tr>
			      <tr> 
			         <td class="active"><label class="pull-right">备注:</label></td>
			         <td colspan=3><span class=" form-control">${artist.remark}</span></td>
			      </tr>
		 </tbody> 
		 </table> 
	</form:form>

</body>
</html>


