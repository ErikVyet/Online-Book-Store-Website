<%@page import="dao.BookDAO"%>
<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/books.css">
    <script src="./resource/js/books.js"></script>
    <title>Book Manager</title>
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
            <div class="book-manager-container">
                <div class="book-manager-header">
                    <div class="book-manager-add">
                        <a href="./bookAddForm.jsp">Add New Book</a>
                    </div>
                    <div class="book-manager-search">
                        <input name="search" type="text" placeholder="Search books...">
                        <div>
                            <button onclick="searchBooks()" title="Search"><img width="80%" alt="Search" src="./resource/image/search.png"></button>
                        </div>
                    </div>
                    <div class="book-manager-filter">
                        <select name="filter">
                            <option value="id">ID</option>
                            <option value="title">Title</option>
                            <option value="price">Price</option>
                            <option value="quantity">Quantity</option>
                            <option value="publication">Publication</option>
                        </select>
                        <div>
                            <input type="radio" name="sort" value="asc" checked> Ascending
                            <input type="radio" name="sort" value="desc"> Descending
                        </div>
                        <div>
                            <button onclick="filterBooks()" title="Filter"><img width="80%" alt="Filter" src="./resource/image/filter.png"></button>
                        </div>
                    </div>
                </div>
                <div class="book-manager-list">
                    <table class="book-manager-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Publication</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
						List<BookDTO> books = BookDAO.getList();
						for (BookDTO book : books) {
%>
                            <tr>
                                <td><%=book.getId()%></td>
                                <td title="<%=book.getTitle()%>"><%=book.getTitle()%></td>
                                <td><%=book.getPrice()%> VNĐ</td>
                                <td><%=book.getQuantity()%></td>
                                <td><%=book.getPublication().toString()%></td>
                                <td>
                                    <a href="./book?action=detail&id=<%=book.getId()%>">Detail</a>
                                    <a onclick="deleteBook(this.parentNode.parentNode, <%=book.getId()%>)">Delete</a>
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