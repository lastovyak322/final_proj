<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 06.09.21
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>

<fmt:message key="header.jsp.admin_menu" />
<p><a href="change_user_status_page.jsp" >Block/Unblock User</a></p>
<p><a href="/hello?command=getAllAccountOrders" >Show Orders</a></p>
</body>
</html>
