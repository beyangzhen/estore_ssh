<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>图书分类</title>
		<jsp:include page="/WEB-INF/commons/css.jsp"></jsp:include>
			<style type="text/css">
				a{color:#613f23;text-decoration:none;}
				a:hover{color:#ed6c00;text-decoration:none;}
				.body-content1{
					margin-top: 30px;
				}
				.center_content{
					margin-left: 250px;
				}
				.left_nav{
					float:left;
					width: 250px;
				}
				.left_nav .wrap{
					padding: 9px;
					margin-right: 18px;
					border-bottom: 1px dashed #e2dcd7;
				}
				.left_nav a{
					color:#613f23;
				}
				.left_nav a:HOVER, .left_nav a:ACTIVE, .left_nav a:FOCUS {
					color: #ed6c00;
					text-decoration: none;
				}

				.left_nav li .checked {
					padding-bottom: 10px;
					margin: 0;
					color: #ed6c00;
					background-color: #f4f2ef;
					border-right: 4px solid #ed6c00;
					border-bottom: 0;
				}

				.u-colslist1 li {
					position: relative;
					float: left;
					width: 434px;
					padding: 0 0 30px 44px;
				}
				
				/* page 移到了page.jsp中*/
				/* 
				.m-page{height:50px;margin-top:10px;padding-right:20px;}
				.m-page .u-page,.m-page .u-page-go{float:right;margin-left:15px;}
				.m-page .u-page-go{margin-top:3px;}	
				.u-page{position:relative;zoom:1;}
				.u-page ul{float:left;}
				.u-page li{float:left;margin-right:5px;}
				.u-page .pre,.u-page .next{margin-right:5px;}
				.u-page .crt a{background:#a6978a;color:#fff;}
				.u-page .crt a:hover{color:#fff;}
				.u-page a{float:left;display:block;height:30px;padding:0 10px;line-height:30px;text-align:center;color:#a6978a;}
				.u-page .init,.u-page .init:hover{color:#aaa;cursor:default;text-decoration:none;}
				.u-page-go{position:relative;zoom:1;color:#a6978a;}
				.u-page-go *{vertical-align:middle;}
				.u-page-go .txt{width:30px;height:18px;padding:2px 3px;margin:0 5px;border:1px solid #e0dad5;text-align:center;line-height:20px;}
				.u-page-go .btn{display:none;position:absolute;top:24px;left:29px;width:38px;height:22px;line-height:22px;background:#e14e37;color:#fff;text-align:center;}
				.u-page-go .btn:hover{text-decoration:underline;} 
				*/	
				
				/* cover and info horizontal */
				.u-bookitm1 .cover{
					-webkit-box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.2), 0 5px 10px 0 rgba(0, 0, 0, 0.19);
					box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.2), 0 5px 10px 0 rgba(0, 0, 0, 0.19);
				}
				.u-bookitm1 .book{float:left;margin-right:-126px;}
				.u-bookitm1 .book .buy-status{margin-top: 10px;text-align:center;color:#999;}
				.u-bookitm1 .book .u-price{text-align:center;}
				.u-bookitm1 .info{margin:4px 0 0 126px;}
				.u-bookitm1 .wrap{height:125px;margin-top:3px;overflow:hidden;}
				.u-bookitm1 .title,.u-bookitm2 .title{display:block;max-height:36px;margin:3px 0 5px;font-size:14px;line-height:18px;overflow:hidden;}
				.u-bookitm1 .desc{color:#666;}
				.u-bookitm1 .u-author,.u-bookitm2 .u-author{padding:3px 0;color:#999;font-size:3px }
				.u-bookitm1 .act .u-btn2,.u-bookitm1 .act .u-btn-disable,.u-bookitm1 .act .icn-cart,.u-bookitm1 .act .icn-cart-chk,.u-bookitm1 .act .icn-heart,.u-bookitm1 .act .icn-heart-chk,.u-bookitm1 .act .icn-quan{float:left;}
				.u-bookitm1 .act .u-btn2,.u-bookitm1 .act .u-btn-disable{width:42px;margin-right:15px;line-height:30px;}
				.u-bookitm1 .act .icn-cart,.u-bookitm1 .act .icn-cart-chk,.u-bookitm1 .act .icn-heart,.u-bookitm1 .act .icn-heart-chk{margin-right:8px;}
				.icn-android,.icn-kindle,.icn-ios,.icn-web,.icn-avatar,.icn-step1,.icn-step2,.icn-step3,.icn-cart,.icn-heart,.icn-cart-chk,.icn-heart-chk,.icn-cart2,.icn-smile,.icn-talk,.icn-trash,.icn-search,.icn-search2,.icn-gift,
				.icn-close,.icn-close2,.icn-null,.icn-disappoint,.icn-arr,.icn-coupon,.icn-coupon1,.icn-coupon2,.icn-plus,.icn-ok{background:url(${pageContext.request.contextPath}/resources/image/sprite.png) -9999px -9999px no-repeat;}
			/* sprite */
			.icn-android,.icn-kindle,.icn-ios,.icn-web{display:block;width:32px;height:32px;}
			.icn-android{background-position:-35px 0;}
			.icn-kindle{background-position:4px 0;}
			.icn-ios{background-position:-73px 0;}
			.icn-web{background-position:-114px 0}
			.icn-avatar{display:block;width:50px;height:50px;background-position:-168px 0;}
			.icn-step1,.icn-step2,.icn-step3{display:block;width:50px;height:50px;}
			.icn-step1{background-position:0 -60px;}
			.icn-step2{background-position:-58px -60px;}
			.icn-step3{background-position:-115px -60px;}
			.icn-cart,.icn-heart,.icn-cart-chk,.icn-heart-chk{display:block;width:32px;height:32px;}
			.icn-heart{background-position:-79px -115px;}
			.icn-heart-chk{background-position:-119px -115px;}
			.icn-cart{background-position:0 -115px;}
			.icn-cart-chk{background-position:-39px -115px;}
			.icn-cart2{display:block;width:14px;height:11px;background-position:0 -160px;}
			.icn-smile{display:block;width:15px;height:14px;background-position:-22px -160px;}
			.icn-smile-selected{background-position:-22px -180px;}
			.icn-talk{display:block;width:14px;height:12px;background-position:-45px -160px;}
			.icn-trash{display:block;width:8px;height:11px;background-position:-68px -160px;}
			.icn-search{display:block;width:14px;height:15px;background-position:-86px -160px;}
			.icn-search2{display:block;width:17px;height:18px;background-position:-167px -115px;}
			.icn-gift{display:block;width:17px;height:18px;background-position:-111px -160px;}
			.icn-close{display:block;width:11px;height:11px;background-position:-135px -160px;text-indent:-9999px;cursor:pointer;}
			.icn-close2{display:block;width:10px;height:10px;background-position:-154px -160px;text-indent:-9999px;cursor:pointer;}
			.icn-null{display:block;width:116px;height:117px;background-position:0 -200px;}
			.icn-disappoint{display:block;width:116px;height:117px;background-position:-122px -200px;}
			.icn-arr{display:block;width:16px!important;height:16px;background-position:-170px -156px;}
			.icn-coupon,.icn-coupon1{display:block;width:36px;height:36px;background-position:-212px -160px;}
			.icn-coupon1{background-position:-212px -115px;}
			.icn-coupon2{display:block;width:20px;height:20px;background-position:-190px -160px;}
			.icn-plus{display:inline-block;width:10px;height:10px;background-position:0 -182px;}
			.icn-change{display: inline-block;width: 14px;height: 16px;vertical-align: text-bottom;margin-right: 6px;}
			.icn-quan{display:block;width:32px;height:32px;}
			.icn-ok{display:block;width:16px;height:16px;background-position:-45px -180px;}				
		</style>
	</head>
	<body>
		<jsp:include page="/WEB-INF/commons/top_nav.jsp"></jsp:include>
			<!-- 主体部分 -->
			<div class="mbody">
			<%-- 调用页面时发送一个参数过 ?active=book --%>
			<jsp:include page="/WEB-INF/commons/header.jsp">
				<jsp:param name="active" value="book"></jsp:param>
			</jsp:include>
			<!-- 主体内容一:首页内容 -->
			<div class="body-content1">
				<div class="left_nav">
					<!-- 放置导航信息 -->
					<c:if test="${cats != null }">
						<ul class="level2">
							<c:forEach var="cat" items="${cats }">
								<li>
									<%-- 路径上的参数id是否与分类的id值相等，如果相同，当前加上激活的样式 --%>
									<c:choose>
										<c:when test="${param.id eq cat.id}">
											<div class="wrap checked">
												<a href="${pageContext.request.contextPath }/book/list.action?parentid=${param.parentid }&id=${cat.id}">
													<span><c:out value="${cat.name }"></c:out></span>
													<em class="num"></em>
												</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="wrap">
												<a href="${pageContext.request.contextPath }/book/list.action?parentid=${param.parentid }&id=${cat.id}">
													<span><c:out value="${cat.name }"></c:out></span>
													<em class="num"></em>
												</a>
											</div>
										</c:otherwise>
									</c:choose>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="center_content">
					<!-- 放入图书 -->
					<c:if test="${pb != null }">
						<!-- 书内容 -->
						<c:if test="${pb.list != null }">
							<ul class="u-colslist1">
								<c:forEach var="book" items="${pb.list }">
									<li class="u-bookitm1 j-bookitm">
										<div class="book">
											<div class="cover">
												<a href="" hidefocus="hidefocus" target="_blank"><img src="${book.filename }" alt="${book.name }" ondragstart="return false;" oncontextmenu="return false;" style="display: block;"></a>
											</div>
											<div class="buy-status j-price">
												<div class="u-price">
													<c:choose>
														<c:when test="${book.price eq 0}">
															<b>免费</b>
														</c:when>
														<c:otherwise>
															<em>¥ ${book.price } </em>
															<del>¥ ${book.oldPrice }</del>
														</c:otherwise>
													</c:choose>
												</div>
												<p style="display: none">已购买</p>
											</div>
										</div>
										<div class="info">
											<div class="wrap">
												<a href="" class="title" hidefocus="hidefocus" target="_blank">${book.name }</a>
												<div class="u-stargrade" itemprop="aggregateRating" itemscope="" itemtype="http://schema.org/AggregateRating">
													<div class="icon grade8"></div>
													<!-- <span class="num" itemprop="ratingValue">( 69 )</span> -->
												</div>
												<div class="u-author">
													<span>${book.author }</span>
												</div><br/>
												<p class="desc">${book.description }</p>
											</div>
											<%--
											<a style="width:110px;background:none;box-shadow:none;display:none" href="/pay/103380#coupon" class="active_46 u-btn j-buy" hidefocus="hidefocus">书券免费兑换</a>
											<a style="margin-top:15px;width:110px;background:none;box-shadow:none;display:none;color:#999;font-size:14px;" href="/pay/103380#coupon" class="active_46_none" hidefocus="hidefocus">暂无可用书券</a>
											--%>
											<div class="act j-act">
												<span class="j-act">
													<a href="javascript:void(0)" data-bookid="${book.id }" class="icn-cart j-cart" hidefocus="hidefocus" title="加入购物车"></a>
													<span class="icn-cart-chk" title="已加入购物车" style="display:none"></span>
													<a href="javascript:void(0)" class="icn-heart j-fav" hidefocus="hidefocus" title="加入收藏"></a>
													<span class="icn-heart-chk" style="display:none" title="已收藏"></span>
													<a class="icn-quan j-coupon" href="/pay/103380#coupon" style="display:none"></a>
												</span>
											</div>
										</div>
									</li>					
								</c:forEach>
							</ul>
						</c:if>
						
						<!-- 统一设置域对象的url属性，供page.jsp中使用el获取 -->
						<c:set var="url" value="${pageContext.request.contextPath }/book/list.action?parentid=${param.parentid }&id=${param.id}"></c:set>
						<%-- 提取公共部分出来 用静态引用方式找它加载进来 --%>
						<%@ include file="/WEB-INF/commons/page.jsp" %>		
					</c:if>
				</div>
			</div>
		</div>
	</body>
	
	<jsp:include page="/WEB-INF/commons/js.jsp"></jsp:include>
	<script type="text/javascript">
		// jquery 入口
		$(function() { 
			// 加入购物车
			$('.icn-cart').on('click', function() {
				$.post('${pageContext.request.contextPath}/cart/add.action', 
					// 请求的方法和url参数
					{bookid:$(this).attr('data-bookid')}, function(res) { // 回调函数（服务后台响应回来的数据）
						// 未登录用户（异步）访问购物车，转到登录页面
					    if(res == "Asyn") {
							location.href = '${pageContext.request.contextPath}/user/login.jsp';
							return;
					    } 
						alert("您已成功添加到购物车 !");
					} 
				);
			});
			
		});
	</script>
</html>