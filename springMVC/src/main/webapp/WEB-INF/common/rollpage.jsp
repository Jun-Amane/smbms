<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function goToUrl(pageNum){
		let formObject=document.getElementsByTagName("form")[0];
		formObject.action=formObject.action+"?pageNum="+pageNum;
		formObject.submit();
	}
</script>
 		<div class="page-bar">
			<ul class="page-num-ul clearfix">
				<li>共${requestScope.pageInfo.total }条记录&nbsp;&nbsp; ${requestScope.pageInfo.pageNum }/${requestScope.pageInfo.pages}页</li>
				<c:if test="${requestScope.pageInfo.pageNum> 1}">
					<a href="javascript:goToUrl(1)">首页</a>
					<a href="javascript:goToUrl(${requestScope.pageInfo.pageNum-1})">上一页</a>
				</c:if>
				<c:if test="${requestScope.pageInfo.pageNum <requestScope.pageInfo.pages }">
					<a href="javascript:goToUrl(${requestScope.pageInfo.pageNum+1})">下一页</a>
					<a href="javascript:goToUrl(${requestScope.pageInfo.pages})">最后一页</a>
				</c:if>
				&nbsp;&nbsp;
			</ul>
		 <span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="inputPage" id="inputPage" class="page-key" />页
	     <button type="button" class="page-btn" onClick='goToUrl(document.getElementById("inputPage").value)'>GO</button>
		</span>
		</div> 
