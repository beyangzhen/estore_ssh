<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>购物车</title>
		<jsp:include page="/WEB-INF/commons/css.jsp"></jsp:include>
		<style type="text/css">
			.breadnav {
				font-size: 16px;
				font-family: 楷体;
			}

			.breadnav>span+span:before {
				color: #CCCCCC;
				content: "> ";
				padding: 0 5px;
			}

			.shopping {
				width: 960px;
				margin: 30px auto;
			}

			.shopping .numBtn {
				float: left;
			}

			.body-content3 .shopping input {
				border: 1px solid #DBDBDB;
				outline-style: inherit;
				text-align: center;
			}
		</style>
	</head>
	<body>
		<!-- 头部分 -->
		<jsp:include page="/WEB-INF/commons/top_nav.jsp"></jsp:include>
		<!-- 主体部分 -->
		<div class="mbody">
			<jsp:include page="/WEB-INF/commons/header.jsp"></jsp:include>
				<div class="breadnav">
					<span>当前位置&nbsp;:&nbsp;首页</span> <span style='color: #CCCCCC;'>购物车</span>
				</div>
			<%-- 看分页对象存在与否 --%>
			<c:if test="${pb != null}">
				<%-- 看分页列表数据存在与否 --%>
				<c:if test="${pb.list != null}">
					<div class="shopping">
						<div class="panel  panel-warning">
							<div class="panel-heading">
								<h3 class="panel-title">购物车状态</h3>
							</div>
							<%-- 控制循环内容部分 --%>
							<c:forEach var="item" items="${pb.list}">
	
								<div class="panel-body">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h3 class="panel-title"></h3>
											<%-- 批量选择支付的多选框 --%>
											<input type="checkbox" name="item" value="${item.itemid }">
										</div>
										<div class="panel-body">
											<div class="order number1">
												<div class="col-md-1">
													<img src="${item.filename}" alt="" style="width: 64px;" />
												</div>
	
												<div class="col-md-3">
													<span style="font-size: 16px; font-weight: bold;">${item.name}</span><br /> <span>${item.author}</span>
													<p style="color: gray; margin-top: 20px;">
														¥<span class='unit-price'>${item.price}</span>
													</p>
												</div>
	
												<div class="col-md-2"></div>
	
												<div class="col-md-3">
													<div class="btn-group" style="width: 180px">
														<span class="numBtn" style="font-size: 16px; line-height: 35px;">总件数:&nbsp;</span>
														<div class="input-group" style="width: 100px;">
															<span class="input-group-btn">
																<button data-itemid="${item.itemid }" class="btn btn-default btnAdd" type="button">+</button>
															</span> <input type="text" class="form-control" value="${item.quantity }"> <span class="input-group-btn">
																<button data-itemid="${item.itemid }" class="btn btn-default btnSub" type="button">-</button>
															</span>
														</div>
													</div>
													<p style="margin-top: 20px; font-size: 16px;">
														总额:<span class='total-amount'>${item.price * item.quantity}</span>
													</p>
												</div>
	
												<div class="col-md-2" style="text-align: center;">
													<button class="btn btn-info">立即支付</button>
												</div>
	
												<div class="col-md-1">
													<span class="glyphicon glyphicon-trash" data-itemid="${item.itemid }"></span>
												</div>
											</div>
										</div>
									</div>
									<%-- end --%>
								</div>
							</c:forEach>
						</div>
					</div>
					<form id="delFrm" action="${pageContext.request.contextPath }/cart/delete.action" method="get">
						<input type="hidden" name="p" value="1">
						<input type="hidden" name="id">
					</form>
				</c:if>
	
				<%-- 统一设置域对象的url属性，供page.jsp中使用el获取 --%>
				<c:set var="url" value="${pageContext.request.contextPath }/cart/list.action"></c:set>
				<%-- 把分页页面以静态引用的方式引用过来 --%>
				<%@ include file="/WEB-INF/commons/page.jsp" %>
			</c:if>
		</div>
	</body>
	
	<jsp:include page="/WEB-INF/commons/js.jsp"></jsp:include>
	<script type="text/javascript">
		// jquery 的入口
		$(function(){
			
			// 改变商品数量（+按键/-按键）
			function modifyQuantity(){
				// console.log(this);
				// console.log($(this).attr('data-itemid')); // 标签设置额外属性 data-itemid=商品编号
				var _self = this, order = $(this).parents('.order');
				var total = order.find('input');
				var amount = order.find('.total-amount');
				var action = $(this).hasClass('btnAdd') ? '+' : '-';
				$.post('${pageContext.request.contextPath }/cart/modifyQuantity.action',
						// 请求的方法和url参数（传递商品id 和 加或减的动作）
						{ action: action, id: $(this).attr('data-itemid') }, function (res){
							// 服务器端设置了响应头 Context-Type:application/json, jquery会自动将返回的json字符串变成json对象（即：特殊js对象）
							if (!$.isEmptyObject(res)) {
								total.val(res.total); // 改变input标签中的文本值
								amount.text(res.money); // 改变该数量标签首尾之间的html文本内容
							}
							// 每从数据库中改写数量和金额后，都为该按键重新绑定点击事件一次
							$(_self).one('click', modifyQuantity);
						}
					);
			};
			// 为该按键绑定一次点击事件
			$('.btnAdd, .btnSub').one('click', modifyQuantity);
			
			
			// 删除单个商品
			$('.glyphicon-trash').on('click', function(){
				// 设置隐藏标签name=ids的值
				$('#delFrm [name=id]').val($(this).attr('data-itemid'));
				// 获取当前的页值
				// TODO 判断要删除的数量有没超出整个页面数据, 如果超出，那么要将页数(除第一页外)-1
				var page = $('#bookpage-a .crt a').text();
				if(!$.isEmptyObject(page)){
					// 设置隐藏标签name=p的值
					$('#delFrm [name=p]').val(page);
				}
				if(confirm("确定删除吗？")) {
					// 点击确定时，表单提交
					$('#delFrm').submit();
				}
				
			});
			
			
			// 支付批量选中的商品  
			$('.batch-buy').on('click', function(){
				var checkeds = $(':checked'); // 选择选中的复选框
				// 数据变换
				var ids = $.map(checkeds, function(v,i){
					//console.log(v,i);
					return $(v).val();
				}).join(','); // 将返回的数组拼接成字符串，并且以,分隔开
				// 发起异常请求
				$.post('${pageContext.request.contextPath }/order/create.action', {ids:ids}, function(res){ 
					if(res != '-1') {
						location.reload();
					}
				});
			});
			
		});
	</script>
		
</html>