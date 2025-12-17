<%@page import="model.OrderStatus"%>
<%@page import="dao.OrderDAO"%>
<%@page import="dto.OrderDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page import="model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/orders.css">
    <script src="./resource/js/orders.js"></script>
    <title>Order Manager</title>
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
			<div class="order-manager-container">
                <div class="order-manager-header"></div>
                <div class="order-manager-list">
                    <table class="order-manager-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Created Date</th>
                                <th>Total</th>
                                <th>Status</th>
                                <th>Payment</th>
                                <th>Address</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
					List<OrderDTO> orders = OrderDAO.getList();
					for (OrderDTO order : orders) {
						if (order.getStatus() == OrderStatus.Pending) {
%>
                            <tr>
                                <td><%=order.getId()%></td>
                                <td><%=order.getDate().toString()%></td>
                                <td><%=order.getTotal()%> VNĐ</td>
                                <td><%=order.getStatus()%></td>
                                <td><%=order.getPayment()%></td>
                                <td title="<%=order.getAddress()%>"><%=order.getAddress()%></td>
                                <td>
                                    <button onclick="showUpdateDialog(this, <%=order.getId()%>)">Update Status</button>
                                </td>
                            </tr>
<%
						}
						else {
%>
							<tr>
                                <td><%=order.getId()%></td>
                                <td><%=order.getDate().toString()%></td>
                                <td><%=order.getTotal()%> VNĐ</td>
                                <td><%=order.getStatus()%></td>
                                <td><%=order.getPayment()%></td>
                                <td title="<%=order.getAddress()%>"><%=order.getAddress()%></td>
                                <td>
                                    <button disabled>Update Status</button>
                                </td>
                            </tr>
<%				
						}
					}
%>
                        </tbody>
                    </table>
                    <div class="order-update-form" draggable="true" ondrag="dragUpdateDialog()">
                        <h3>Update Status</h3>
                        <div class="order-info-group">
                            <label><img width="4%" src="./resource/image/id.png"> Order's ID</label>
                            <input type="text" name="order-id" readonly>
                        </div>
                        <div class="order-info-group">
                            <label><img width="4%" src="./resource/image/status.png"> Status</label>
                            <select name="order-status">
<%
							for (OrderStatus status : OrderStatus.values()) {                            
%>
								<option value="<%=status%>"><%=status%></option>
<%
							}
%>
                            </select>
                        </div>
                        <div>
                            <button onclick="updateOrderStatus()">Update</button>
                            <button onclick="hideUpdateDialog()">Cancel</button>
                        </div>
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