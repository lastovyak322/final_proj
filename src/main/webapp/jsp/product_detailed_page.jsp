

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pdp" scope="request" class="com.epam.lastoviak.online_store.db.dto.ProductDetailedPage"/>
<html>
<head>

    <title>Title</title>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>
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
