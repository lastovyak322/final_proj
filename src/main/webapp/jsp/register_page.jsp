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
    <input type="hidden" name="command" value="register"/>
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="username"><b>Name</b></label>
        <input type="text" placeholder="Enter Name" name="name" id="username" required>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required>

        <label for="phone"><b>Phone</b></label>
        <input type="text" placeholder="Enter Phone" name="phone" id="phone" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="psw" required>

<%--        <label for="psw-repeat"><b>Repeat Password</b></label>--%>
<%--        <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>--%>
        <hr>

        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit" class="registerbtn">Register</button>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="login_page.jsp">Sign in</a>.</p>
    </div>
</form>
</body>
</html>
