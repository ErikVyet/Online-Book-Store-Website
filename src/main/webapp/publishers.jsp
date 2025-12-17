<%@page import="dao.PublisherDAO"%>
<%@page import="dto.PublisherDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/publishers.css">
    <script src="./resource/js/publishers.js"></script>
    <title>Publisher Manager</title>
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
            <div class="publisher-manager-container">
                <div class="publisher-manager-header">
                    <div class="publisher-manager-add">
                        <a href="./publisherAddForm.jsp">Add New Publisher</a>
                    </div>
                    <div class="publisher-manager-search">
                        <input name="search" type="text" placeholder="Search publishers...">
                        <div>
                            <button onclick="searchPublishers()" title="Search"><img width="80%" alt="Search" src="./resource/image/search.png"></button>
                        </div>
                    </div>
                    <div></div>
                </div>
                <div class="publisher-manager-list">
                    <table class="publisher-manager-table">
                        <thead>
                            <tr>
                            	<th>ID</th>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Phone</th>
                                <th>Email</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
						List<PublisherDTO> publishers = PublisherDAO.getList();
						for (PublisherDTO publisher : publishers) {
%>
                            <tr>
                            	<td><%=publisher.getId()%></td>
                                <td><%=publisher.getName()%></td>
                                <td title="<%=publisher.getAddress()%>"><%=publisher.getAddress()%></td>
                                <td><%=publisher.getPhone()%></td>
                                <td><%=publisher.getEmail()%></td>
                                <td>
                                    <a href="./publisher?action=detail&id=<%=publisher.getId()%>">Detail</a>
                                    <a onclick="deletePublisher(this.parentNode.parentNode, <%=publisher.getId()%>)">Delete</a>
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