<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 06.09.21
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>


<fmt:message key="header.jsp.user_menu" />
<a href="/hello?command=getAccountOrdersByAccountId"><fmt:message key="account_settings.jsp.orders_history"/></a>




</body>
</html>
