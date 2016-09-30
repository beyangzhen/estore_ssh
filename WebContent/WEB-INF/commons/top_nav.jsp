<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 头部分 -->
<div class="headbox">
	<div class="navbox">
		
		<div class="nav-left">
			<ul class="lt-list">
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">小米网</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">MIUI</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">米聊</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">游戏</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a checked" href="#" target="_blank">多看阅读</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">云服务</a></li>
				<li class="part">|</li>
				<li class="lt-li"><a class="nav-a" href="#" target="_blank">小米网移动版</a></li>
			</ul>
		</div>
		
		<div class="nav-right">
			<ul class="rt-list">
				<li class="rt-li">
				<c:choose>
					<c:when test="${login_user != null }">
						欢迎你，<font color="red">${login_user.username }</font> /<a href="${pageContext.request.contextPath }/UserCtrl?method=logout">注销</a>
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a>/<a href="dklogin.html">注册</a>
					</c:otherwise>
				</c:choose>
				</li>
				<li class="part">|</li>
				<li class="rt-li shop"><a href="${pageContext.request.contextPath }/CartCtrl?method=list">购物车</a></li>
				<li class="part">|</li>
				<li class="rt-li myPerson"><a href="${pageContext.request.contextPath }/OrderCtrl?method=personCenter">个人中心</a></li>
			</ul>
		</div>
	</div>
</div>
