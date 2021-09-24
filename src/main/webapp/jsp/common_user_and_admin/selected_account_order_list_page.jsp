<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:useBean id="accountOrderList" scope="request" type="java.util.List"/>
    <jsp:useBean id="accountOrder" class="com.epam.lastoviak.online_store.db.dto.AccountOrder"/>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>

<c:forEach var="accountOrder" items="${accountOrderList}">
    Order id:${accountOrder.id}
    <c:if test="${sessionScope.roleId==2}"> Account id:${accountOrder.accountId}</c:if>
    Status:${accountOrder.statusId}
    <p><a href="/hello?command=accountOrderDetailedPage&accountOrderId=${accountOrder.id}">Details</a></p>
    <br>
</c:forEach>
<c:forEach begin="1" end="${requestScope.numbOfPages}" var="i">
    <c:choose>
        <c:when test="${requestScope.currentPage eq i}">
            ${i}
        </c:when>
        <c:otherwise>
            <a href="/hello?page=${i}&command=getAccountOrdersByAccountId">${i}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>

</body>
</html>
