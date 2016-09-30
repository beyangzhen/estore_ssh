<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>个人中心</title>
		<jsp:include page="/WEB-INF/commons/css.jsp"></jsp:include>
		<style type="text/css">
		     *{margin: 0;padding: 0;}
		    /*---------- 左边导航 ----------*/
	         .person-center{width: 1190px; margin: 0 auto;}
	         .person-left{
	         	width: 235px;
	         	height: 500px;
	         	float: left;
	         }
	
	         .person-left h3{color: gray;}
	         .person-left a{font-size: 16px; font-weight: bold;}
	         .person-left a:link{color: black;}
	         .person-left a:visited{color: black;}
	         .person-left a:active{color: black;}
	
	         .act{
	         	background-color: rgb(244,242,239);
	         	border-right: 5px solid orange;
	         }
	        /* --------右边内容--------- */
	        .person-right1{
	        	width: 955px;
	         	float: right;
	         	border-left: 1px solid #DBDBDB;
	        }
	        .person-top{
	            width: 925px;
	            margin-left: 30px;
	            margin-top: 30px;
	            padding-top: 10px;
	            border-bottom: 1px solid #DBDBDB;
	        }
	        .person-content,.person-pay{
	        	padding-top: 30px;
	        	padding-left: 30px;
	        }
	
		</style>
	</head>
	<body>
		<!-- 头部分 -->
		<jsp:include page="/WEB-INF/commons/top_nav.jsp"></jsp:include>
	
	      <!-- 主体部分 -->
	      <div class="mbody">
	         <jsp:include page="/WEB-INF/commons/header.jsp"></jsp:include>
		     <!-- 左边导航 -->
			 <div class="person-left">
			 	 <ul class="nav nav-pills nav-stacked">
			 	 	<li><h3>个人中心</h3></li>
			 	 	<li class="act" value="1"><a href="#">帐号设置</a></li>
			 	 	<li value="2"><a href="${pageContext.request.contextPath }/OrderCtrl?method=create">订单</a></li>
			 	 	<li value="3"><a href="${pageContext.request.contextPath }/OrderCtrl?method=amount">我的余额</a></li>
			 	 </ul>
			 </div>
	
	         <!-- 右边内容显示:帐号设置 -->
			 <div class="person-right1">
			 	 <div class="person-top">
			 	 	<h4>帐号设置</h4>
			 	 </div>
	
			 	 <div class="person-content">
			 	 	 <form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="firstname" class="col-md-2 control-label">帐号:</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="firstname"
									   placeholder="请输入名字">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-md-2 control-label">密码:</label>
							<div class="col-md-4">
								<input type="password" class="form-control" id="lastname"
									   placeholder="请输入姓">
							</div>
						</div>
	
						<div class="form-group">
							<label for="lastname" class="col-md-2 control-label">确认密码:</label>
							<div class="col-md-4">
								<input type="password" class="form-control" id="lastname"
									   placeholder="请再次输入密码">
							</div>
						</div>
	
						<div class="form-group">
							<div class="col-md-offset-2 col-md-4">
								<button type="submit" class="btn btn-primary">更新资料</button>
							</div>
						</div>
					</form>
			 	 </div>
			 </div>
	
		</div>
	</body>
	
	<jsp:include page="/WEB-INF/commons/js.jsp"></jsp:include>
	<script type="text/javascript">
	
		$('.person-left a').click(function  () {
		    $(this).parent().addClass('act');
		    $(this).parent().siblings().removeClass('act');
		});
	</script>
</html>
