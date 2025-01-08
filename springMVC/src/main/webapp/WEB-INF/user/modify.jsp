<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<div class="right">
  <div class="location">
    <strong>你现在所在的位置是:</strong>
    <span>用户管理页面 >> 用户修改页面</span>
  </div>
  <div class="providerAdd">
    <form id="userForm" name="userForm" method="post" action="user/modify">
      <input type="hidden" name="id" value="${user.id}"/>
      <div>
        <label for="userName">用户名称：</label>
        <input type="text" name="userName" id="userName" value="${user.userName }">
        <font color="red"></font>
      </div>
      <div>
        <label >用户性别：</label>
        <input type="radio" name="gender" value="1" <c:if test="${user.gender eq 1}">checked</c:if>>男
        <input type="radio" name="gender" value="2" <c:if test="${user.gender eq 2}">checked</c:if>>女
      </div>
      <div>
        <label for="birthday">出生日期：</label>
        <input type="text" Class="Wdate" id="birthday" name="birthday" value="${user.birthday }"
               readonly="readonly" onclick="WdatePicker();">
        <font color="red"></font>
      </div>

      <div>
        <label for="phone">用户电话：</label>
        <input type="text" name="phone" id="phone" value="${user.phone }">
        <font color="red"></font>
      </div>
      <div>
        <label for="address">用户地址：</label>
        <input type="text" name="address" id="address" value="${user.address }">
      </div>
      <div>
        <label >用户角色：</label>
        <!-- 列出所有的角色分类 -->
        <select name="role.id" id="userRole">
          <c:forEach  var="role" items="${requestScope.rolelist}">
            <option <c:if test="${role.id eq user.role.id}">selected</c:if> value="${role.id}">${role.roleName}</option>
          </c:forEach>

        </select>
        <font color="red"></font>
      </div>
      <div class="providerAddBtn">
        <input type="submit" name="save" id="save" value="保存" />
        <input type="button" id="back" name="back" onclick="history.back()" value="返回"/>
      </div>
    </form>
  </div>
</div>
</section>
<%@include file="../common/foot.jsp" %>

