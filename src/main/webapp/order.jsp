<%@page import="dao.OrderDAO"%>
<%@page import="model.ShippingStatus"%>
<%@page import="dto.OrderDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/order.css">
    <script src="./resource/js/order.js" async="true"></script>
    <title>Order</title>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (user == null) {
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
			<div class="order-container">
                <table class="order-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Created Date</th>
                            <th>Order Status</th>
                            <th>Shipping Status</th>
                            <th>Estimated Date</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
<%
              		List<OrderDTO> orders = OrderDAO.getListForUser(user.getId());
					for (OrderDTO order : orders) {
%>
                        <tr>
                            <td><%=order.getId()%></td>
                            <td><%=order.getDate().toString()%></td>
                            <td><%=order.getStatus()%></td>
                            <td><%=order.getShipping().getStatus()%></td>
                            <td><%=order.getShipping().getShipped_date() == null ? "" : order.getShipping().getShipped_date()%></td>
                            <td>
                                <a href="./order?action=detail&userId=<%=user.getId()%>&orderId=<%=order.getId()%>">Detail</a>
<%
							if (order.getShipping().getStatus() == ShippingStatus.Delivered ||
								order.getShipping().getStatus() == ShippingStatus.Cancelled) {
%>
                                <button disabled>Cancel</button>
<%
							}
							else {
%>
								<button onclick="cancelOrder(<%=order.getId()%>)">Cancel</button>
<%
							}
%>
                            </td>
                        </tr>
<%
					}
%>
                    </tbody>
                </table>
            </div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>