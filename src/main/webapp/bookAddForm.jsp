<%@page import="dao.PublisherDAO"%>
<%@page import="dto.PublisherDTO"%>
<%@page import="dao.AuthorDAO"%>
<%@page import="dto.AuthorDTO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page import="model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/bookAddForm.css">
    <script src="./resource/js/bookAddForm.js"></script>
    <title>Book Insert Form</title>
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
            <h1><img width="10%" src="./resource/image/banner.png">BOOKZ</h1>
        </header>
        <nav class="side-navbar">
            <ul>
                <hr/>
                <li class="welcome-user">
                    <div><img width="70%" src="./resource/image/user.png"></div>
                    <a> Welcome, <%=user.getFullname()%></a>
                </li>
                <hr/>
                <li>
                    <div><img width="70%" src="./resource/image/dashboard.png"></div>
                    <a href="./dashboard.jsp"> Dashboard</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/book.png"></div>
                    <a href="./books.jsp"> Book</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/category.png"></div>
                    <a href="./categories.jsp"> Category</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/author.png"></div>
                    <a href="./authors.jsp"> Author</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/publisher.png"></div>
                    <a href="./publishers.jsp"> Publisher</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/order.png"></div>
                    <a href="./orders.jsp"> Order</a>
                </li>
                <li>
                    <div><img width="70%" src="./resource/image/transport.png"></div>
                    <a href="./shippings.jsp"> Shipping</a>
                </li>
                <hr/>
                <li>
                    <div><img width="70%" src="./resource/image/logout.png"></div>
                    <a href="./user?action=logout"> Logout</a>
                </li>
            </ul>
        </nav>
        <main>
            <form action="./book?action=add" method="post" enctype="multipart/form-data" class="form-container">
                <div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/title.png"> Title</label>
                        <input name="title" type="text" autocomplete="off" required>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/category.png"> Category</label>
                        <select name="category">
<%
						List<CategoryDTO> categories = CategoryDAO.getList();
						for (CategoryDTO category : categories) {
%>
                            <option value="<%=category.getId()%>"><%=category.getName()%></option>
<%
						}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/author.png"> Author</label>
                        <select name="author">
<%
						List<AuthorDTO> authors = AuthorDAO.getList();
						for (AuthorDTO author : authors) {
%>
							<option value="<%=author.getId()%>"><%=author.getName()%></option>
<%
						}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/publisher.png"> Publisher</label>
                        <select name="publisher">
<%
						List<PublisherDTO> publishers = PublisherDAO.getList();
						for (PublisherDTO publisher : publishers) {
%>
                            <option value="<%=publisher.getId()%>"><%=publisher.getName()%></option>
<%
						}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/quantity.png"> Quantity</label>
                        <input name="quantity" type="number" min="0" value="0" required>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/price.png"> Price</label>
                        <input name="price" type="number" min="0" value="0" required>
                    </div>
                </div>
                <div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/description.png"> Description</label>
                        <input name="description" type="text">
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/date.png"> Publication</label>
                        <input name="publication" type="date" required>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/image.png"> Image</label>
                        <input name="image" onchange="changeImage(this)" type="file" accept="image/*" required>
                    </div>
                    <div id="image-display-area">
                        <img id="image-display-frame" src=""> 
                    </div>
                </div>
                <div>
                    <input type="submit" value="Add Book">
                    <a href="./books.jsp">Back</a>
                </div>
            </form>
        </main>
        <footer>
            <p>&copy; Đồ án thực tập chuyên ngành</p>
        </footer>
    </div>
</body>
</html>