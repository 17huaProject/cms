<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<link rel="icon" href="http://image.17hua.me/upload/image/201709/201709180100.ico" type="image/x-icon" />

		<title>${fns:getConfig('productName')} 登录</title>
		<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
		</script>
    <style>
        body{
            margin: 0;
            background: url("http://image.17hua.me/upload/image/201709/1505213032661885.jpg") no-repeat;
            background-size:100% 100%;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-align: center;
            -webkit-align-items: center;
            -ms-flex-align: center;
            align-items: center;
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            -ms-flex-pack: center;
            justify-content: center;
            min-height: 100vh;
        }
        #main_box{
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            box-shadow: 0 4px 20px 2px #4a4a4a;
        }
        #img_area{
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-orient: vertical;
            -webkit-flex-direction: column;
            -ms-flex-direction: column;
            flex-direction: column;
        }
        #lo_lg{
            position: relative;
            margin:10px 0  -60px 40px;
            width: 220px;
        }
        #input_area{
            width: 450px;
            height: 490px;
            background-color: #ffffff;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-orient: vertical;
            -webkit-flex-direction: column;
            -ms-flex-direction: column;
            flex-direction: column;
        }
        .input_line{
            float: left;
            width: 60%;
            padding: 0 20%;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-align: center;
            -webkit-align-items: center;
            -ms-flex-align: center;
            align-items: center;
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            -ms-flex-pack: center;
            justify-content: center;
        }
        .title_name{
            height: 80px;
            font-size: 20px;
        }
        .title_name div{
            width: 100%;
            color: #333333;
        }
        .input_text{
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-orient: vertical;
            -webkit-flex-direction: column;
            -ms-flex-direction: column;
            flex-direction: column;
        }
        .input_text div{
            width: 98%;
            font-size: 16px;
            color: #666666;
            padding-left: 2%;
        }
        .input_text input{
            margin-top: 10px;
            width: 90%;
            font-size: 16px;
            height: 36px;
            line-height: 36px;
            color: #666666;
            outline: none;
            -webkit-border-radius: 50px;
            -o-border-radius: 50px;
            border-radius: 50px;
            padding: 0 5%;
            border: 0;
            box-shadow: 0 0 0 1px #999999;
        }
        .input_text img{
            margin-top: -33px;
            margin-bottom: 3px;
            margin-left: 88%;
            width: 20px;
            height: 20px;
            padding: 5px 0;
            cursor: pointer;
        }
        .remenber_me{
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-justify-content: flex-start;
            -ms-flex-pack: start;
            justify-content: flex-start;
        }
        .remenber_me div{
            float: left;
            width: 16px;
            height: 16px;
            box-shadow: 0 0 0 1px #999999;
            margin-left: 2%;
        }
        .remenber_me span{
            float: left;
            margin-left: 10px;
            font-size: 12px;
            color: #666666;
        }
        .buttom_login button{
            background-color: #ff604f;
            color: #ffffff;
            float: left;
            width: 100%;
            line-height: 36px;
            height: 36px;
            -webkit-border-radius: 50px;
            -o-border-radius: 50px;
            border-radius: 50px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
        }
        .marginTop20{
            margin-top: 20px;
        }
        .marginTop40{
            margin-top: 40px;
        }
        #bottom_area_contral{
            width: 410px;
            padding: 0 20px;
            margin-left: -450px;
            margin-top: 450px;
            height: 40px;
            font-size: 16px;
            color: #333333;
            line-height: 40px;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -webkit-justify-content: space-between;
            justify-content: space-between;
        }
        #bottom_area_contral div:hover{
            text-decoration: underline;
            cursor: pointer;
        }
        .securityCode{float:left;align-items:left;justify-content:left;}
        .securityCode .error{color:red;font-size:14px;}
        .securityCode input{width:100px;}
        .securityCode img{margin-left: 150px; width: 100px; height: 36px; padding: 0px;}
    </style>
			
	</head>
	<body>
	
	<div id="main_box">
	    <div id="img_area">
	        <img id="lo_lg" src="http://image.17hua.me/upload/image/201709/1505211507101330.png">
	        <img id="lo_bk" src="http://image.17hua.me/upload/image/201709/1505211507553158.png">
	    </div>
	    <div id="input_area">
	        <div class="input_line title_name">
	            <div>
	            	后台管理系统
	            </div>
	        </div>
			<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
				<div class="input_line input_text">
					<div>用户名</div>
					<input type="text"  id="login_usr_login" name="username" maxlength="100"  value="${username}" />
					<img id="lo_close_button" style="display: none" src="http://image.17hua.me/upload/image/201709/1505211506857556.png">
				</div>
				<div class="input_line input_text marginTop20">
					<div>密码</div>
					<input id="login_psw_login" name="password" maxlength="100" type="password">
					<img id="lo_show_button" style="display: none" src="http://image.17hua.me/upload/image/201709/1505211506896983.png">
					<img id="lo_hide_button" style="display: none" src="http://image.17hua.me/upload/image/201709/1505211507105884.png">
				</div>
				<div class="input_line input_text marginTop20 securityCode">
					<div>验证码</div>
					<input type="text" placeholder="输入验证码" data-id="security-code" name="validateCode" maxlength="4">
					<img src="${ctx}/cms/securityCode/generate" alt="验证码" data-id="security-code"  title="点击换一张验证码">
					<div class="tip"><sys:message content="${message}"/></div>
				</div>
				<div class="input_line remenber_me marginTop20">
					<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} />
					<span>记住我</span>
				</div>
				<div class="input_line buttom_login marginTop20">
					<button type="submit" >登录</button>
				</div>
			</form>	
	    </div>
	    <div id="bottom_area_contral">
	       <!--  <div>忘记密码</div> -->
	       <!--  <div>没有账号？去注册</div> -->
	    </div>
	</div>
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script>
	    var usr = document.getElementById('login_usr_login');
	    var psw = document.getElementById('login_psw_login');
	    var clo = document.getElementById('lo_close_button');
	    var sho = document.getElementById('lo_show_button');
	    var hid = document.getElementById('lo_hide_button');
	    usr.addEventListener('keyup',function (e) {
	        usr.value.length>0?clo.style.display = 'block':clo.style.display = 'none'
	    });
	    clo.addEventListener('click',function (e) {
	        usr.value = '';
	        clo.style.display = 'none'
	    });
	    psw.addEventListener('keyup',function (e) {
	        psw.type=='password'?psw.value.length>0?sho.style.display = 'block':sho.style.display = 'none':psw.value.length>0?hid.style.display = 'block':hid.style.display = 'none'
	    });
	    sho.addEventListener('click',function (e) {
	        psw.type='text';
	        sho.style.display = 'none';
	        hid.style.display = 'block';
	    });
	    hid.addEventListener('click',function (e) {
	        psw.type='password';
	        hid.style.display = 'none';
	        sho.style.display = 'block';
	    })
	    
	$(document).ready(function(){
		//验证码
		$("img[data-id='security-code']").bind("click",{dataId:"security-code"},function(){
			$(this).attr("src","${ctx}/cms/securityCode/generate?timestamp="+new Date().getTime()).show();
		});
		
		//验证验证码
		$("input[data-id='security-code']").keyup(function(){
			var current = $(this);
			var securityCode = $.trim($(this).val());
			if(securityCode.length == 4){
				$.post("${ctx}/cms/securityCode/verify",{securityCode : securityCode},function(data){
					
					if(data.code == 1110){
						$(".tip").html("验证码错误").addClass("error").css("display","");
						current.val("");
					}else{
						$(".tip").html("");
					}
				});
			}
		});
	});
	</script>
	

	</body>
</html>
