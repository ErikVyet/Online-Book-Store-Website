<%@page import="dto.AuthorDTO"%>
<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/authorDetailEditForm.css">
    <script src="./resource/js/authorDetailEditForm.js"></script>
    <title>Author Details</title>
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
	AuthorDTO author = (AuthorDTO) request.getAttribute("AUTHOR");
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
            <form class="form-container" action="./author?action=update" method="post">
                <div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/id.png"> ID</label>
                        <input name="id" type="text" value="<%=author.getId()%>" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/name.png"> Name</label>
                        <input name="name" type="text" value="<%=author.getName()%>" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/description.png"> Bio</label>
                        <textarea name="bio" readonly><%=author.getBio()%></textarea>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/date.png"> Birth</label>
                        <input name="birth" type="date" value="<%=author.getBirth().toString()%>" required readonly>
                    </div>
                    <div class="form-input-group">
                        <label><img src="./resource/image/country.png"> Country</label>
                        <input name="country" type="text" value="<%=author.getCountry()%>" required readonly>
                    </div>
                </div>
                <div>
                    <input id="btnSave" type="submit" value="Save">
                    <input id="btnCancel" onclick="viewMode()" type="button" value="Cancel">
                    <input id="btnEdit" onclick="editMode()" type="button" value="Edit">
                    <a id="btnBack" href="./authors.jsp">Back</a>
                </div>
            </form>
        </main>
        <footer>
            <p>&copy; Đồ án thực tập chuyên ngành</p>
        </footer>
    </div>
</body>
</html>