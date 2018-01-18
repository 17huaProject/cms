<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定制管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	   .form-control-none{border:0px;height:auto;} 
	</style>	
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			
		});
	</script>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/custom/save" commandName="custom" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		<tr>
			        <td colspan="4" class="active"><label class="pull-left">定制信息</label></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">姓名:</label></td>
			         <td><span class=" form-control form-control-none">${custom.contact}</span></td>
			         <td class="active"><label class="pull-right">手机号:</label></td>
			         <td><span class=" form-control form-control-none">${custom.phone}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">城市:</label></td>
			         <td><span class=" form-control form-control-none">${custom.city}</span></td>
			         <td class="active"><label class="pull-right">类型:</label></td>
			         <td><span class=" form-control form-control-none">${fns:getDictLabel(custom.customType,'custom_type','')}</span></td>
			      </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">预计时间:</label></td>
			         <td><span class=" form-control form-control-none"><fmt:formatDate value='${custom.estDate}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">预计人数:</label></td>
			         <td><span class=" form-control form-control-none">${custom.estNum}</span></td>
			    </tr>
			   	<tr>
			         <td class="active"><label class="pull-right">创建时间:</label></td>
			         <td><span class=" form-control form-control-none"><fmt:formatDate value='${custom.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span></td>
			         <td class="active"><label class="pull-right">状态:</label></td>
			         <td><span class=" form-control form-control-none">${fns:getDictLabel(custom.status,'custom_status','')}</span></td>
			    </tr>
			    
			    <!-- 处理 情况 -->
			    <tr>
			        <td colspan="4" class="active"><label class="pull-left">处理情况</label></td>
			    </tr>
			     <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>处理人:</label></td>
			         <td colspan="3"><span class=" form-control">${custom.currentUser.name}</span></td>
			     </tr>
			     <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>意见:</label></td>
			         <td colspan="3">
						<form:select path="status"  class="form-control required">
							<form:options items="${fns:getDictList('custom_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						 </form:select>
					</td>
			     </tr>
			     <%-- <tr>
			         <td class="active"><label class="pull-right"><font color="red">*</font>处理时间:</label></td>
			         <td colspan="3">
			         	<input id="transTime" name="transTime"  maxlength="50" class=" form-control" 
						value="<fmt:formatDate value='${custom.transTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"/> 
					 </td>
			     </tr> --%>
			     <tr>
		         	<td class="active"><label class="pull-right"><font color="red">*</font>备注:</label></td>
		         	<td colspan=3><form:textarea path="transDesc" htmlEscape="false" rows="3" maxlength="200" class="form-control required"/></td>
		     	 </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>