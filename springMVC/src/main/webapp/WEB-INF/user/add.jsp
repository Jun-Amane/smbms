<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="user/add">
            <div>
                <label for="userCode">用户编码：</label>
                <input type="text" name="userCode" id="userCode" value="">
                <!-- 放置提示信息 -->
                <font color="red"></font>
            </div>
            <div>
                <label for="userName">用户姓名：</label>
                <input type="text" name="userName" id="userName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label >用户性别：</label>
                <input type="radio" name="gender" value="1">男
                <input type="radio" name="gender" value="2">女
            </div>
            <div>
                <label for="birthday">出生日期：</label>
                <input type="text" Class="Wdate" id="birthday" name="birthday"
                       readonly="readonly" onclick="WdatePicker();">
                <font color="red"></font>
            </div>
            <div>
                <label for="phone">用户电话：</label>
                <input type="text" name="phone" id="phone" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="address">用户地址：</label>
                <input name="address" id="address">
            </div>
            <div>
                <label >用户角色：</label>
                <!-- 列出所有的角色分类 -->
                <select name="role.id" id="userRole">
                    <c:forEach items="${requestScope.roleList}" var="role">
                        <option value="${role.id}">${role.roleName}</option>
                    </c:forEach>
                </select>
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="submit" name="add" id="add" value="保存" >
                <input type="button" id="back" name="back" onclick="history.back()" value="返回" >
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="../common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/useradd.js"></script>
