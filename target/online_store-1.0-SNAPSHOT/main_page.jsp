
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <title>Магазин</title>
</head>
<body>
<br>
<br>
<br>
<c:choose>
    <c:when test="${sessionScope.account==null}"> <a href="login_page.jsp"> Login></a></c:when>
    <c:when test="${sessionScope.account.roleId==2}"><a href="admin_page.jsp"> AdminMenu</a> </c:when>
    <c:when test="${sessionScope.account.roleId==1}"><a href="account_settings_page.jsp"> UserMenu</a></c:when>
</c:choose>




<br>



</body>
</html>