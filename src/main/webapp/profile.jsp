<%@page import="dao.UserDAO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/profile.css">
    <script src="./resource/js/profile.js" async="true"></script>
    <title>Profile</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
	if (userDTO == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
	
	UserDTO user = UserDAO.getUser(userDTO.getId());
%>
	<div class="container">
        <header>
            <div class="banner">
                <h1><img width="12%" src="./resource/image/banner.png"> BOOKZ</h1>
            </div>
            <nav class="navbar">
                <a class="navbar-item" href="#">Privacy</a>
            </nav>
            <form action="./book?action=search" method="post" class="searchbar">
                <input name="content" type="text" placeholder="Search your book here..." autocomplete="off">
                <button type="submit" title="Search"><img width="55%" src="./resource/image/search.png" alt="Search"></button>
            </form>
            <form action="./book?action=filter" method="post" class="filterbar">
                <select name="filter-option">
<%
                List<CategoryDTO> categories = CategoryDAO.getList();
				for (CategoryDTO category : categories) {
%>
					<option value="<%=category.getId()%>"><%=category.getName()%></option>
<%
				}
%>
                </select>
                <button type="submit" title="Filter"><img width="100%" src="./resource/image/filter.png" alt="Filter"></button>
            </form>
            <div class="user">
                <a id="profile" href="./profile.jsp"><img width="80%" src="./resource/image/user.png"></a>
				<p><%=user.getUsername()%></p>
            </div>
        </header>
        <nav class="side-navbar">
            <hr/>
            <a href="./home.jsp" class="side-navbar-item">
                <img width="8%" src="./resource/image/home.png">
                <span>Discover</span>
            </a>
            <a href="./cart.jsp" class="side-navbar-item">
                <img width="8%" src="./resource/image/cart.png">
                <span>Cart</span>
            </a>
            <a href="./order.jsp" class="side-navbar-item">
                <img width="8%" src="./resource/image/package.png">
                <span>Order</span>
            </a>
            <a href="#" class="side-navbar-item">
                <img width="8%" src="./resource/image/info.png">
                <span>About</span>
            </a>
            <hr/>
            <a href="./profile.jsp" class="side-navbar-item">
                <img width="8%" src="./resource/image/user.png">
                <span>Profile</span>
            </a>
            <a href="./user?action=logout" class="side-navbar-item">
                <img width="8%" src="./resource/image/logout.png">
                <span>Logout</span>
            </a>
        </nav>
        <main>
            <div class="user-info-container">
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/fullname.png"> Fullname</label>
                    <input type="text" name="fullname" value="<%=user.getFullname()%>" required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/email.png"> Email</label>
                    <input type="email" name="email" value="<%=user.getEmail()%>" disabled required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/phone.png"> Phone</label>
                    <input type="tel" name="phone" value="<%=user.getPhone()%>" required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/address.png"> Address</label>
                    <input type="text" name="address" value="<%=user.getAddress()%>" required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/username.png"> Username</label>
                    <input type="text" name="username" value="<%=user.getUsername()%>" required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/password.png"> Password</label>
                    <input type="password" name="password" value="<%=user.getPassword()%>" disabled required readonly>
                </div>
                <div class="user-info-group">
                    <label><img width="1.5%" src="./resource/image/date.png"> Created</label>
                    <input type="date" name="created" value="<%=user.getCreated().toString()%>" disabled required readonly>
                </div>
                <p id="error"></p>
                <div class="user-info-action-button-group">
                    <button type="submit" id="save" onclick="saveButton(this, <%=user.getId()%>)">Save</button>
                    <button type="button" id="cancel" onclick="cancelButton(this)">Cancel</button>
                    <button type="button" id="edit" onclick="editButton(this)">Edit</button>
                </div>
            </div>
        </main>
        <footer>
            <p>&copy; Đồ án thực tập chuyên ngành</p>
        </footer>
    </div>
</body>
</html>