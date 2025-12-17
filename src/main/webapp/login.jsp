<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/login.css">
    <title>Login</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%>
	<form class="login-form" action="./user?action=login" method="post">
        <h2>Login</h2>
        <div class="login-input-group">
            <label><img width="5%" src="./resource/image/email.png"> Email</label>
            <div>
                <input type="email" id="email" name="email" value="${ EMAIL }" required>
            </div>
        </div>
        <div class="login-input-group">
            <label><img width="5%" src="./resource/image/username.png"> Username</label>
            <div>
                <input type="text" id="username" name="username" value="${ USERNAME }" required>
            </div>
        </div>
        <div class="login-input-group">
            <label><img width="5%" src="./resource/image/password.png"> Password</label>
            <div>
                <input type="password" id="password" name="password" autocomplete="off" required>
            </div>
        </div>
        <div>
            <p>${ ERROR }</p>
            <a href="./recovery.jsp">Forgot password</a>
        </div>
        <div>
            <button type="submit">Login</button>
            <hr/>
            <a href="./user?action=guestLogin">Login as Guest</a>
        </div>
        <div>
            <p>Or sign up using</p>
            <div class="sign-up-links">
                <a href="#"><img width="8%" src="./resource/image/google-icon.png"></a>
                <a href="#"><img width="8%" src="./resource/image/facebook-icon.png"></a>
                <a href="#"><img width="8%" src="./resource/image/twitter-icon.png"></a>
            </div>
            <p>Or</p>
            <div>
                <a href="./register.jsp">Sign up</a>
            </div>
        </div>
    </form>
</body>
</html>