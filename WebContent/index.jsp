<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>多看阅读</title>
		<jsp:include page="/WEB-INF/commons/css.jsp"></jsp:include>	
			<style>
				/* 目录  */
				.m-directory *{margin: 0;padding: 0;}
				a .label{color:#613f23;text-decoration:none;}
				a:hover{color:#ed6c00;text-decoration:none;}
				.m-directory{width:230px;background:#f4f2ef;overflow:hidden;float: left;}
				.m-directory .ttl{height:35px;padding:0 20px;font-size:14px;line-height:35px;color:#333;background:#e7e2dd;}
				.m-directory .num{color:#afa298;font-size:10px;}
				.m-directory span{display:block;}
				.m-directory ul{width:242px;}
				.m-directory li{float:left;border-right:1px solid #dbd6d2;width: 50%;}
				.m-directory li a{display:block;width:80px;height:40px;padding:14px 20px 0;}
				.m-directory li a:hover{text-decoration:none;}
				.m-directory .itm1{background:#e7e2dd;}
			</style>
		</head>
		<body>
		<jsp:include page="/WEB-INF/commons/top_nav.jsp"></jsp:include>
			<!-- 主体部分 -->
			<div class="mbody">
			<%-- 调用页面时发送一个参数过 ?active=book --%>
			<jsp:include page="/WEB-INF/commons/header.jsp">
				<jsp:param name="active" value="home"></jsp:param>
			</jsp:include>
			<!-- 主体内容一:首页内容 -->
			<div class="body-content1">

				<!-- 主体banner部分 -->
				<div class="banner">
					<!-- 左侧盒子:小说分类导航 -->
					<div class="m-directory">
						<h5 class="ttl">图书分类</h5>
						<ul class="f-cb">
							<c:if test="${categories != null }">
								<c:forEach varStatus="s" var="cat" items="${categories }">
									<c:choose>
										<c:when test="${s.index%4 gt 1}">
											<li class="itm itm1">
											</c:when>
											<c:otherwise>
											<li class="itm">
											</c:otherwise>
										</c:choose>
										<a href="${pageContext.request.contextPath }/book/list.action?parentid=${cat.id}&id=${cat.children[0].id}&p=1" hidefocus="hidefocus">
											<span class="label">${cat.name}</span>
										</a>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<div id="big_banner_wrap" style="display: none;">
						<ul id="banner_menu_wrap">
							<c:if test="${categories != null }">
								<c:forEach var="cat" items="${categories }"><%--迭代顶级分类 --%>
									<li class="active">
										<a href="${pageContext.request.contextPath }/${cat.id}">${cat.name }</a>
										<c:if test="${cat.children != null }"><%-- 子分类存在则输出 --%>
											<a class="banner_menu_i carousel-control right">&rsaquo;</a>
											<div class="banner_menu_content" style="width: 600px; top: -20px;">
												<ul class="banner_menu_content_ul">
													<c:forEach var="c" items="${cat.children }"><%-- 迭代子分类 --%>
														<li><a>${c.name }</a><span>选购</span></li>
															</c:forEach>
												</ul>
											</div>
										</c:if>
									</li>
								</c:forEach>
							</c:if>

						</ul>
					</div>

					<!-- 右侧盒子:循环滚动图片浏览 -->
					<div id="big_banner_pic_wrap">
						<!-- 轮播插件 -->
						<!--
							容器:carousel slide
							自动轮播:data-ride="carousel"
						-->
						<!-- 保证容器大小和图片要一致  否则出现问题 -->
						<div id="myCarousel" class="carousel slide" style="width:955px">
							<!-- 分页控件 -->
							<ol class="carousel-indicators">
								<li data-target="#myCarousel" class="active" data-slide-to="0"></li>
								<li data-target="#myCarousel" data-slide-to="1"></li>
								<li data-target="#myCarousel" data-slide-to="2"></li>
								<li data-target="#myCarousel" data-slide-to="3"></li>
								<li data-target="#myCarousel" data-slide-to="4"></li>
								<li data-target="#myCarousel" data-slide-to="5"></li>
							</ol>
							<!-- 图片内容 <-->
							<div class="carousel-inner">
								<div class="item active">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr1.jpg" alt="第一张"></a>
								</div>
								<div class="item">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr2.jpg" alt="第二张"></a>
								</div>
								<div class="item">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr3.jpg" alt="第三张"></a>
								</div>
								<div class="item">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr4.jpg" alt="第四张"></a>
								</div>
								<div class="item">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr5.jpg" alt="第五张"></a>
								</div>
								<div class="item">
									<a href="#"><img src="${pageContext.request.contextPath }/resources/image/scr6.jpg" alt="第六张"></a>
								</div>
							</div>
							<!-- data-slide:上下图片的切换 -->
							<a href="#myCarousel" data-slide="prev" class="carousel-control left"><span class="glyphicon glyphicon-chevron-left"></span></a>
							<a href="#myCarousel" data-slide="next" class="carousel-control right"><span class="glyphicon glyphicon-chevron-right"></span></a>

						</div>

						<!-- 重磅推荐 -->
						<div class="choose-title">重磅推荐</div>

						<div class="collection-img">

							<div class="collection left">
								<div class="left-img">
									<img src="${pageContext.request.contextPath }/resources/image/1.jpg">
									<p class="price">
										<span>￥49.9</span>
										<del>￥418.00</del>
									</p>
								</div>

								<div class="right-button">
									<div class="information">
										<p><a href="#">定位系列经典收藏版（共19册）</a></p>
										<p>商业人士必读系列，开创“胜出竞争”的营销之道。</p>
									</div>

									<div class="bt">
										<button class="btn btn-info btn-sm">立即购买</button>
										<button class="btn btn-info btn-sm">加入购物车</button>
									</div>
								</div>
							</div>

							<div class="collection center">
								<div class="left-img">
									<img src="${pageContext.request.contextPath }/resources/image/2.jpg">
									<p class="price">
										<span>￥9.99</span>
										<del>￥73.00</del>
									</p>
								</div>

								<div class="right-button">
									<div class="information">
										<p><a href="#">大谋小计五十年：诸葛亮传（全五册）</a></p>
										<p>谋略、生活、爱情……还原一个有血有肉的诸葛亮，马亲王力荐！</p>
									</div>

									<div class="bt">
										<button class="btn btn-info btn-sm">立即购买</button>
										<button class="btn btn-info btn-sm">加入购物车</button>
									</div>
								</div>
							</div>

							<div class="collection right">
								<div class="left-img">
									<img src="${pageContext.request.contextPath }/resources/image/3.jpg">
									<p class="price">
										<span>￥6.99</span>
										<del>￥68.00</del>
									</p>
								</div>

								<div class="right-button">
									<div class="information">
										<p><a href="#">布朗神父探案全集（译言古登堡计划 全五册）</a></p>
										<p>丘吉尔、希区柯克、克里斯蒂最推崇的推理小说。</p>
									</div>

									<div class="bt">
										<button class="btn btn-info btn-sm">立即购买</button>
										<button class="btn btn-info btn-sm">加入购物车</button>
									</div>
								</div>
							</div>

							<div class="new-flash">
								<p>活动快讯</p>
								<ul>
									<li>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;</li>
									<li><a href="#">超级畅销的好书特价啦，戳此看看～</a></li>
								</ul>
							</div>

						</div>
					</div>
				</div>

				<!-- 主体content部分 -->
				<div class="content">
					<!-- 左侧广告栏 -->
					<div class="poster">
						<ul class="poster-list">
							<li><img src="${pageContext.request.contextPath }/resources/image/content1.jpg"></li>
							<li><img src="${pageContext.request.contextPath }/resources/image/content2.jpg"></li>
							<li><img src="${pageContext.request.contextPath }/resources/image/content3.jpg"></li>
						</ul>
					</div>

					<!-- 右侧书籍展示 -->
					<div class="content-collection">
						<div class="content-top">
							<div class="collection-img">

								<div class="collection left">
									<div class="left-img">
										<img src="${pageContext.request.contextPath }/resources/image/con1.jpg">
										<p class="price">
											<span>￥1.99</span>
											<del>￥6.00</del>
										</p>
									</div>

									<div class="right-button">
										<div class="information">
											<p><a href="#">经典超译本：理想国</a></p>
											<p>内容广博，涉及哲学、政治伦理、教育、心理、社会、家庭、宗教、技术……</p>
										</div>

										<div class="bt">
											<button  class="btn btn-info btn-sm">立即购买</button>
											<button class="btn btn-info btn-sm">加入购物车</button>
										</div>
									</div>
								</div>

								<div class="collection center">
									<div class="left-img">
										<img src="${pageContext.request.contextPath }/resources/image/con2.jpg">
										<p class="price">
											<span>￥3.99</span>
											<del>￥12.00</del>
										</p>
									</div>

									<div class="right-button">
										<div class="information">
											<p><a href="#">临界·爵迹 1</a></p>
											<p>《幻城》问世十周年之际，郭敬明第二部奇幻长篇《临界.爵迹》诞生！ </p>
										</div>

										<div class="bt">
											<button class="btn btn-info btn-sm">立即购买</button>
											<button class="btn btn-info btn-sm">加入购物车</button>
										</div>
									</div>
								</div>

								<div class="collection right">
									<div class="left-img">
										<img src="${pageContext.request.contextPath }/resources/image/con3.jpg">
										<p class="price">
											<span>￥19.99</span>
											<del>￥73.00</del>
										</p>
									</div>

									<div class="right-button">
										<div class="information">
											<p><a href="#">中层领导力（全三册）</a></p>
											<p>奥巴马、比尔·盖茨、巴菲特和乔布斯共同推崇的领导力经典著作！ </p>
										</div>

										<div class="bt">
											<button class="btn btn-info btn-sm">立即购买</button>
											<button class="btn btn-info btn-sm">加入购物车</button>
										</div>
									</div>
								</div>

								<div class="content-bottom">
									<div class="collection-img">

										<div class="collection left">
											<div class="left-img">
												<img src="${pageContext.request.contextPath }/resources/image/con4.jpg">
												<p class="price">
													<span>￥4.99</span>
													<del>￥18.00</del>
												</p>
											</div>

											<div class="right-button">
												<div class="information">
													<p><a href="#">黑洞战争</a></p>
													<p>本书揭开了斯蒂芬·霍金与伦纳德·萨斯坎德、赫拉德·特霍夫特关于黑洞本性论战的深层内幕</p>
												</div>

												<div class="bt">
													<button  class="btn btn-info btn-sm">立即购买</button>
													<button class="btn btn-info btn-sm">加入购物车</button>
												</div>
											</div>
										</div>

										<div class="collection center">
											<div class="left-img">
												<img src="${pageContext.request.contextPath }/resources/image/con5.jpg">
												<p class="price">
													<span>￥9.99</span>
													<del>￥40.00</del>
												</p>
											</div>

											<div class="right-button">
												<div class="information">
													<p><a href="#">不平等经济学（第七版）</a></p>
													<p>研究财富与收入不平等重磅之作！看经济发展是如何产生出社会不平等的？ </p>
												</div>

												<div class="bt">
													<button class="btn btn-info btn-sm">立即购买</button>
													<button class="btn btn-info btn-sm">加入购物车</button>
												</div>
											</div>
										</div>

										<div class="collection right">
											<div class="left-img">
												<img src="${pageContext.request.contextPath }/resources/image/con6.jpg">
												<p class="price">
													<span>￥3.99</span>
													<del>￥12.00</del>
												</p>
											</div>

											<div class="right-button">
												<div class="information">
													<p><a href="#">实战高手教你做短线</a></p>
													<p>从短线操作这一基本概念入手 分享实战高手短线操作的特点、要求、原则、策略及技巧！</p>
												</div>

												<div class="bt">
													<button class="btn btn-info btn-sm">立即购买</button>
													<button class="btn btn-info btn-sm">加入购物车</button>
												</div>
											</div>
										</div>



									</div>
								</div>

							</div>
						</div>

						<!--  页面切换按钮  -->
						<div class="person-bottom" style="padding-top: 50px; float: right; ">
							<button class="btn btn-default">确定</button>
							<span class="bottom-txt">到第<input type="text" value="1" style="width: 40px; height: 35px; text-align: center;">页</span>
							<p class='bottom-txt' style=''>共35页</p>
							<button class="btn btn-default">下一页&nbsp;&gt;</button>
							<p class='bottom-txt' style=''>......</p>
							<button  class="btn btn-default">7</button>
							<button  class="btn btn-default">6</button>
							<button  class="btn btn-default">5</button>
							<button  class="btn btn-default">4</button>
							<button   class="btn btn-default">3</button>
							<button  class="btn btn-default">2</button>
							<p class='bottom-txt' style=''>1</p>
							<button class="btn btn-default disabled">&lt;&nbsp;上一页</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</body>
	
	<jsp:include page="/WEB-INF/commons/js.jsp"></jsp:include>
</html>

