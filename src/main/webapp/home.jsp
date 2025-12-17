<%@page import="dao.AuthorDAO"%>
<%@page import="dto.BookDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dao.BookDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Home</title>
	<link rel="stylesheet" href="./resource/css/home.css">
	<script src="./resource/js/home.js" async="true"></script>
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
		<main class="discover-container">
			<div class="discover-recommended-container">
				<div>
					<p>Recommended</p>
					<div>
						<a href="./book?action=seeAll">See All</a>
					</div>
				</div>
				<div>
<%
				List<BookDTO> recommendedBooks = BookDAO.getSortedListByRating(false);
				int size = recommendedBooks.size() > 4 ? 4 : recommendedBooks.size();
				for (int i = 0; i < size; i++) {
					BookDTO book = recommendedBooks.get(i);
%>
					<div class="discover-recommended-book" onclick="changeActiveBookFromRecommended(<%=i%>)" title="<%=book.getTitle()%>">
						<img src="<%=book.getImage()%>" alt="<%=book.getId()%>">
						<p><%=book.getTitle()%></p>
						<p><%=book.getAuthor()%></p>
					</div>
<% 
				}
%>
				</div>
			</div>
			<div class="discover-categories-container">
				<div>
					<p>Categories</p>
				</div>
				<div>
					<button class="discover-categories-option" onclick="chooseCategory(this, -1)">All</button>
<%
				
				for (CategoryDTO category : categories) {
%>
					<button class="discover-categories-option" onclick="chooseCategory(this, <%=category.getId()%>)"><%=category.getName()%></button>
<%
				}
%>
				</div>
				<div class="discover-category-book-list">
<% 
				List<BookDTO> categoryBooks = BookDAO.getList();
				int j = 0;
				for (BookDTO book : categoryBooks) {
%>
					<div class="discover-category-book" onclick="changeActiveBookFromCategory(<%=j++%>)" title="<%=book.getTitle()%>">
						<img src="<%=book.getImage()%>" alt="<%=book.getId()%>">
						<p><%=book.getTitle()%></p>
						<p><%=book.getAuthor()%></p>
					</div>
<%
				}
%>
				</div>
			</div>
			<div class="discover-active-book-container">
<%
			BookDTO latestBook = BookDAO.getLatestBook();
%>
				<div>
					<img src="<%=latestBook.getImage()%>" alt="<%=latestBook.getId()%>" id="active-book-image">
				</div>
				<div>
					<h2 id="active-book-title"><%=latestBook.getTitle()%></h2>
					<h3 id="active-book-author"><%=latestBook.getAuthor()%></h3>
					<div><h4 id="active-book-rating"><%=latestBook.getRating()%> </h4>&#11088;</div>
					<textarea id="active-book-description" readonly><%=latestBook.getDescription()%></textarea>
					<a id="active-book-detail" href="./book?action=detail&id=<%=latestBook.getId()%>">Detail</a>
				</div>
			</div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>