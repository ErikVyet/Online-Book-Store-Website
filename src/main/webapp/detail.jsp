<%@page import="dao.BookDAO"%>
<%@page import="dto.BookDTO"%>
<%@page import="dto.OrderDTO"%>
<%@page import="dto.OrderDetailDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/detail.css">
    <title>Detail</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (user == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
	OrderDTO order = (OrderDTO) request.getAttribute("ORDER");
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
				<a id="profile" href="./profile.jsp"><img width="80%" src="./resource/image/user.png"></a>
				<p><%=user.getUsername()%></p>
			</div>
		</header>
		<nav class="side-navbar">
			<hr />
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
			<hr />
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
			<div class="order-detail-container">
                <div class="order-detail-info-group">
                    <label>Payment</label>
                    <p><%=order.getPayment().toString()%></p>
                </div>
                <div class="order-detail-info-group">
                    <label>Shipping Method</label>
                    <p><%=order.getShipping().getMethod().toString()%></p>
                </div>
                <div class="order-detail-info-group">
                    <label>Address</label>
                    <p><%=user.getAddress()%></p>
                </div>
                <div class="order-detail-info-group">
                    <label>Total</label>
                    <p><%=order.getTotal()%> VNĐ</p>
                </div>
                <div>
                    <table class="order-detail-table">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
<%
						List<OrderDetailDTO> details = order.getDetails();
						for (OrderDetailDTO detail : details) {
							BookDTO book = BookDAO.find(detail.getBookId());
%>
                            <tr>
                                <td><%=book.getTitle()%></td>
                                <td><%=detail.getQuantity()%></td>
                                <td><%=detail.getPrice()%> VNĐ</td>
                            </tr>
<%
						}
%>
                        </tbody>
                    </table>
                </div>
            </div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>