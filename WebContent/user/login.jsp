<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content ="width=device-width, initial-scale=1"> 
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css">
        <title>duokan登录/注册</title>
        <style>
            *{
                margin: 0px;
                padding: 0px;
            }
            /*布局开始*/
            #login_dialog {
                position: fixed;
                left: 50%;
                top: 30%;
                /* background-color: #303a40; */
                width: 500px;
                margin-left: -200px;
                margin-top: -150px;
                font-family: "黑体";
                /*禁止复制粘贴*/
                -moz-user-select: none;
                -webkit-user-select: none;
                user-select:none;
            }
            .register_dialog_title {
                height: 35px;
                line-height: 35px;
                margin: 0 5px;
            }

            .register_dialog_info {
                float: left;
                margin-left:10px;
                color: #fff;
                margin-top: 5px;
                font-size: 20px;
            }
            #register_dialog {
                position: fixed;
                left: 40%;
                top: 40%;
                background-color: #303a40;
                width: 500px;
				/*  height: 600px; */
                margin-left: -200px;
                margin-top: -200px;
                font-family: "黑体";
                -moz-user-select:none; /*火狐*/
                -webkit-user-select:none; /*webkit浏览器*/
                -ms-user-select:none; /*IE10*/
                -khtml-user-select:none; /*早期浏览器*/
                user-select:none;
            }

            .register_dialog_info {
                float: left;
                margin-left:10px;
                color: #fff;
                margin-top: 5px;
                font-size: 20px;
            }
            .dialog_close {
                cursor: pointer;
                width: 40px;
                height:40px;
                border-radius:25px;
                float: right;
                line-height:40px;
                font-size: 20px;
                color: #d8dadb;
                font-family: "微软雅黑";
                text-align: center;
                cursor:center;
            }
            form{padding: 20px 0px;}
            ul li {list-style: none;}
            .sub {
                text-align: center;
            }
            .sub input {
                display: inline-block;
                width: 300px;
                background-color: rgb(255, 109, 11);
                color: rgb(255, 255, 255);
                font-size: 20px;
                text-align: center;
                height: 40px;
                line-height: 40px;
                font-family: 黑体;
                outline: none;
                border: none;
                margin: auto;
            }
            .dialog_close:hover {
                background-color: #566
            }
            input[type = "submit"]:hover{cursor: pointer;}
            /*布局结束*/
            .reg-box { padding-left: 30px; }

            .reg-box li { line-height: 50px; width: 500px; overflow: hidden; }

            .reg-box li label { width: 68px; height: 50px; float: left; line-height: 50px; text-align: right; padding-right: 20px; }

            .reg-box li input,.reg-box li select{ padding: 6px 0; font-size: 16px; width: 296px; height: 50px; line-height: 28px; border: 1px solid #dddddd; text-indent: 0.5em; float: left; }

            .reg-box li select option{font-size:16px;}

            .registered .btn a { background: #ff7200; margin-left: 110px; }

            /*验证码*/
            .add { width: 128px; height: 44px; float: left; _display: inline; cursor: pointer; margin-left: 20px; }

            .reg-box li .sradd { width: 148px; text-indent: 4px; font-size: 14px; }

            .reg-box li .input-code { width: 106px; padding: 10px; font-family: Arial; font-style: italic; color: red; letter-spacing: 1px; cursor: pointer; text-align: center; text-indent: 0; }

            .yzm,.phoKey { background: #ff7200; text-align: center; line-height: 44px; color: #fff; }

            .phoKey{letter-spacing: 3px; font-size:18px;}

            .yzmc { background: #dddddd; text-align: center; line-height: 44px; color: #999; }

            .error { clear:both;display:block;color: red; padding-left: 90px; padding-bottom:5px;height:20px;float: left; font-size:12px;line-height: 20px;}

            input { background-color: #fff; outline: none; }

            .reg-box li { width: auto; }

            .reg-box li input.errorC, .errorC{ border: 1px solid red; }

            .reg-box li input.checkedN , .checkedN{ border: 1px solid #1ece6d; }

			.refresh{cursor: pointer;}
        </style>

    </head>
    <body>
        <div id="login_dialog" class="panel panel-success">
			<div class="panel-heading"> 
                <h3 class="panel-title">登录/注册 
                	<c:if test="${ errors!=null}">
                		<font color="red">${errors[0] }</font>
                	</c:if>
               	</h3> 
			</div> 
            <div style="background-color:#ffffff;margin:10px;" class="panel-body">
                <form action="${pageContext.request.contextPath }/UserCtrl" method="post"> <!-- ?method=login --><!-- onsubmit="return false;" 一般不把js混在页面上 -->
					<input type="hidden" name="method" value="login">               
                    <ul class="reg-box">                 
                        <li>
                            <label for="">账    号</label><input name="username" type="text" placeholder="请输入您的账号" class="input account" maxlength="11" style="color:#999;"/><span class="error error5"></span>
                        </li>
                        <li>
                            <label for="">密    码</label><input name="password" type="password"  class = "input admin_pwd" placeholder="请输入密码" style="color:#999;""/><span class="error error6"></span>
                        </li>
                        <li>
                            <label for="">验证码</label><input name="vcode" type="text" class="input sradd photokey" placeholder="请输入验证码" style="color:#999;ime-mode:disabled;-webkit-ime-mode:inactive;"/>
							<a class="refresh">看不清</a>	
							<span class="add phoKey"></span>
							<span class="error error7"></span>
                        </li>
                    </ul>
                    <div class="sub">
                        <input type="submit" class="loin_btn" value="立即登录" />
                    </div>                  
                </form>
            </div>
        </div>
    </body>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/jquery-3.1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript">
	    $(function(){
			var rootPath = '${pageContext.request.contextPath }';
		
			// 设置验证码
			$('.phoKey').css('background', 'url('+rootPath+'/UserCtrl?method=vcode)');
			// 刷新验证码
			$('.refresh').on('click', function(){
				$('.phoKey').css('background', 'url('+rootPath+'/UserCtrl?method=vcode&_dc='+new Date().getTime()+')');
			});
			
			var empty = {username:'请输入您的手机号',password:'请输入密码',vcode:'请输入收到的验证码'};
			
			// 表单提交前
			$('form').on('submit', function(){
				// console.log('submit');
				var isOk = true;
				// 内容完整才能提交(return true)
				$('.input').each(function(i,v){
					// 清理提示信息
					$(v).removeClass('errorC');
					$(v).next().empty();
					// console.log({i:i,v:v});
					var value = $(v).val(), name = $(v).attr('name');
					// null, undefined, '', '   '
					if($.isEmptyObject(value) || $.isEmptyObject(value.trim())){
						isOk = false;
						// console.log('first');
						// 验证不通过的表单，设置提示信息
						$(v).addClass("errorC");
	                	$(v).next().html(empty[name]);
	                	$(v).next().css("display","block");
						return;
					}
				});
	
				return isOk;
			});
			
			// 失去焦点
			$('.input').on('blur', function(){
				// console.log('blur')
				// 清理提示信息
				var error = $(this).parent().find('.error');
				$(this).removeClass('errorC');
				error.empty();
				
				var value = $(this).val(), name = $(this).attr('name');
				if($.isEmptyObject(value) || $.isEmptyObject(value.trim())){
					// 验证不通过的表单，设置提示信息
					$(this).addClass("errorC");
                	error.html(empty[name]);
                	error.css("display","block");
				}
			});
			
		});
	</script>
    
</html>