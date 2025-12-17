<%@page import="dto.CategoryDTO"%>
<%@page import="dto.CartDTO"%>
<%@page import="dto.UserDTO"%>
<%@page import="dao.CartDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="model.CartStatus"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/info.css">
    <script src="./resource/js/info.js"></script>
    <title>Info</title>
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
	<div class="rating-dialog">
        <div>
            <label>How do you feel about this book?</label>
            <div>
                <input name="rate" type="number" min="0" max="5" value="3"> &#11088;
            </div>
        </div>
        <div>
<%
		if (user == null) {
%>
			<button>Submit</button>
<%
		}
		else {
%>
			<button onclick="submitRating(${ BOOK.id }, <%=user.getId()%>)">Submit</button>
<%
		}
%>
            <button onclick="hideRatingDialog()">Cancel</button>
        </div>
    </div>
    <div class="put-in-cart-dialog">
        <h2>Detail</h2>
        <div>
            <label>Quantity:</label>
            <div>
                <input name="quantity" type="number" min="1" max="${ BOOK.quantity }" value="1">
            </div>
        </div>
        <div>
            <label>Cart:</label>
            <div>
                <select name="cart">
<%
		if (user != null) {
			List<CartDTO> carts = CartDAO.getListForUser(user.getId());
			for (CartDTO cart : carts) {
				if (cart.getStatus() == CartStatus.available) {	
%>
					<option value="<%=cart.getId()%>">Created on <%=cart.getCreated()%></option>
<%
				}
			}
		}
%>
                    <option value="0">Create new cart</option>
                </select>
            </div>
        </div>
        <div>
<%
		if (user == null) {
%>
			<button>Add to cart</button>
<%
		}
		else {
%>
			<button onclick="addItemToCart(${ BOOK.id }, <%=user.getId()%>)">Add to cart</button>
<%
		}
%>
            
            <button onclick="hideConfirmDialog()">Cancel</button>
        </div>
    </div>

    <div class="container">
        <header>
            <div class="banner">
                <h1><img width="12%" src="./resource/image/banner.png"> BOOKZ</h1>
            </div>
            <nav class="navbar">
                <a class="navbar-item" href="#">Privacy</a>
            </nav>
            <form action="./book?action=search" method="post" class="searchbar">
                <input name="content" type="text" value="" placeholder="Search your book here..." autocomplete="off">
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
            <hr/>
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
            <hr/>
<%
		if (user == null) {
%>
			<a onclick="alertGuest()" class="side-navbar-item">
                <img width="8%" src="./resource/image/user.png">
                <span>Profile</span>
            </a>
<%
		}
		else {
%>
			<a href="./profile.jsp" class="side-navbar-item">
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
            <div class="book-detail-container">
                <div class="book-detail-image">
                    <img src="${ BOOK.image }" alt="${ BOOK.id }">
                </div>
                <div class="book-detail-info">
                    <h1>${ BOOK.title }</h1>
                    <h2>by ${ BOOK.author }</h2>
                    <textarea readonly>${ BOOK.description }</textarea>
                    <hr/>
                    <div>
                        <p>Rating</p>
                        <div>
                            <p id="point">${ BOOK.rating }</p>&#11088;
<%
						if (user == null) {
%>
							<button onclick="alertGuest()">Rate</button>
<%
						}
						else {
%>
							<button onclick="showRatingDialog()">Rate</button>
<%
						}
%>
                        </div>
                    </div>
                    <div>
                        <p>Publisher</p>
                        <p>${ BOOK.publisher }</p>
                    </div>
                    <div>
                        <p>First Publish</p>
                        <p>${ BOOK.publication }</p>
                    </div>
                    <div>
                    	<p>Category</p>
                    	<p>${ BOOK.category }</p>
                    </div>
                    <div>
                        <p>Available</p>
                        <p>${ BOOK.quantity }</p>
                    </div>
                    <div>
                        <p>Price</p>
                        <p>${ BOOK.price } VNĐ</p>
                    </div>
                    <div>
<%
					if (user == null) {
%>
						<button onclick="alertGuest()">Put in cart</button>
<%
					}
					else {
%>
						<button onclick="showConfirmDialog()">Put in cart</button>
<%
					}
%>
                    </div>
                </div>
            </div>
        </main>
        <footer>
            <p>&copy; Đồ án thực tập chuyên ngành</p>
        </footer>
    </div>
</body>
</html>