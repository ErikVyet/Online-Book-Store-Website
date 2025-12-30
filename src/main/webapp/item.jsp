<%@page import="dto.BookDTO"%>
<%@page import="dao.BookDAO"%>
<%@page import="dto.CartItemDTO"%>
<%@page import="dto.CartDTO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/item.css">
    <script src="./resource/js/item.js"></script>
    <title>Items</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	UserDTO user = (UserDTO) session.getAttribute("USER");
	if (user == null) {
		response.sendRedirect("./login.jsp");
		return;
	}
	CartDTO cart = (CartDTO)request.getAttribute("CART");
	List<CartItemDTO> items = cart.getItems();
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
			<div class="cart-detail-container">
                <div class="cart-detail-info">
                    <label>Cart ID</label>
                    <p><%=cart.getId()%></p>
                </div>
                <div class="cart-detail-info">
                    <label>Created</label>
                    <p><%=cart.getCreated()%></p>
                </div>
                <div class="cart-detail-info">
                    <label>Total Item(s)</label>
                    <p id="quantity"><%=cart.getItems().size()%></p>
                </div>
                <div class="cart-detail-info">
                    <label>Total Cost</label>
                    <p id="total">${ COST } VNĐ</p>
                </div>
                <div class="cart-item-container">
                    <table class="cart-item-table">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
						for (CartItemDTO item : items) {
							BookDTO book = BookDAO.find(item.getBookId());
%>
							<tr>
	                            <td><%=book.getTitle()%></td>
	                            <td><input name="quantity" type="number" value="<%=item.getQuantity()%>" max="<%=book.getQuantity()%>" readonly></td>
	                            <td><%=book.getPrice()%> VNĐ</td>
	                            <td>
	                                <button name="btnEdit" onclick="itemEditMode(this.parentNode)">Edit</button>
	                                <button name="btnSave" onclick="saveChanges(<%=item.getCartId()%>)">Save</button>
	                                <button name="btnCancel" onclick="cancelChanges(this.parentNode)">Cancel</button>
	                                <button name="btnDelete" onclick="deleteItem(<%=item.getCartId()%>, <%=item.getBookId()%>, this.parentNode.parentNode)">Delete</button>
	                            </td>
	                            <td><input name="book" value="<%=item.getBookId()%>" type="hidden"></td>
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