<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 主体搜索部分 -->
<div class="body-top">
	<div class="logo">
		<img src="${pageContext.request.contextPath }/resources/image/logo.png">
	</div>

	<div class="serch">
		<input class="txt" type="text" placeholder="搜索书名或作者...">
		<input class="butn" type="button">
	</div>
</div>

<!-- 主导航部分 -->
<div class="main-nav">
	<ul class="center-nav">
		<li class="checked"><a href="${pageContext.request.contextPath }/index.jsp">首页</a></li>
		<li><a href="#">榜单</a></li>
		<li><a href="#">书评</a></li>
		<li><a href="#">精品.免费</a></li>
		<li><a href="#">分类</a></li>
		<li><a href="#">客户端</a></li>
		<li><a href="#">论坛</a></li>
	</ul>
</div>
