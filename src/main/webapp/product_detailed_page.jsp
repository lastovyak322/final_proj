
<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 12.09.21
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <jsp:useBean id="productDescription" scope="request" class="com.epam.lastoviak.online_store.db.dto.ProductDescription"/>
    <jsp:useBean id="productSpecification" scope="request" class="com.epam.lastoviak.online_store.db.dto.ProductSpecification"/>
    <jsp:useBean id="product" scope="request" class="com.epam.lastoviak.online_store.db.dto.Product"/>
    <title>Title</title>
</head>
<body>
<p>${product.name}</p>
<p>Max Speed: ${productSpecification.maxSpeed}</p>
<p>Max Load: ${productSpecification.maxLoad}</p>
<p>Power Reserve: ${productSpecification.powerReserve}</p>
<p>Manufacturer: ${productSpecification.manufacturer}</p>

<p>Description:</p>
    <br>
    ${productDescription.description}
<br>
<br>
Price: ${product.price} <a href="/hello?command=addToCart&productId=${product.id}">Buy</a>

</body>
</html>
