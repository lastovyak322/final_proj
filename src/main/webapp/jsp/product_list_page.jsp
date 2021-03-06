<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 07.09.21
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <jsp:useBean id="product" class="com.epam.lastoviak.online_store.db.dto.Product"/>
    <jsp:useBean id="productList" scope="request" type="java.util.List"/>
    <title>Title</title>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>

<c:forEach var="product" items="${productList}">
    <p>${product.name}</p>
    <p>${product.price}</p>
    <p><a href="/hello?command=productDetailedPage&productId=${product.id}">Details </a></p>
</c:forEach>
<br>


<%--For displaying Previous link except for the 1st page --%>
<c:if test="${requestScope.currentPage != 1}">
    <a href="/hello?page=${requestScope.currentPage - 1}&command=showProductsByCategory">Previous</a>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${requestScope.numbOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td>
                        <a href="/hello?page=${i}&command=showProductsByCategory&${requestScope.manufacturer}&maxPrice=${requestScope.maxPrice}&minPrice=${requestScope.minPrice}&orderBy=${requestScope.orderBy}&filter=${requestScope.filter}&categoryId=${requestScope.categoryId}">${i}</a>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>
<br>
Filter
<form action="/hello">
    <input type="hidden" name="filter" value="doFilter"/>
    <input type="hidden" name="command" value="showProductsByCategory"/>
    <input type="hidden" name="categoryId" value="${requestScope.categoryId}"/>

    <input type="text" name="minPrice"> Min Price
    <br>
    <input type="text" name="maxPrice"> Max price
    <br>
    Manufacturer
    <br>
    <label>
        <input type="checkbox" name="manufacturer" value="1">
    </label> 1
    <br>
    <label>
        <input type="checkbox" name="manufacturer" value="2">
    </label> 2

    <br>
    Sort by
    <br>
    <select name="orderBy" onchange="submit()">
        <option selected value="def">default</option>
        <option value="cheap">cheap->exp</option>
        <option value="expensive">exp->cheap</option>
        <option value="name">a-z</option>
        <option value="s4">z-a</option>
        <option value="nameReverse">new->old</option>
        <option value="new">old->new</option>
    </select>

    <br>
    <input type="submit" value="Search">
    <a href="/hello?command=showProductsByCategory&filter=resetFilter&categoryId=${requestScope.categoryId}">Reset</a>
</form>


</body>
</html>
