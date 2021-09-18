<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 17.09.21
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:useBean id="accountOrderList" scope="request" type="java.util.List"/>
    <jsp:useBean id="accountOrder" class="com.epam.lastoviak.online_store.db.dto.AccountOrder"/>
</head>
<body>
<p><a href="../main_page.jsp">Main Page</a></p>

<c:forEach var="accountOrder" items="${accountOrderList}">
    Order id:${accountOrder.id}
    Account id:${accountOrder.accountId}
    Status:${accountOrder.statusId}
    <p><a href="/hello?command=accountOrderDetailedPage&accountOrderId=${accountOrder.id}">Details</a></p>
    <%--    Approved by:${accountOrder.approvedBy}--%>
    <br>
    <%--    <p><a href="/hello?command=productDetailedPage&productId=${product.id}">Details </a></p>--%>
</c:forEach>
<c:forEach begin="1" end="${requestScope.numbOfPages}" var="i">
    <c:choose>
        <c:when test="${requestScope.currentPage eq i}">
            ${i}
        </c:when>
        <c:otherwise>
            <a href="/hello?page=${i}&command=getAllAccountOrders">${i}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>
