
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 07.09.21
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <jsp:useBean id="product" class="com.epam.lastoviak.online_store.db.dto.Product"/>
  <jsp:useBean id="productList" scope="request" type="java.util.List"/>
  <title>Title</title>
</head>
<body>
<br>

<c:forEach var="product"  items="${productList}">
  <p>${product.name}</p>
  <p>${product.price}</p>
</c:forEach>
<br>
Filter
<form action="/hello">

  <%--    <input type="text" name="minPrice"> Min Price--%>
  <br>
  <%--    <input type="text" name="maxPrice"> Max price--%>
  <br>
  Manufacturer
  <br>
  <%--    <input type="checkbox" name="selectType" value="1"> Samsung--%>
  <br>
  <%--    <input type="checkbox" name="selectType" value="2"> Xiaomi--%>

  <%--<input type="checkbox" name="selectType" value="manufacturer" > Manufacturer--%>
  <br>
  Sort by
  <br>
  <select name="orderBy" >
    <option selected value="def">default</option>
    <option value="cheap">cheap->exp</option>
    <option value="expensive">exp->cheap</option>
    <option value="name">a-z</option>
    <option value="s4">z-a</option>
    <option value="nameReverse">new->old</option>
    <option value="new">old->new</option>
  </select>


  <br>
  <input type="submit" name="command" value="showProductsByCategory&categoryId=">
</form>


${requestScope.categoryId}
</body>
</html>
