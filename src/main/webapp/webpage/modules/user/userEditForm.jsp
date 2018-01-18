<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>基本信息</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.dataTable tbody tr td label {margin-bottom: 0px;}
		.paddingLeft10 {padding-left:10px;}
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
	
	<shiro:hasPermission name="wms:user:addBalance">
	<script type="text/javascript">
	$(document).ready(function() {
		//余额
		$("#add_balance").click(function(){
			$("#add_balance_div").css("display","inline-block"); 
		});
		$("#add_balance_cancel").click(function(){
			$("#balance_input").val("");
			$("#add_balance_div").css("display","none"); 
		});
		$("#add_balance_submit").click(function(){
			var balanceAdd = $("#balance_input").val(); 
			var userId = $("#userId").val(); 
	
			$("#balance_input").val("");
			$("#add_balance_div").css("display","none");
			$("#add_balance_spinner_div").css("display","inline-block");
			
			$.post("${ctx}/wms/user/addBalance", {userId : userId , balanceAdd : balanceAdd},
			   function(data){
					if (data.code == 0) {
						$("#balance_show").html(data.data);	
						$("#add_balance_spinner_div").css("display","none");
					} else {
						alert(data.message) ;
						$("#add_balance_spinner_div").css("display","none");
						$("#add_balance_div").css("display","inline-block"); 
					}
			});
		});
	});
	</script>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="wms:user:addScore">
	<script type="text/javascript">
	$(document).ready(function() {
		//积分
		$("#add_score").click(function(){
			$("#add_score_div").css("display","inline-block"); 
		});
		$("#add_score_cancel").click(function(){
			$("#score_input").val("");
			$("#add_score_div").css("display","none");
		});
		$("#add_score_submit").click(function(){
			var scoreAdd = $("#score_input").val();
			var userId = $("#userId").val(); 
	
			$("#score_input").val("");
			$("#add_score_div").css("display","none");
			$("#add_score_spinner_div").css("display","inline-block");
			
			$.post("${ctx}/wms/user/addScore", { userId : userId , scoreAdd : scoreAdd},
			   function(data){
					if (data.code == 0) {
						$("#score_show").html(data.data);
						$("#add_score_spinner_div").css("display","none");
					} else {
						alert(data.message) ;
						$("#add_score_spinner_div").css("display","none");
						$("#add_score_div").css("display","inline-block"); 
					}
			});
		});
	})
	</script>
	</shiro:hasPermission>
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/user/save" commandName="user" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>
		<table class="table dataTable no-footer"> 
		   <tbody>
			   	<tr>
			         <td colspan="4" class="active"><label class="pull-left">基本信息</label></td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">姓名:</label></td>
			         <td>${user.realname}</td>
			         <td ><label class="pull-right">昵称:</label></td>
			         <td>${userProfile.nickname}</td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">性别:</label></td>
			         <td>${fns:getDictLabel(userProfile.gender,'gender_type','')}</td>
			         <td ><label class="pull-right">生日:</label></td>
			         <td>${userProfile.birthday}</td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">地点:</label></td>
			         <td>${userProfile.country}-${userProfile.province}-${userProfile.city}</td>
			         <td ><label class="pull-right">工作地:</label></td>
			         <td>${userProfile.workplace}</td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">职业:</label></td>
			         <td>${userProfile.occupation}</td>
			         <td ><label class="pull-right">email:</label></td>
			         <td>${user.email}</td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">手机号:</label></td>
			         <td><input name="phone" maxlength="11" class=" form-control  required" value="${user.phone}"/></td>
			         <td ><label class="pull-right">身份证号:</label></td>
			         <td><input name="idcard" maxlength="18" class=" form-control  required" value="${user.idcard}"/></td>
			    </tr>
			   	<tr>
			         <td ><label class="pull-right">客户类型:</label></td>
			         <td>
			          	<form:select path="userType"  class="form-control required">
							<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
			         </td>
			         <td ><label class="pull-right">客户状态:</label></td>
			         <td>
			          	<form:select path="status"  class="form-control required">
							<form:options items="${fns:getDictList('user_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
			         </td>
			    </tr>
			    <tr>
			         <td colspan="4" class="active"><label class="pull-left">资产信息</label></td>
			    </tr>
			    <tr>
			         <td><label class="pull-right">账户余额:</label></td>
			         <td>
			         	￥<span id="balance_show">${user.balance}</span>
			         	<shiro:hasPermission name="wms:user:addBalance">
			         	<span><a id="add_balance" href="#">充值</a></span>
			         	<div id="add_balance_div" style="display:none;margin-left:10px;">
				         	<input id="balance_input" class="paddingLeft10" placeholder="充值的金额(可为负数)"/>
				         	<a id="add_balance_submit" class="layui-layer-btn0 paddingLeft10">提交</a>
				         	<a id="add_balance_cancel" class="layui-layer-btn1 paddingLeft10">取消</a>
			         	</div>
			         	<div  id="add_balance_spinner_div" style="display:none;margin-left:10px;color:red;"><i class='fa fa-spinner fa-spin'></i></div>
			         	</shiro:hasPermission>
			         </td>
			    </tr>
			    <tr>
			         <td><label class="pull-right">积分:</label></td>
			         <td colspan="3">
			         	<span id="score_show">${user.score}</span>
			         	<shiro:hasPermission name="wms:user:addScore">
			         	<span><a id="add_score" href="#">给积分</a></span>
			         	<div id="add_score_div" style="display:none;margin-left:10px;">
				         	<input id="score_input" class="paddingLeft10" placeholder="充值的积分数(可为负数)"/>
				         	<a id="add_score_submit" class="layui-layer-btn0 paddingLeft10">提交</a>
				         	<a id="add_score_cancel" class="layui-layer-btn1 paddingLeft10">取消</a>
			         	</div>
			         	<div  id="add_score_spinner_div" style="display:none;margin-left:10px;color:red;"><i class='fa fa-spinner fa-spin'></i></div>
			         	</shiro:hasPermission>
			         </td>
			    </tr>
			    <tr>
			         <td><label class="pull-right">会员卡:</label></td>
			         <td colspan="3">
			         	<span></span>
			         	<span><a href="#">设置</a></span>
			         </td>
			    </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>
</html>