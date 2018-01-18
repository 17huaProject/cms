<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html style="overflow-x:hidden;overflow-y:hidden;">
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.ibox-title{background-color:#54A4D2;}
	.ibox-title h5{font-size:18px;color:white;}
	.ibox-title h6{display:inline-block;float:right;font-size:12px;color:white;margin:0 0 7px;clear:right;}
	.item-center{text-align:center;font-size: 14px;}
	.order-item{
		display:inline-block;
		font-size: 16px;  
		text-align:center;
		padding-top:35px;
		padding-bottom:40px;
	}
	.border-solid{border-right:1px solid #F3F3F4;}
	.number-font-size{font-size:48px;}
	.user-total-div{
		text-align: left; 
		height:64px; 
		padding-left:70px;
		margin-bottom: 10px; 
		background-repeat:no-repeat;
	}
	.icon-div{margin-bottom:10px;}
	</style>
	<script type="text/javascript">
		var event=false , venus=false  , artist=false  , order=false  , user=false  ;
		$(document).ready(function() {
		     WinMove();
		});
	</script>
	<script src="${ctxStatic}/common/contabs-subiframe.js"></script> 

</head>
<body class="gray-bg">

   <div class="row  border-bottom white-bg dashboard-header">
        <div class="col-sm-12">
            <span class="text-info" style="font-size:14px">
				嗨，欢迎到一起画后台管理系统！建议使用 Firefox 或 chrome 最新版浏览器。
            </span>
        </div>
    </div>
    <div class="wrapper wrapper-content">
        <div class="row">
        
            <shiro:hasPermission name="wms:order:index">
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>订单数据</h5>
                        <h6>
                        	更新时间：<span class="update-time"></span>
                        </h6>
                      <!--   <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div> -->
                    </div>
                    <div class="ibox-content item-center">
                    	<div class="order-item border-solid" style="width:20%">
                    		<div><span id="order_today_paidAmount" class="number-font-size"></span> 元</div>
                    		<div><span id="order_today"></span> 订单 </div>
                    		<div>今天订单</div>
                    	</div>
                    	<div class="order-item border-solid" style="width:25%">
                    		<div><span id="order_yesterday_paidAmount" class="number-font-size"></span> 元</div>
                    		<div><span id="order_yesterday"></span> 订单</div>
                    		<div>昨天订单</div>
                    	</div>
                    	<div class="order-item border-solid" style="width:25%">
                    		<div><span id="order_week_paidAmount" class="number-font-size"></span> 元</div>
                    		<div><span id="order_week"></span> 订单</div>
                    		<div>本周订单</div>
                    	</div>
                    	<div class="order-item" style="width:25%">
                    		<div><span id="order_month_paidAmount" class="number-font-size"></span> 元</div>
                    		<div><span id="order_month"></span> 订单</div>
                    		<div>本月订单</div>
                    	</div>
                    
                    <!-- 
                    	 <ol>
							<li>今天支付订单：<span id="order_today"></span> 订单 , <span id="order_today_paidAmount"></span> 元</li>
							<li>昨天支付订单：<span id="order_yesterday"></span> 订单 , <span id="order_yesterday_paidAmount"></span> 元</li>
							<li>本周支付订单：<span id="order_week"></span> 订单 , <span id="order_week_paidAmount"></span> 元</li>
							<li>本月支付订单：<span id="order_month"></span> 订单 , <span id="order_month_paidAmount"></span> 元</li>
                        </ol> -->
			        </div>
            	</div>
            </div>
            </shiro:hasPermission>
            <shiro:hasPermission name="wms:user:index">
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>会员 </h5>
                        <h6>
                        	更新时间：<span class="update-time"></span>
                        </h6>
                    </div>
                    <div class="ibox-content  item-center">
                    	<div class="order-item border-solid" style="width:45%;">
                    		<div class="user-total-div" style="background-image:url(/yqhCMS/static/common/img/home_icon_associator.png);">
                    		总注册会员：<br/>
                    		<span id="user_total" style="font-size:36px;"></span> 人
                    		</div>
                    		<div>今日注册会员：<span id="user_todayNum"></span> 人</div>
                    	</div>
                    	<div class="order-item" style="width:45%;padding-left:20px;">
                    		<div class="user-total-div" style="background-image:url(/yqhCMS/static/common/img/home_icon_proposal.png);">
                    		意见反馈：<br/>
                    		<span id="user_feedback" style="font-size:36px;"></span> 条
                    		</div>
                            <shiro:hasPermission name="wms:user:feedback:edit">
                    		<div>待处理：<a class="J_menuItem"  href="${ctx}/wms/user/feedback/index?status=0" data-title="意见反馈"><span id="user_feedback_uncheck"></span> 条</a></div> 
                        	</shiro:hasPermission>
                    	</div>
                     
                    <%-- 
                        <ol>
                            <li>总注册会员：<span id="user_total"></span> 人</li>
                            <li>今日注册会员：<span id="user_todayNum"></span> 人</li>
                            <shiro:hasPermission name="wms:user:feedback:edit">
                            <li>待处理意见反馈：<a class="J_menuItem"  href="${ctx}/wms/user/feedback/index?status=0" data-title="意见反馈"><span id="user_feedback"></span> 待处理</a></li>
                        	</shiro:hasPermission>
                        </ol> --%>
                    </div>
                </div>
            </div>
            </shiro:hasPermission>      
        </div>
        <div class="row">
            <shiro:hasPermission name="wms:event:index">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>活动</h5>
                        <h6>
                        	<i class="fa fa-spinner"  aria-hidden="true"></i>
                        	<span class="update-time"></span>
                        </h6> 
                    </div>
                    <div class="ibox-content item-center">
                   		<div class="icon-div"><img alt="" src="/yqhCMS/static/common/img/home_icon_activity.png"></div>
                       	<shiro:hasPermission name="wms:event:audit">
                   		<div>
                   			待审核的活动：<a class="J_menuItem"  href="${ctx}/wms/event/index?isCheck=0" data-title="活动管理"><span id="event_uncheck"></span>待审核</a>
                   		</div>
						</shiro:hasPermission>
                   		<div>
                   			销售中的活动：<span id="event_ONSALE"></span>销售中
                   		</div>
                   		<div>
                   			已结束的活动：<span id="event_FINISH"></span>已结束
                   		</div>
                       	<shiro:hasPermission name="wms:custom:edit">
                   		<div>
                   			待处理的定制活动：<a class="J_menuItem"  href="${ctx}/wms/custom/index?status=0" data-title="定制管理"><span id="custom_uncheck"></span>待处理</a>
                   		</div>
						</shiro:hasPermission>
                    <%-- 
                        <ol> 
                        	<shiro:hasPermission name="wms:event:audit">
							<li>待审核的活动：<a class="J_menuItem"  href="${ctx}/wms/event/index?isCheck=0" data-title="活动管理"><span id="event_uncheck"></span>待审核</a></li>
							</shiro:hasPermission>
							<li>销售中的活动：<span id="event_ONSALE"></span>销售中</li>
							<li>已结束的活动：<span id="event_FINISH"></span>已结束</li>
                        	<shiro:hasPermission name="wms:custom:edit">
							<li>待处理的定制活动：<a class="J_menuItem"  href="${ctx}/wms/custom/index?status=0" data-title="定制管理"><span id="custom_uncheck"></span>待处理</a></li>
							</shiro:hasPermission>
                        </ol>
                         --%>
                    </div>
                </div>
            </div>
            </shiro:hasPermission>
        
        	<shiro:hasPermission name="wms:artist:index">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>艺术指导</h5> 
                        <h6>
                        	<i class="fa fa-spinner"  aria-hidden="true"></i>
                        	<span class="update-time"></span>
                        </h6> 
                    </div>
                    <div class="ibox-content item-center">
                    	<div class="icon-div"><img alt="" src="/yqhCMS/static/common/img/home_icon_leaguer.png"></div>
                       	<shiro:hasPermission name="wms:artist:audit">
                   		<div>
                   			待审核画师：<a class="J_menuItem"  href="${ctx}/wms/artist/index?isCheck=0" data-title="画师管理"><span id="artist_uncheck"></span> 位</a>
                   		</div>
                       	</shiro:hasPermission>
                       	<div>
                   			已审核画师：<a class="J_menuItem"  href="${ctx}/wms/artist/index?isCheck=1" data-title="画师管理"><span id="artist_checked"></span> 位</a>
						</div>
					    <div>&nbsp;</div>
					    <div>&nbsp;</div>
                       <%--  <ol>
                        	<shiro:hasPermission name="wms:artist:audit">
							<li>待审核画师：<a class="J_menuItem"  href="${ctx}/wms/artist/index?isCheck=0" data-title="画师管理"><span id="artist_uncheck"></span> 待审核</a></li>
                        	</shiro:hasPermission>
                        </ol> --%>
                    </div>
                </div>
             </div>
             </shiro:hasPermission>
             <shiro:hasPermission name="wms:venues:index">
             <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>艺术空间</h5> 
                        <h6>
                        	<i class="fa fa-spinner"  aria-hidden="true"></i>
                        	<span class="update-time"></span>
                        </h6> 
                    </div>
                    <div class="ibox-content item-center">
                    	<div class="icon-div"><img alt="" src="/yqhCMS/static/common/img/home_icon_place.png"></div>
                       	<shiro:hasPermission name="wms:venues:audit">
                   		<div>
                   			待审核空间：<a class="J_menuItem"  href="${ctx}/wms/venue/index?isCheck=0" data-title="场地管理"><span id="venues_uncheck"></span> 个</a>
                   		</div>
                       	</shiro:hasPermission>
                   		<div>
                   			已审核空间：<a class="J_menuItem"  href="${ctx}/wms/venue/index?isCheck=1" data-title="场地管理"><span id="venues_checked"></span> 个</a>
                   		</div>
					    <div>&nbsp;</div>
					    <div>&nbsp;</div>                   
                        <%-- <ol>
                        	<shiro:hasPermission name="wms:venues:audit">
							<li>待审核场地：<a class="J_menuItem"  href="${ctx}/wms/venue/index?isCheck=0" data-title="场地管理"><span id="venues_uncheck"></span> 待审核</a></li>
            				</shiro:hasPermission>
                        </ol> --%>
                    </div>
                </div>
            </div>
            </shiro:hasPermission>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>画作</h5> 
                        <h6>
                        	<i class="fa fa-spinner"  aria-hidden="true"></i>
                        	<span class="update-time"></span>
                        </h6> 
                    </div>
                    <div class="ibox-content item-center">
                    	<div class="icon-div"><img alt="" src="/yqhCMS/static/common/img/home_icon_paint.png"></div>
                   		<div>
                   			画作总数：<span id="art_total"></span> 幅
                   		</div>
                   		<div>&nbsp;</div>
					    <div>&nbsp;</div>
					    <div>&nbsp;</div>
                    </div>
                </div>
            </div>
         </div>
    </div>

<shiro:hasPermission name="wms:event:index">		
 <script type="text/javascript">
 	event = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/event/worktable/getEventInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
				}
	   });
	});
 </script>
</shiro:hasPermission>
<shiro:hasPermission name="wms:venues:audit">
 <script type="text/javascript">
 	venus = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/venue/worktable/getVenusInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
				}
	   });
	});
 </script>
</shiro:hasPermission>
<shiro:hasPermission name="wms:artist:audit">
 <script type="text/javascript">
 	artist = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/artist/worktable/getArtistInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
				}
	   });
	});
 </script>
</shiro:hasPermission>
<shiro:hasPermission name="wms:order:index">
 <script type="text/javascript">
 	order = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/order/worktable/getOrderInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
					$("#"+data[index].owner + "_" + data[index].type + "_paidAmount").html(data[index].sumPaidAmount);
				}
	   });
	});
 </script>
</shiro:hasPermission>
<shiro:hasPermission name="wms:user:index">
 <script type="text/javascript">
 	user = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/user/worktable/getUserInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
				}
	   });
	});
 </script>
</shiro:hasPermission>
<shiro:hasPermission name="wms:event:view">
 <script type="text/javascript">
 	art = true ;
	$(document).ready(function() {
		$.post("${ctx}/wms/art/worktable/getArtInfo",
		   function(data){
				for(var index = 0,length = data.length ; index < length ; index++){
					$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
				}
	   });
	});
 </script>
</shiro:hasPermission>

 <script type="text/javascript">
	 $(document).ready(function() {
		 currentTime(); 
		 
	 });
	 
	 
 	var timer = $.timer(function() {
 		if (event == true) {
 			$.post("${ctx}/wms/event/worktable/getEventInfo",
			   function(data){
					for(var index = 0,length = data.length ; index < length ; index++){
						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
					}
		   });
 	 	}
 	 	if (venus == true) {
 	 		$.post("${ctx}/wms/venue/worktable/getVenusInfo",
 			   function(data){
 					for(var index = 0,length = data.length ; index < length ; index++){
 						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
 					}
 		   });
 	 	}
 	 	if (artist == true) {
 	 		$.post("${ctx}/wms/artist/worktable/getArtistInfo",
 			   function(data){
 					for(var index = 0,length = data.length ; index < length ; index++){
 						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
 					}
 		   });
 	 	}
 	 	if (order == true) {
 	 		$.post("${ctx}/wms/order/worktable/getOrderInfo",
 			   function(data){
 					for(var index = 0,length = data.length ; index < length ; index++){
 						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
 						$("#"+data[index].owner + "_" + data[index].type + "_paidAmount").html(data[index].sumPaidAmount);
 					}
 		   });
 	 	}
 	 	if (user == true) {
 	 		$.post("${ctx}/wms/user/worktable/getUserInfo",
 			   function(data){
 					for(var index = 0,length = data.length ; index < length ; index++){
 						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
 					}
 		   });
 	 	}
 	 	if (art == true) {
 	 		$.post("${ctx}/wms/art/worktable/getArtInfo",
 			   function(data){
 					for(var index = 0,length = data.length ; index < length ; index++){
 						$("#"+data[index].owner + "_" + data[index].type).html(data[index].count);
 					}
 		   });
 	 	}
 	 	
 	 	
 	 	currentTime();
	});
 	//timer.set({ time : 30000000, autostart : true });
 	timer.set({ time : 1000*60*10, autostart : true });
 	
 	function currentTime() {
		var eventTime = new Date();
		var hours = eventTime.getHours();
		var minutes = eventTime.getMinutes();
		var seconds = eventTime.getSeconds();
		if (hours<10) { hours = "0" + hours}
		if (minutes<10) { minutes = "0" + minutes}
		if (seconds<10) { seconds = "0" + seconds}
		
		if ( !isNaN(hours) && !isNaN(minutes) && !isNaN(seconds)) {
			var closingTimeStr = hours + ':' + minutes + ':' + seconds ; 
			$(".update-time").html(closingTimeStr);
		}
		
	}
 
 
 </script>
</body>

</html>











