<%@page import="dto.CategoryDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Result</title>
	<link rel="stylesheet" href="./resource/css/result.css">
	<script src="./resource/js/home.js" async="true"></script>
	<script src="./resource/js/result.js" async="true"></script>
</head>
<body>

<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (session.getAttribute("GUEST") == null && user == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
%>
	<div class="container">
		<header>
			<div class="banner">
				<h1>
					<img width="12%" src="./resource/image/banner.png"> BOOKZ
				</h1>
			</div>
			<nav class="navbar">
				<a class="navbar-item" href="#">Privacy</a>
			</nav>
			<form action="./book?action=search" method="post" class="searchbar">
				<input name="content" type="text" placeholder="Search your book here..." autocomplete="off">
				<button type="submit" title="Search">
					<img width="55%" src="./resource/image/search.png" alt="Search">
				</button>
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
				<button type="submit" title="Filter">
					<img width="100%" src="./resource/image/filter.png" alt="Filter">
				</button>
			</form>
			<div class="user">
<%
			if (user == null) {
%>
				<a href="./login.jsp">Login for more features</a>
<%
			}
			else {
%>
				<a id="profile" href="./profile.jsp"><img width="80%" src="./resource/image/user.png"></a>
				<p><%=user.getUsername()%></p>
<%
			}
%>
			</div>
		</header>
		<nav class="side-navbar">
			<hr />
			<a href="./home.jsp" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/home.png"> 
				<span>Discover</span>
			</a>
<%
		if (user == null) {			
%>
			<a onclick="alertGuest()" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/cart.png"> 
				<span>Cart</span>
			</a> 
			<a onclick="alertGuest()" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/package.png"> 
				<span>Order</span>
			</a> 
<%
		}
		else {
%>
			<a href="./cart.jsp" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/cart.png"> 
				<span>Cart</span>
			</a> 
			<a href="./order.jsp" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/package.png"> 
				<span>Order</span>
			</a> 
<%
		}
%>
			<a href="#" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/info.png">
				<span>About</span>
			</a>
			<hr />
<%
		if (user != null) {
%>
			<a href="./profile.jsp" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/user.png"> 
				<span>Profile</span>
			</a> 
<%
		}
		else {
%>
			<a onclick="alertGuest()" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/user.png"> 
				<span>Profile</span>
			</a> 
<%
		}
%>
			<a href="./user?action=logout" class="side-navbar-item"> 
				<img width="8%" src="./resource/image/logout.png"> 
				<span>Logout</span>
			</a>
		</nav>
		<main>
			<div class="result-container">
                <p>Result: ${ COUNT } item(s)</p>
                <div>
                	<c:forEach var="book" items="${ RESULT }">
	                	<div class="result-book" title="${ book.title }" onclick="redirectToDetail(${ book.id })">
	                        <img src="${ book.image }" alt="${ book.id }">
	                        <p>${ book.title }</p>
	                        <p>${ book.author }</p>
	                    </div>
                	</c:forEach>
                    <hr/>
                </div>
            </div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>