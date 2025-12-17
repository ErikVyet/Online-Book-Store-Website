<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/register.css">
    <script src="./resource/js/register.js" async="true"></script>
    <title>Register</title>
</head>
<body>
	<form class="register-form" action="./user?action=register" method="post">
        <h2>Register</h2>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/fullname.png"> Fullname</label>
            <input type="text" name="fullname" required>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/phone.png"> Phone</label>
            <input type="tel" name="phone" maxlength="10" required>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/address.png"> Address</label>
            <input type="text" name="address" required>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/email.png"> Email</label>
            <input type="email" name="email" required>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/verified.png"> Code</label>
            <div>
                <input type="text" name="code" required>
                <hr/>
                <button type="button" id="code" onclick="disableGetCode()">Get Code</button>
            </div>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/username.png"> Username</label>
            <input type="text" name="username" required>
        </div>
        <div class="register-input-group">
            <label><img width="5%" src="./resource/image/password.png"> Password</label>
            <input type="password" name="password" required>
        </div>
        <p id="message">${ ERROR }</p>
        <div>
            <button type="submit">Register</button>
        </div>
        <div>
            Already had an account?
            <a href="./login.jsp">Login</a>
        </div>
    </form>
</body>
</html>