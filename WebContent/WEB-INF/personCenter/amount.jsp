<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	  <meta charset="UTF-8">
	  <title>个人中心我的余额</title>
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
	         .person-right3{
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
	
	
	        .person-price{
	          padding-top: 10px;
	          margin-left: 30px;
	          border-bottom: 1px solid #DBDBDB;
	        }
	
	        .person-right3 .person-price .balance{
	          font-size: 30px;
	          font-weight: bold;
	          color: rgb(229,158,80);
	        }
	        .person-right3 .person-price .pay{
	          margin-top: 50px;
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
	        <li value="1"><a href="${pageContext.request.contextPath }/OrderCtrl?method=personCenter">帐号设置</a></li>
	        <li value="2"><a href="${pageContext.request.contextPath }/OrderCtrl?method=create">订单</a></li>
	        <li class="act" value="3"><a href="#">我的余额</a></li>
	       </ul>
	     </div>
	
	     <!-- 右边内容显示:我的余额-->
	    <div class="person-right3">
	          <div class="person-top">
	          <h4>我的余额</h4>
	         </div>
	         <div class="person-price">
	                  <p class="balance"><span>99</span>&nbsp;书币</p>
	                  <h4 class="pay">账户充值</h4>
	         </div>
	
	         <div class="person-pay">
	            <form class="form-horizontal" role="form">
	
	              <div class="form-group">
	                  <label for="inputPassword" class="col-md-2 control-label">账户:</label>
	                  <div class="col-md-4">
	                      <input type="text" class="form-control" id="inputPassword" placeholder="请输入:手机号/账户名(昵称)">
	                  </div>
	              </div>
	
	               <div class="form-group">
	                  <label for="inputPassword" class="col-md-2 control-label">充值金额:</label>
	                  <div class="col-md-4">
	                      <input type="text" class="form-control" id="inputPassword" placeholder="请输入充值金额:">
	                  </div>
	              </div>
	
	                <div class="form-group">
	                  <div class="col-md-offset-2 col-md-2">
	                      <button type="submit" class="btn btn-primary">确认充值</button>
	                  </div>
	              </div>
	            </form>
	         </div>
	      </div>
	  </div>
	</body>
	
	<jsp:include page="/WEB-INF/commons/js.jsp"></jsp:include>
</html>
