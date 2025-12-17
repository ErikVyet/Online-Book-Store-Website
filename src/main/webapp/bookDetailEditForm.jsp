<%@page import="dto.BookDTO"%>
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
    <link rel="stylesheet" href="./resource/css/bookDetailEditForm.css">
    <script src="./resource/js/bookDetailEditForm.js"></script>
    <title>Book Details</title>
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
	BookDTO book = (BookDTO) request.getAttribute("BOOK");
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
            <form class="form-container" action="./book?action=update" method="post" enctype="multipart/form-data">
                <div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/id.png"> ID</label>
                        <input name="id" type="text" value="<%=book.getId()%>" autocomplete="off" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/title.png"> Title</label>
                        <input name="title" type="text" value="<%=book.getTitle()%>" autocomplete="off" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/category.png"> Category</label>
                        <select name="category" disabled>
<%
					List<CategoryDTO> categories = CategoryDAO.getList();
					for (CategoryDTO category : categories) {
						if (category.getId() == book.getCategoryId()) {
%>
							<option value="<%=category.getId()%>" selected="selected"><%=category.getName()%></option>
<%
						}
						else {
%>
                            <option value="<%=category.getId()%>"><%=category.getName()%></option>
<%
						}	
					}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/author.png"> Author</label>
                        <select name="author" disabled>
<%
					List<AuthorDTO> authors = AuthorDAO.getList();
					for (AuthorDTO author : authors) {
						if (author.getId() == book.getAuthorId()) {
%>
							<option value="<%=author.getId()%>" selected="selected"><%=author.getName()%></option>
<%
						}
						else {
%>
							<option value="<%=author.getId()%>"><%=author.getName()%></option>
<%
						}
					}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/publisher.png"> Publisher</label>
                        <select name="publisher" disabled>
<%
					List<PublisherDTO> publishers = PublisherDAO.getList();
					for (PublisherDTO publisher : publishers) {
						if (publisher.getId() == book.getPublisherId()) {
%>
                            <option value="<%=publisher.getId()%>" selected="selected"><%=publisher.getName()%></option>
<%
						}
						else {
%>
							<option value="<%=publisher.getId()%>"><%=publisher.getName()%></option>
<%
						}
					}
%>
                        </select>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/quantity.png"> Quantity</label>
                        <input name="quantity" type="number" min="0" value="<%=book.getQuantity()%>" required readonly>
                    </div>
                </div>
                <div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/price.png"> Price</label>
                        <input name="price" type="number" min="0" value="<%=book.getPrice()%>" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/description.png"> Description</label>
                        <input name="description" type="text" value="<%=book.getDescription()%>" readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/date.png"> Publication</label>
                        <input name="publication" type="date" value="<%=book.getPublication().toString()%>" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/image.png"> Image</label>
                        <input name="image" onchange="changeImage(this)" type="file" accept="image/*" disabled>
                    </div>
                    <div id="image-display-area">
                        <img id="image-display-frame" src="<%=book.getImage()%>"> 
                    </div>
                </div>
                <div>
                    <input id="btnSave" type="submit" value="Save">
                    <input id="btnCancel" onclick="viewMode()" type="button" value="Cancel">
                    <input id="btnEdit" onclick="editMode()" type="button" value="Edit">
                    <a id="btnBack" href="./books.jsp">Back</a>
                </div>
            </form>
        </main>
        <footer>
            <p>&copy; Đồ án thực tập chuyên ngành</p>
        </footer>
    </div>
</body>
</html>