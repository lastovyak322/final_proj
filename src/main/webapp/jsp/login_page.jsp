<%--
  Created by IntelliJ IDEA.
  User: synteztech
  Date: 05.09.21
  Time: 01:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/register.css">

</head>
<body>
<form action="/hello" method="post">
    <input type="hidden" name="command" value="login"/>
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to log in.</p>
        <hr>


        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required>


        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="psw" required>

        <%--        <label for="psw-repeat"><b>Repeat Password</b></label>--%>
        <%--        <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>--%>
        <hr>


        <button type="submit" class="registerbtn">Log in</button>
        <p>dont have account ?<a href="register_page.jsp">Registration</a>.</p>
    </div>

</form>
</body>
</html>
