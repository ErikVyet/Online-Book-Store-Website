<%@page import="dao.AuthorDAO"%>
<%@page import="dto.AuthorDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/authors.css">
    <script src="./resource/js/authors.js"></script>
    <title>Author Manager</title>
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
            <div class="author-manager-container">
                <div class="author-manager-header">
                    <div class="author-manager-add">
                        <a href="./authorAddForm.jsp">Add New Author</a>
                    </div>
                    <div class="author-manager-search">
                        <input name="search" type="text" placeholder="Search authors...">
                        <div>
                            <button onclick="searchAuthors()" title="Search"><img width="80%" alt="Search" src="./resource/image/search.png"></button>
                        </div>
                    </div>
                    <div></div>
                </div>
                <div class="author-manager-list">
                    <table class="author-manager-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Bio</th>
                                <th>Birth</th>
                                <th>Country</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
						List<AuthorDTO> authors = AuthorDAO.getList();
						for (AuthorDTO author : authors) {
%>
                            <tr>
                                <td><%=author.getId()%></td>
                                <td><%=author.getName()%></td>
                                <td title="<%=author.getBio()%>"><%=author.getBio()%></td>
                                <td><%=author.getBirth().toString()%></td>
                                <td><%=author.getCountry()%></td>
                                <td>
                                    <a href="./author?action=detail&id=<%=author.getId()%>">Detail</a>
                                    <a onclick="deleteAuthor(this.parentNode.parentNode, <%=author.getId()%>)">Delete</a>
                                </td>
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