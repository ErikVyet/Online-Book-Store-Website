<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/recovery.css">
    <script src="./resource/js/recovery.js" async="true"></script>
    <title>Recovery</title>
</head>
<body>
	<form class="recovery-form" action="./user?action=recovery" method="post">
        <h2>Recovery</h2>
        <div class="recovery-input-group">
            <label><img width="5%" src="./resource/image/email.png"> Email</label>
            <input type="email" name="email" value="${ EMAIL }" required>
        </div>
        <div class="recovery-input-group">
            <label><img width="5%" src="./resource/image/verified.png"> Code</label>
            <div>
                <input type="text" name="code" required>
                <hr/>
                <button type="button" id="code" onclick="disableGetCode()">Get Code</button>
            </div>
        </div>
        <div class="recovery-input-group">
            <label><img width="5%" src="./resource/image/password.png"> Password</label>
            <input type="password" name="password" required>
        </div>
        <div class="recovery-input-group">
            <label><img width="5%" src="./resource/image/password.png"> Confirm Password</label>
            <input type="password" name="confirm-password" required>
        </div>
        <p id="error">${ ERROR }</p>
        <div>
            <button type="submit">Reset</button>
        </div>
        <div>
            <a href="./login.jsp">Back</a>
        </div>
    </form>
</body>
</html>