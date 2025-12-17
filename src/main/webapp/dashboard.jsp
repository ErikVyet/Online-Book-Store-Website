<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="./resource/css/dashboard.css">
	<script src="./resource/js/dashboard.js" defer></script>
	<title>Dashboard</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (user == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
	if (user.getRole() != Role.admin) {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
		return;
	}
%>
	<div class="container">
		<header>
			<h1>
				<img width="10%" src="./resource/image/banner.png">BOOKZ
			</h1>
		</header>
		<nav class="side-navbar">
			<ul>
				<hr />
				<li class="welcome-user">
					<div>
						<img width="70%" src="./resource/image/user.png">
					</div>
					<a> Welcome, <%=user.getFullname()%></a>
				</li>
				<hr />
				<li>
					<div>
						<img width="70%" src="./resource/image/dashboard.png">
					</div>
					<a href="./dashboard.jsp"> Dashboard</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/book.png">
					</div>
					<a href="./books.jsp"> Book</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/category.png">
					</div>
					<a href="./categories.jsp"> Category</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/author.png">
					</div>
					<a href="./authors.jsp"> Author</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/publisher.png">
					</div>
					<a href="./publishers.jsp"> Publisher</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/order.png">
					</div>
					<a href="./orders.jsp"> Order</a>
				</li>
				<li>
					<div>
						<img width="70%" src="./resource/image/transport.png">
					</div>
					<a href="./shippings.jsp"> Shipping</a>
				</li>
				<hr />
				<li>
					<div>
						<img width="70%" src="./resource/image/logout.png">
					</div>
					<a href="./user?action=logout"> Logout</a>
				</li>
			</ul>
		</nav>
		<main>
			<div class="dashboard-content">
                <div class="card" id="book-card" onclick="changeStatistic(0)">
                    <h2>Books</h2>
                    <img width="40%" src="./resource/image/book-dashboard.png">
                </div>
                <div class="card" id="revenue-card" onclick="changeStatistic(1)">
                    <h2>Revenues</h2>
                    <img width="40%" src="./resource/image/revenue-dashboard.png">
                </div>
                <div class="card" id="order-card" onclick="changeStatistic(2)">
                    <h2>Orders</h2>
                    <img width="40%" src="./resource/image/order-dashboard.png">
                </div>
                <div class="card" id="customer-card" onclick="changeStatistic(3)">
                    <h2>Customers</h2>
                    <img width="40%" src="./resource/image/customer-dashboard.png">
                </div>
            </div>
            <div class="chart-container">
            	<div class="chart-wrapper">
            		<canvas id="canva"></canvas>
            	</div>
                <div>
                    <h3>Criteria</h3>
                    <select name="criteria" onchange="changeCriteria(0, this.value)">
                        <option value="0">Numbers of Books by Category</option>
                        <option value="1">Numbers of Books By Author</option>
                        <option value="2">Numbers of Books by Publisher</option>
                        <option value="3">Best-Selling Books</option>
                        <option value="4">Ratings Distribution</option>
                        <option value="5">Stock Status</option>
                    </select>
                </div>
            </div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>