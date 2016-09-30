<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
	/* page */
	.m-page{height:50px;margin-top:10px;padding-right:20px;}
	.m-page .u-page,.m-page .u-page-go{float:right;margin-left:15px;}
	.m-page .u-page-go{margin-top:3px;}	
	.u-page{position:relative;zoom:1;}
	.u-page ul{float:left;}
	.u-page li{float:left;margin-right:5px;}
	.u-page .pre,.u-page .next{margin-right:1px;}
	.u-page .crt a{background:#a6978a;color:#fff;}
	.u-page .crt a:hover{color:#fff;}
	.u-page a{float:left;display:block;height:30px;padding:0 10px;line-height:30px;text-align:center;color:#a6978a;}
	.u-page .init,.u-page .init:hover{color:#aaa;cursor:default;text-decoration:none;}
	.u-page-go{position:relative;zoom:1;color:#a6978a;}
	.u-page-go *{vertical-align:middle;}
	.u-page-go .txt{width:30px;height:18px;padding:2px 3px;margin:0 5px;border:1px solid #e0dad5;text-align:center;line-height:20px;}
	.u-page-go .btn{display:inline;top:24px;left:29px;width:38px;height:22px;line-height:22px;background:#e14e37;color:#fff;text-align:center;}
	.u-page-go .btn:hover{text-decoration:underline;}
	.u-page-total{margin-right:15px;}
</style>

<!-- 分页信息  -->
<div class="m-page j-pager">
	<div class="u-page-go">
		<span class="u-page-total">共${pb.totalPage }页</span>
		<%-- 总页数如果大于1页，显示去第几页和确定按钮 --%>
		<c:if test="${pb.totalPage gt 1}">	
			<span>去第</span><input type="text" class="txt j-input" value=""><span>页</span>
			<a href="javascript:void(0);" class="btn j-confirm">确定</a>
		</c:if>
	</div>
	<div class="u-page">
		<ul id="bookpage-a">
			<%-- 如果分页参数p为1或者不存在则为第一页，第一页不显示上一页 --%>
			<c:if test="${param.p !=1 && param.p != null }">
				<a href="${url}&p=${pb.currentPage-1}" class="pre " hidefocus="hidefocus">上一页</a>
			</c:if>

			<%-- 计算begin和end --%>
			<c:choose>
				<%-- 如果总页数<=6，那么显示所有页码，即begin=1 end=${pb.totalPage} --%>
				<c:when test="${pb.totalPage <= 6 }">
					<c:set var="begin" value="1" />
					<c:set var="end" value="${pb.totalPage }" />
				</c:when>
				<c:otherwise>
					<%-- 设置begin=当前页码-2，end=当前页码+2 --%>
					<c:set var="begin" value="${pb.currentPage-2 }" />
					<c:set var="end" value="${pb.currentPage+2 }" />
					<c:choose>
						<%-- 如果begin<1，那么让begin=1 end=5 --%>
						<c:when test="${begin<1}">
							<c:set var="begin" value="1" />
							<c:set var="end" value="5" />
						</c:when>
						<%-- 如果end>最大页，那么begin=最大页-5 end=最大页 --%>
						<c:when test="${end>pb.totalPage}">
							<c:set var="begin" value="${pb.totalPage-5 }" />
							<c:set var="end" value="${pb.totalPage}" />
						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<%-- 显示点点点 --%>
			<c:if test="${begin == 2}">
				<li><a href="${url}&p=1">1</a></li>
			</c:if>
			<c:if test="${begin > 2}">
				<li><a href="${url}&p=1">1</a></li>
				<li>...</li>
			</c:if>
			<%-- 显示分布 1,2,3 --%>
			<c:forEach var="i" begin="${begin}" end="${end}">
				<%-- 相等时加入激活样式 --%>
				<c:choose>
					<c:when test="${pb.currentPage eq i }">
						<li class="crt"><a href="${url}&p=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${url}&p=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<%-- 显示点点点 --%>
			<c:if test="${end < pb.totalPage }">
				<li>...</li>
				<li><a href="${url}&p=${pb.totalPage}">${pb.totalPage}</a></li>
			</c:if>
		</ul>
		<%-- 如果当前页为最后一页则不显示下一页 --%>
		<c:if test="${pb.currentPage != pb.totalPage }">
			<a href="${url}&p=${pb.currentPage+1}" class="next " hidefocus="hidefocus">下一页</a>
		</c:if>
	</div>
</div>
