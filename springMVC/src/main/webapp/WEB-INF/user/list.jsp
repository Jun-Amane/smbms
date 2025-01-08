<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<script>
  function deleteById(id){
    let flag=window.confirm("你确定要删除"+id+"号用户码?")
    if(flag==true){
      window.location="user/delete?id="+id;
    }
  }
</script>
<div class="right">
  <div class="location">
    <strong>你现在所在的位置是:</strong>
    <span>用户管理页面</span>
  </div>
  <div class="search">
    <form method="post" action="user/list">
      <input name="method" value="query" class="input-text" type="hidden">
      <span>用户姓名：</span>
      <input name="userName" class="input-text"	type="text" value="${queryUserName }">

      <span>用户角色：</span>
      <select name="role.id">
        <c:if test="${requestScope.roleList != null }">
          <option value="0">--请选择--</option>
          <c:forEach var="role" items="${requestScope.roleList}">
            <option <c:if test="${param['role.id'] eq role.id}">selected</c:if> value="${role.id}">${role.roleName}</option>
          </c:forEach>
        </c:if>
      </select>
      <input	value="查 询" type="submit" id="searchbutton">
      <a href="user/toAdd" >添加用户</a>
    </form>
  </div>
  <!--用户-->
  <table class="providerTable" cellpadding="0" cellspacing="0">
    <tr class="firstTr">
      <th width="10%">用户编码</th>
      <th width="20%">用户姓名</th>
      <th width="10%">性别</th>
      <th width="10%">年龄</th>
      <th width="10%">电话</th>
      <th width="10%">用户角色</th>
      <th width="30%">操作</th>
    </tr>
    <c:forEach var="user" items="${requestScope.pageInfo.list}" varStatus="status">
      <tr>
        <td>
          <span>${user.userCode }</span>
        </td>
        <td>
          <span>${user.userName }</span>
        </td>
        <td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
        </td>
        <td>
          <span>${user.age}</span>
        </td>
        <td>
          <span>${user.phone}</span>
        </td>
        <td>
          <span>${user.role.roleName}</span>
        </td>
        <td>
          <span><a class="viewUser" href="user/detail?id=${user.id}"}><img src="statics/images/read.png" alt="查看" title="查看"/></a></span>
          <span><a class="modifyUser" href="user/toModify?id=${user.id}"}><img src="statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
          <span><a class="deleteUser" href="javascript:deleteById(${user.id})"}><img src="statics/images/schu.png" alt="删除" title="删除"/></a></span>
        </td>
      </tr>
    </c:forEach>
  </table>
  <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
  <c:import url="../common/rollpage.jsp">
  </c:import>
</div>
</section>



<%@include file="../common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>
