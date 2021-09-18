<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 13.09.21
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>


</head>
<body>
<p><a href="main_page.jsp">Main Page</a></p>
Product cart
<c:choose>
    <c:when test="${sessionScope.cart!=null}">
        <c:set var="blockBuy" value="${true}" scope="request"/>
        <c:set var="totalPrice" value="${0}"/>
        <jsp:useBean id="cart" scope="session" type="java.util.Map"/>
        <c:forEach var="entry" items="${cart}">
            <c:set var="totalPrice" value="${totalPrice + entry.key.price*entry.value}"/>
            <p>Product: ${entry.key.name}</p>
            <p>
                Amount:
                <c:if test="${entry.value>1}">
                    <a href="/hello?command=decreaseAmount&productId=${entry.key.id}">-</a>
                </c:if>
                    ${entry.value}
                <a href="/hello?command=addToCart&productId=${entry.key.id}">+</a>
                <a href="/hello?command=deleteProduct&productId=${entry.key.id}">Delete</a>
            </p>
            <c:if test="${entry.key.amount<entry.value }">
                Not so much in stock
                <c:set var="blockBuy" value="${true}" scope="request"/>
                <c:set var="blockBuy" value="${true}" scope="page"/>
            </c:if>
        </c:forEach>
        <br>
        <br>
        Total price
        ${totalPrice}

        <c:choose>
            <c:when test="${sessionScope.account==null}">Only register user can buy </c:when>
            <c:when test="${pageScope.blockBuy!=null}"> Cant buy</c:when>
            <c:otherwise>
                <a href="/hello?command=registerBuy">Buy</a>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        is empty
    </c:otherwise>

</c:choose>
<br>


</body>
</html>
