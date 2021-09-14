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
Admin page
<form action="/hello" method="get">
    <input type="hidden" name="command" value="logout">
    <button type="submit">Logout</button>

</form>
</body>
</html>
