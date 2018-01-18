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
	  	.font-highlight{color:red}
 		 .width80{ width:80%;}
  		.ui-helper-hidden-accessible{display: block; width:100%;}
  		#shareDescList{display: block;z-index:9999;position:absolute;background-color:white;width:80%}
  		#shareDescList ul li{height:20px;line-height:20px;}
  
  		#freeService span {display:inline-block; margin-right:10px;font-weight:normal;}
  		#freeService input , #isRefundSelect input{margin-top:0px;width:auto;}
  		#freeService label , #isRefundSelect label{margin-bottom:0px;margin-left:4px;margin-right:10px;font-weight:normal;}
  		#isRefundSelect input{margin-top:-4px;}
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
		$("#name").focus();
		$("#cityCode").attr("disabled" , "disabled");
		
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
		
		//：城市
		$("#provinceCode").change(function(){
			var provinceCode = $(this).val();
			$("#address").val("");
			$("#address").attr("disabled","disabled");
			$.post("${ctx}/wms/region/city/list", { provinceCode: provinceCode},
			   function(data){
					var cityObj = jQuery.parseJSON(data);
			   		console.log(data);
			   		if(cityObj.code == 0){
				   		var cities = cityObj.data;
					   	$("#cityCode").empty();
				   		if(cities.length == 0){
				   			$("#cityCode").append("<option selected=\"selected\" value=\"\">无开通城市</option>");
				   		}else{
				   			//$("#cityCode").append("<option selected=\"selected\" value=\"\">请选择市</option>");
					   		for(var i=0; i<cities.length ;i++){
					   			$("#cityCode").append("<option value=\""+cities[i].code+"\">"+cities[i].name+"</option>");
					   		}
					   		$("#cityCode").removeAttr("disabled");
				   		}
			   		}else{
			   			console.log("城市查询出错！");
			   		}
		   });
		});
		
		$("#cityCode").change(function(){
			if($.trim($(this).val()) == "") return ;
			var province = $("#provinceCode").find("option:selected").text() ;
			var city = $(this).find("option:selected").text() ;
			$("#city").val(city);
		});
		//：~ 城市
		
		//远程校验
		$("#artist-autocomplete").focusout(function(){
			removeVerify("artist");
		});
		
		$("#artist-autocomplete").autocomplete({
			 source: function( request, response ) {
				 $.post("${ctx}/wms/artist/list4AutoComplete", { searchParam: request.term},
				    function(data){
				   		if(data.code == 0){
				   			var artists = data.data ;
				   			if(artists != null){
				   				response(artists );	
				   			}else{
				   				
				   			}
				   		}
				   		if(data.code == 1101){
				   			console.log("参数不可为空");
				   		}
				  },"json");
			 },
			 minLength: 2,
			 focus: function( event, ui ) {
				return false;
			 },
		     select: function( event, ui ) {
				$( "#artist-autocomplete" ).val( ui.item.realname );
		    	$( "#artistId" ).val( ui.item.id );
		    	$(".ui-helper-hidden-accessible").text("");
		    	return false;
		     },
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  var searchParam = $("#artist-autocomplete").val();
			  var searchReg = new RegExp(searchParam, 'g') ;
			  var valReplaced = "<span class='font-highlight'>"+searchParam+"</span>" ;
			  
		      return $( "<li>" ).append(  
		        	  	 "<a>"+ 
		        	  	 	"<div>"+ 
		        	  	 		item.artistName.replace(searchReg,valReplaced)   + "&nbsp;&nbsp;(&nbsp;&nbsp;"+
		        	  	 		item.realname.replace(searchReg,valReplaced) + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
		        	  	 		item.city.replace(searchReg,valReplaced) + "&nbsp;&nbsp;)&nbsp;&nbsp;"+
		        	  	 	"</div>"+
		        			"<div>"+ 
		        				"手机号：" + item.phone.replace(searchReg,valReplaced) + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
		        				"身份证号：" + item.idcard.replace(searchReg,valReplaced) +
		        			"</div>"+
		        		 "</a>"
						)
		        .appendTo( ul );
		    };
		
		//远程校验
		$("#art-autocomplete").focusout(function(){
			removeVerify("art");
		});
		
		$("#art-autocomplete").autocomplete({
			 source: function( request, response ) {
				 $.post("${ctx}/wms/art/list4AutoComplete", { searchParam: request.term},
				    function(data){
				   		if(data.code == 0){
				   			var arts = data.data ;
				   			if(arts != null){
				   				response( arts );	
				   			}else{
				   				
				   			}
				   		}
				   		if(data.code == 1101){
				   			console.log("参数不可为空");
				   		}
				  },"json");
			 },
			 minLength: 2,
			 focus: function( event, ui ) {
				return false;
			 },
		     select: function( event, ui ) {
				$( "#art-autocomplete" ).val( ui.item.artName );
		    	$( "#artId" ).val( ui.item.id );
		    	$(".ui-helper-hidden-accessible").text("");
		    	$(".keywordsTags").importTags(ui.item.note);
		    	return false;
		     },
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  var searchParam = $("#art-autocomplete").val();
			  var searchReg = new RegExp(searchParam, 'g') ;
			  var valReplaced = "<span class='font-highlight'>"+searchParam+"</span>" ;
			  
		      return $( "<li>" ).append(  
		        	  	 "<a>"+ 
		        	  	 	"<img src='"+ item.bigImg +"!80.40' class='art-img'>"+
		        	  	 	"<div class='art-div-contain'>"+ 
			        	  	 	"<div>"+ 
			        	  	 		item.artName.replace(searchReg,valReplaced) +
			        	  	 	"</div>"+
			        	  	 	"<div>"+ 
		        					"类型：" + item.type + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
		        					"难度：" + item.easyLevel +
	        					"</div>"+
        					"</div>"+
        					" <div style='clear:both'></div>"+
		        		 "</a>"
						)
		        .appendTo( ul );
		    };
		    
	    //远程校验
		$("#venue-autocomplete").focusout(function(){
			removeVerify("venue");
		});    
		$("#venue-autocomplete").autocomplete({
			 source: function( request, response ) {
				 $.post("${ctx}/wms/venue/list4AutoComplete", { searchParam: request.term},
				    function(data){
				   		if(data.code == 0){
				   			var venues = data.data ;
				   			if(venues != null){
				   				response( venues );	
				   			}else{
				   				
				   			}
				   		}
				   		if(data.code == 1101){
				   			console.log("参数不可为空");
				   		}
				  },"json");
			 },
			 minLength: 2,
			 focus: function( event, ui ) {
				return false;
			 },
		     select: function( event, ui ) {
				$( "#venue-autocomplete" ).val( ui.item.venueName );
		    	$( "#venueId" ).val( ui.item.id );
		    	$(".ui-helper-hidden-accessible").text("");
		    	return false;
		     },
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  var searchParam = $("#venue-autocomplete").val();
			  var searchReg = new RegExp(searchParam, 'g') ;
			  var valReplaced = "<span class='font-highlight'>"+searchParam+"</span>" ;
			  
		      return $( "<li>" ).append(  
		        	  	 "<a>"+ 
		        	  	 	"<div>"+ 
			        	  	 	item.venueName.replace(searchReg,valReplaced) + "&nbsp;&nbsp;(&nbsp;&nbsp;"+
		        	  	 		item.capacity + "人 &nbsp;&nbsp;,&nbsp;&nbsp;"+ 
		        	  	 		item.city + "&nbsp;&nbsp;)&nbsp;&nbsp;"+
		        	  	 	"</div>"+
		        	  	 	"<div>"+ 
	        					"地址：" + item.address + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
	        					"联系人：" + item.contact + "&nbsp;&nbsp;,&nbsp;&nbsp;"+
	        					item.phone + 
	        				"</div>"+
		        		 "</a>"
						)
		        .appendTo( ul );
		    };
		    
	    //远程校验
		$("#assistant-autocomplete").focusout(function(){
			var val = $.trim( $(this).val() );
			console.log(val);
			if (val == null || val == "") {
				return ;
			}
			removeVerify("assistant");
		});  
		//助教
		$("#assistant-autocomplete").autocomplete({
			 source: function( request, response ) {
				 $.post("${ctx}/sys/user/list4AutoComplete", { searchParam: request.term},
				    function(data){
				   		if(data.code == 0){
				   			var assistants = data.data ;
				   			if(assistants != null){
				   				response( assistants );	
				   			}else{
				   				
				   			}
				   		}
				   		if(data.code == 1101){
				   			console.log("参数不可为空");
				   		}
				  },"json");
			 },
			 minLength: 2,
			 focus: function( event, ui ) {
				return false;
			 },
		     select: function( event, ui ) {
				$( "#assistant-autocomplete" ).val( ui.item.name );
		    	$( "#assistantId" ).val( ui.item.id );
		    	$(".ui-helper-hidden-accessible").text("");
		    	return false;
		     },
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  var searchParam = $("#assistant-autocomplete").val();
			  var searchReg = new RegExp(searchParam, 'g') ;
			  var valReplaced = "<span class='font-highlight'>"+searchParam+"</span>" ;
			  
		      return $( "<li>" ).append(  
		        	  	 "<a>"+ 
		        	  	 	"<div>"+ 
			        	  	 	item.name.replace(searchReg,valReplaced) +
		        	  	 	"</div>"+
		        	  	 	"<div>"+ 
	        					"手机：" + item.mobile.replace(searchReg,valReplaced) + "&nbsp;&nbsp;,&nbsp;&nbsp;"+ 
	        					"电话：" + item.phone.replace(searchReg,valReplaced) + 
	        				"</div>"+
		        		 "</a>"
						)
		        .appendTo( ul );
		    };
		    
		$(".keywordsTags").tagsInput({
		   'height':'auto',
		   'width':'auto',
		   'interactive':false,
		   'defaultText':'添加标签',
		   'unique':true,
		   'delimiter': [',',';',' '],  
		   'removeWithBackspace' : true,
		   'minChars' : 2,
		   //'maxChars' : 0, // if not provided there is no limit
		   'placeholderColor' : '#666666',
		   //'onAddTag':onAddTag,
		   //'onRemoveTag':onRemoveTag,
/* 			   onAdd: function(tag){
			   console.log(tag);
			   if ($('.keywordsTags').tagExist(tag)) return ;
		   },
*/			   onChange: function(elem, elem_tags){
			   var keywords = "";
				$('#0_tagsinput .tag').each(function(){
					keywords = keywords + $.trim($(this).find("span").text()) + ',' ;
				});
				$(".keywordsTags").attr("type","hidden");
				$(".keywordsTags").removeAttr("display");
				$(".keywordsTags").val(keywords);
			}
		});
		
		//报名截止时间为活动时间推迟2小时
		$("#eventTime").focusout(function() {
			var eventTimeStr = $(this).val(); //2017-10-11 18:30:00
			var eventTime = new Date(Date.parse(eventTimeStr.replace(/-/g, "/")));
			eventTime.setHours( eventTime.getHours() + 2 )  ;
			
			var year = eventTime.getFullYear();
			var month = eventTime.getMonth() + 1;
			var date = eventTime.getDate();
			var hours = eventTime.getHours();
			var minutes = eventTime.getMinutes();
			var seconds = eventTime.getSeconds();
			if (month<10) { month = "0" + month}
			if (date<10) { date = "0" + date}
			if (hours<10) { hours = "0" + hours}
			if (minutes<10) { minutes = "0" + minutes}
			if (seconds<10) { seconds = "0" + seconds}
			
			if (!isNaN(year) && !isNaN(month) && !isNaN(date) && !isNaN(hours) && !isNaN(minutes)) {
				var closingTimeStr = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds ; 
				$("#closingTime").val(closingTimeStr);
			}
			
		});
		
		//画师筛选
	   $("#artist-search").click( function(){ 
		  var url = "${ctx}/wms/artist/artistList4Event";
		  windowOpen(url,"画师筛选",1200,700);
	   });
		//作品筛选
	   $("#art-search").click( function(){ 
		  var url = "${ctx}/wms/art/artList4Event";
		  windowOpen(url,"作品筛选",1100,700);
	   });
		//场所筛选
	   $("#venue-search").click( function(){ 
		  var url = "${ctx}/wms/venue/venueList4Event";
		  windowOpen(url,"场所筛选",1200,700);
	   });
		//助教筛选
	   $("#assistant-search").click( function(){ 
		  var url = "${ctx}/sys/user/userList4Event";
		  windowOpen(url,"助教筛选",1280,700);
	   });
		
		//分享标题
		$("#shareDesc").click(function(){
			$("#shareDescList").css("display","block");
		});
		$("#shareDescList a").click(function(){
			var shareDesc = $(this).html();
			$("#shareDesc").val(shareDesc) ;
			$("#shareDescList").css("display","none");
		});
			
		$("#venueFeeRate").focusout(function(){
			var venueFeeRate = $(this).val();
			var price = parseInt($("#price").val()) ;
			var capacity = parseInt($("#capacity").val()) ;
			
			if (venueFeeRate > 0 && venueFeeRate < 1) {
				if (price > 0 && capacity > 0) {
					var venueFee = capacity * price * venueFeeRate  ;
					$("#venueFee").val(venueFee);
				} else {
					alert("价格和人数不能为空");
				}
			}
		});
		
		//实例化编辑器ueditor
		var ue = UE.getEditor('content');
		//对编辑器的操作最好在编辑器ready之后再做
		ue.ready(function() {});
	});
	
	//添加错误提示
	function addErrorHint(type){
		$("#"+type+"-error").remove();
		$("#"+type+"-autocomplete").after("<label id='"+type+"-error' class='error' for='"+type+"'>请从提示列表中选择</label>");
   		$("#"+type+"-autocomplete").addClass("error");
   		$("#"+type+"-autocomplete").val("");
   		$("#"+type+"Id").val("");
	}
	
	//移除错误提示
	function removeErrorHint(type){
		$("#"+type+"-error").remove();
   		$("#"+type).removeClass("error");
	}
	
	  //远程校验
	function removeVerify(type){
		var id = $.trim( $( "#"+type+"Id" ).val() );
		var searchParam = $.trim( $("#"+type+"-autocomplete").val() );
		if (id == "" || searchParam == "") {
   			addErrorHint(type) ;
   			return ;
		}
		
		var requestUrl ;
		if (type == "assistant") {
			requestUrl = "${ctx}/sys/user/verifyRemote4EventBind";
		}else{
			requestUrl = "${ctx}/wms/"+type+"/verifyRemote4EventBind";
		}
		
		$.post(requestUrl, {id:id , searchParam:searchParam },
		    function(data){
		   		if(data == false){
		   			$( "#"+type+"Id" ).val("");
		   			addErrorHint(type) ;
		   		} else {
		   			removeErrorHint(type) ;
		   		}
		  },"json");
	};
	//标签、关键字 自动填充，供子窗口调用
    function fillTags(tags){
    	$("input[name='keywords']").importTags(tags);
    };

	</script>
    	
</head>
<body>
	<form:form id="inputForm" action="${ctx}/wms/event/audit" commandName="event" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   	 <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>活动名称:</label></td>
		         <td><form:input path="eventName" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>活动类型:</label></td>
		         <td>
			         <form:select path="type"  class="form-control required">
						<form:options items="${fns:getDictList('event_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>价格:</label></td>
		         <td><form:input path="price" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>允许退款:</label></td>
		         <td id="isRefundSelect">
					<form:radiobutton path="isRefund" value="1"/><label>是</label>
	               	<form:radiobutton path="isRefund" value="0"/><label>否</label>
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>活动人数:</label></td>
		         <td><form:input path="capacity" htmlEscape="false" maxlength="50" class=" form-control  required"/></td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>活动状态:</label></td>
		         <td>
			         <form:select path="eventStatus"  class="form-control required">
						<form:options items="${fns:getDictList('event_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>		         
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>城市:</label></td>
		         <td colspan="3">
					 <form:select path="provinceCode"  class=" required">
						<!-- <option selected="selected" value="">请选择省份</option> -->
						<form:options items="${fns:getProvinceList()}" itemLabel="name" itemValue="code" htmlEscape="false"/>
					 </form:select>
					 <form:select path="cityCode"  class="required">
						<!-- <option value="">请选择市</option> -->
						<form:options items="${cityInfo}"/>
					 </form:select>
				 </td>
		      </tr>
		      <tr> 
		         <td class="active"><label class="pull-right"><font color="red">*</font>活动时间:</label></td>
		         <td><input  name="eventTime" id="eventTime" maxlength="50" class=" form-control required" 
		         		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"
		         		value="<fmt:formatDate value='${event.eventTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
		         		
		         </td>
		         <td class="active"><label class="pull-right"><font color="red">*</font>报名截止时间:</label></td>
		         <td>
		         	<input id="closingTime" name="closingTime" maxlength="50" class=" form-control required" 
		         		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,startDate:'%y-%M-01 00:00:00',alwaysUseStartDate:true})"
		         		value="<fmt:formatDate value='${event.closingTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
		         </td>
		      </tr>
 
		      <tr>
	      		 <td class="active"><label class="pull-right">免费服务:</label></td>
		         <td colspan=3 id="freeService"><form:checkboxes path="freeServices" items="${fns:getDictList('free_service')}"  itemLabel="label" itemValue="value" class=" form-control"/></td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right"><font color="red">*</font>作品:</label></td>
		         <td colspan=3>
		         	<form:hidden path="artId"/>
		         	<input id="art-autocomplete" value="${art.artName}" placeholder="请输入作品名"  maxlength="50" class="form-control width80 required ">
		         	<a id="art-search" class="btn btn-info btn-xs" href="#"><i class="fa fa-search-plus"></i> 查找</a>
		         </td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right"><font color="red">*</font>画师:</label></td>
		         <td colspan=3>
		         	<form:hidden path="artistId"/>
		         	<input id="artist-autocomplete" value="${artist.realname}"  placeholder="请输入姓名/手机号/身份证号" maxlength="50" class="form-control width80 required">
		         	<a id="artist-search" class="btn btn-info btn-xs" href="#"><i class="fa fa-search-plus"></i> 查找</a>
		         </td>
			  </tr>  
		      <tr>
	      		 <td class="active"><label class="pull-right">助教:</label></td>
		         <td colspan=3>
		         	<form:hidden path="assistantId"/>
		         	<input id="assistant-autocomplete" value="${user.name}" placeholder="请输入姓名/手机号" maxlength="50" class="form-control width80">
		         	<a id="assistant-search" class="btn btn-info btn-xs" href="#"><i class="fa fa-search-plus"></i> 查找</a>
		         </td>
			  </tr> 
		      <tr>
	      		 <td class="active"><label class="pull-right"><font color="red">*</font>场所:</label></td>
		         <td colspan=3>
		         	<form:hidden path="venueId"/>
		         	<input id="venue-autocomplete" value="${venue.venueName}" placeholder="请输入场所名" maxlength="50" class="form-control width80 required">
		         	<a id="venue-search" class="btn btn-info btn-xs" href="#"><i class="fa fa-search-plus"></i> 查找</a>
		         </td>
			  </tr> 
			  <tr>
		         <td class="active"><label class="pull-right">商家佣金费率:</label></td>
		         <td>
		         	<form:input path="venueFeeRate" number='true' min="0" max="1" htmlEscape="false" class=" form-control"/>
		         	<span style="color:green; width:100%;">范围(0,1], 1:固定金额 , (0,1):佣金费率</span>
		         </td>
		         <td class="active"><label class="pull-right">商家佣金:</label></td>
		         <td>
		         	<form:input path="venueFee" number='true' htmlEscape="false" class=" form-control"/>
		         	<span style="color:green; width:100%;">费率:1, 填写固定金额 ; 费率:(0,1), 佣金=价格*人数*费率</span>
		         </td>
		      </tr>
			   
			  <tr>
	      		 <td class="active"><label class="pull-right"><font color="red">*</font>活动标签:</label></td>
		         <td colspan=3>
		         	<input id="0" name="keywords" value="${event.keywords}" maxlength="50" class=" form-control keywordsTags required"/>
		         	<span style="color:green; width:100%;">该标签为作品标签,且不可手动添加,选定作品后将自动填充</span>
<!-- 		         <span style="color:green; width:100%;">每个标签至少2个字,按空格键或enter键完成添加</span> -->
		         </td>
			  </tr>  
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>简介:</label></td>
		         <td colspan=3>
		         	<form:textarea path="eventDesc" htmlEscape="false" rows="3" maxlength="50" class="form-control required"/>
		         	<div style="color:green; width:100%;">
						注意：字数不超过50字
					</div>
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>详情:</label></td>
		         <td colspan=3>
					<%-- <form:textarea path="content" class="form-control required" type="hidden" htmlEscape="false"/>		         	
					<sys:ckeditor replace="content" uploadPath="/event/content" />	 --%>
					<script id="content" name="content" type="text/plain" style="width:100%;height:300px;" >${event.content}</script>
					<div style="color:green; width:100%;">注意：·图片&lt;2M, ·视频&lt;100MB, ·文件&lt;50MB</div>
				 </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">分享标题:</label></td>
		         <td colspan=3>
		         	<form:input path="shareDesc" htmlEscape="false" maxlength="20" class="form-control"/>
		         	<div id="shareDescList" style="display:none;">
		         		<ul>
			         		<c:forEach items="${shareDescList}" var="eachDesc" >
			         			<li><a>${eachDesc}</a></li>
			         		</c:forEach>
		         		</ul>
		         	</div>
		         </td>
		      </tr>
		      <tr>
		         <td class="active"><label class="pull-right">备注:</label></td>
		         <td colspan=3><form:textarea path="remark" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
		 </tbody> 
		 </table> 
	</form:form>
</body>

</html>