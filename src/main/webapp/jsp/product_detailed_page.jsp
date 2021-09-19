
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

    <jsp:useBean id="pdp" scope="request" class="com.epam.lastoviak.online_store.db.dto.ProductDetailedPage"/>

    <title>Title</title>
</head>
<body>
<p>${pdp.name}</p>
<p>Max Speed: ${pdp.maxSpeed}</p>
<p>Max Load: ${pdp.maxLoad}</p>
<p>Manufacturer: ${pdp.manufacturer}</p>

<p>Description:</p>
    <br>
    ${pdp.description}
<br>
<br>
Price: ${pdp.price} <a href="/hello?command=addToCart&productId=${pdp.id}">Buy</a>

</body>
</html>
