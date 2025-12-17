<%@page import="dto.CartDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="model.CartStatus"%>
<%@page import="dao.CartDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/cart.css">
    <script src="./resource/js/cart.js" async="true"></script>
    <title>Cart</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (user == null) {
		response.sendRedirect("./view/login.jsp");
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
			<div class="cart-container">
                <table>
                    <thead>
                        <tr>
                            <th>Cart ID</th>
                            <th>Items</th>
                            <th>Created</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
<%
              	List<CartDTO> carts = CartDAO.getListForUser(user.getId());
				for (CartDTO cart : carts) {
					if (cart.getStatus() == CartStatus.available) {
%>
						<tr>
                            <td><%=cart.getId()%></td>
                            <td><%=cart.getItems().size()%></td>
                            <td><%=cart.getCreated().toString()%></td>
                            <td>
                            	<a onclick="showPaymentDialog(<%=cart.getId()%>)">Place Order</a>
                                <a href="./cart?action=detail&userId=<%=user.getId()%>&cartId=<%=cart.getId()%>">Detail</a>
                                <a onclick="deleteCart(<%=cart.getId()%>, this)">Delete</a>
                            </td>
                        </tr>
<%
					}
				}
%>
                    </tbody>
                </table>
            </div>
            <div class="payment-container" id="payment-form">
                <h2>Payment</h2>
                <div class="payment-info-group">
                    <label><img width="4%" src="./resource/image/payment.png"> Method</label>
                    <select name="method" onchange="switchMethod(this.value)">
                        <option value="1">Cash on Delivery</option>
                        <option value="2">Visa</option>
                    </select>
                </div> 
                <div class="payment-container-divider"><hr/></div>
                <form class="cod-container" method="post">
                	<div class="payment-info-group">
	                    <label><img width="4%" src="./resource/image/transport.png"> Method</label>
	                    <select name="transport">
	                        <option value="1">Standard</option>
	                        <option value="2">Express</option>
	                    </select>
	                </div>
                	<div>
	                    <input type="submit" value="Order Now">
	                    <input type="button" value="Cancel" onclick="hidePaymentDialog()">
	                </div>
                </form>
                <form class="visa-container" method="post">
                    <div class="payment-info-group">
                        <label><img width="4%" src="./resource/image/credit-number.png"> Number</label>
                        <input type="text" onkeypress="return checkOnlyNumberInput(event)" name="visa-number" placeholder="XXXX-YYYY-xxxx-yyyy" minlength="16" maxlength="16" autocomplete="off" required>
                    </div>
                    <div class="payment-info-group">
                        <label><img width="4%" src="./resource/image/date.png"> Expiry Date</label>
                        <input type="month" name="visa-expiry" required>
                    </div>
                    <div class="payment-info-group">
                        <label><img width="4%" src="./resource/image/password.png"> CVC/CVV</label>
                        <input type="password" onkeypress="return checkOnlyNumberInput(event)" name="visa-code" minlength="3" maxlength="3" required>
                    </div>
                    <div class="payment-info-group">
	                    <label><img width="4%" src="./resource/image/transport.png"> Method</label>
	                    <select name="transport">
	                        <option value="1">Standard</option>
	                        <option value="2">Express</option>
	                    </select>
	                </div>
                    <p id="visa-error-message">${ ERROR }</p>
                    <div>
	                    <input type="submit" value="Order Now">
	                    <input type="button" value="Cancel" onclick="hidePaymentDialog()">
	                </div>
                </form>
            </div>
		</main>
		<footer>
			<p>&copy; Đồ án thực tập chuyên ngành</p>
		</footer>
	</div>
</body>
</html>