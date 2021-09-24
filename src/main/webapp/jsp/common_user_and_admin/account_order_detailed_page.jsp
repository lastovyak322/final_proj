<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 17.09.21
  Time: 01:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:useBean id="accountOrderProductList" scope="request" type="java.util.List"/>
    <jsp:useBean id="accountOrderProduct" class="com.epam.lastoviak.online_store.db.dto.AccountOrderProduct"/>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>


<c:set var="totalPrice" value="${0}"/>
<c:forEach var="accountOrderProduct" items="${accountOrderProductList}">
    <c:set var="totalPrice" value="${totalPrice + accountOrderProduct.price*accountOrderProduct.count}"/>
    Product id:${accountOrderProduct.productId}
    Price for one:${accountOrderProduct.price}
    Amount:${accountOrderProduct.count}
    <br>
</c:forEach>
Total price:
${totalPrice}
</body>
</html>
