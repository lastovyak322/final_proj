
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>


<head>
    <title>Магазин</title>
</head>
<body>
<%@ include file="/jspf/header.jspf" %>
<br>
<br>
<br>
<br>
<br>
<br>
<fmt:message key="main_page.jsp.categories" />
<br>
<a href="/hello?command=showProductsByCategory&categoryId=1">Kick scooters</a>
<br>
<a href="/hello?command=showProductsByCategory&categoryId=2">Hoverboards</a>
<br>



</body>
</html>