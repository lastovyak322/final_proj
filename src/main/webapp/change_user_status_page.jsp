<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 14.09.21
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/hello">
    <input type="hidden" name="command" value="findAccount"/>
    <input type="text" name="accountId"/>
    <input type="submit" value="Find">
</form>
<c:if test="${requestScope.account!=null}">

        <p>Name:${requestScope.account.name}</p>
        <p>Email:${requestScope.account.email}</p>
        <p>Status: ${requestScope.account.statusId}</p>
               Change Status:
    <form>
        <input type="hidden" name="accountId" value="${requestScope.account.id}">
        <input type="hidden" name="command" value="changeAccountStatus">

        <select name="statusId">
            <option value="2">Block</option>
            <option value="1">Unblock</option>
        </select>
        <p><input type="submit" value="change"></p>
    </form>
</c:if>

</body>
</html>
