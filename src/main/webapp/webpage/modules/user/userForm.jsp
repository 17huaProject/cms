<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>基本信息</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.dataTable tbody tr td label {margin-bottom: 0px;}
	</style>
	
</head>
<body>
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
		         <td>
					<shiro:hasPermission name="wms:user:phone">
					${user.phone}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:phone">
					${fn:substring(user.phone,0,3)}*****${fn:substring(user.phone,8,11)}
					</shiro:lacksPermission>
				 </td>
		         <td ><label class="pull-right">身份证号:</label></td>
		         <td>
					<shiro:hasPermission name="wms:user:idcard">
					${user.idcard}
					</shiro:hasPermission>
					<shiro:lacksPermission name="wms:user:idcard">
					${fn:substring(user.idcard,0,3)}*****${fn:substring(user.idcard,fn:length(user.idcard)-4,fn:length(user.idcard))}
					</shiro:lacksPermission>
				</td>
		    </tr>
		   	<tr>
		         <td ><label class="pull-right">客户类型:</label></td>
		         <td>${fns:getDictLabel(user.userType,'customer_type','')}</td>
		         <td ><label class="pull-right">客户状态:</label></td>
		         <td>${fns:getDictLabel(user.status,'user_status','')}</td>
		    </tr>
		   <!--  
		   	<tr>
		         <td colspan="4" class="active"><label class="pull-left">消费信息</label></td>
		    </tr>
		     -->
		    <tr>
		         <td colspan="4" class="active"><label class="pull-left">资产信息</label></td>
		    </tr>
		    <tr>
		         <td><label class="pull-right">账户余额:</label></td>
		         <td>￥${user.balance}</td>
		    </tr>
		    <tr>
		         <td><label class="pull-right">积分:</label></td>
		         <td colspan="3">
		         	<span>${user.score}</span>
		         </td>
		    </tr>
		    <tr>
		         <td><label class="pull-right">会员卡:</label></td>
		         <td colspan="3">
		         	<span></span>
		         </td>
		    </tr>
		    
		    <tr>
		         <td colspan="4" class="active"><label class="pull-left">登录信息</label></td>
		    </tr>
		    <tr>
		         <td><label class="pull-right">注册时间:</label></td>
		         <td><fmt:formatDate value='${user.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
		         <td><label class="pull-right">登录次数:</label></td>
		         <td>${user.loginNum}</td>
		    </tr>
		    <tr>
		         <td><label class="pull-right">最后登录时间:</label></td>
		         <td><fmt:formatDate value='${user.lastTime}' pattern='yyyy-MM-dd HH:mm:ss'/> </td>
		         <td><label class="pull-right">最后登录IP:</label></td>
		         <td>${user.lastip}</td>
		    </tr>
		    
	 </tbody> 
	 </table> 
</body>
</html>