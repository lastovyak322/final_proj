
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
    <c:when test="${sessionScope.account.roleId==2}"><a href="admin/admin_page.jsp"> AdminMenu</a> </c:when>
    <c:when test="${sessionScope.account.roleId==1}"><a href="common_user_and_admin/account_settings_page.jsp"> UserMenu</a></c:when>
</c:choose>
<p><a href="account_cart_page.jsp">Cart</a> </p>
<br>
<br>
<br>
Categories
<br>
<a href="/hello?command=showProductsByCategory&categoryId=1&filter=OFF">Kick scooters</a>
<br>
<a href="/hello?command=showProductsByCategory&categoryId=2&filter=OFF">Hoverboards</a>





<br>



</body>
</html>