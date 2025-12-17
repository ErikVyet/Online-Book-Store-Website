<%@page import="model.OrderStatus"%>
<%@page import="dao.OrderDAO"%>
<%@page import="dto.OrderDTO"%>
<%@page import="model.ShippingStatus"%>
<%@page import="dao.ShippingDAO"%>
<%@page import="dto.ShippingDTO"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resource/css/shippings.css">
    <script src="./resource/js/shippings.js"></script>
    <title>Shipping Manager</title>
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
			<div class="shipping-manager-container">
                <div class="shipping-manager-header"></div>
                <div class="shipping-manager-list">
                    <table class="shipping-manager-table">
                        <thead>
                            <tr>
                                <th>Shipping's ID</th>
                                <th>Order's ID</th>
                                <th>Order' Status</th>
                                <th>Shipping' Status</th>
                                <th>Method</th>
                                <th>Estimated Date</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
<%
						List<ShippingDTO> shippings = ShippingDAO.getList();
						for (ShippingDTO shipping : shippings) {
							OrderDTO order = OrderDAO.find(shipping.getOrderId());
%>
                            <tr>
                                <td><%=shipping.getId()%></td>
                                <td><%=shipping.getOrderId()%></td>
                                <td><%=order.getStatus()%></td>
                                <td><%=shipping.getStatus()%></td>
                                <td><%=shipping.getMethod()%></td>
                                <td><%=shipping.getShipped_date() == null ? "NULL" : shipping.getShipped_date().toString()%></td>
<%
							if (shipping.getStatus() == ShippingStatus.Cancelled || shipping.getStatus() == ShippingStatus.Delivered || order.getStatus() != OrderStatus.Confirmed) {
%>
                                <td>
                                    <button title="The shipping' status is either Delivered or Cancelled or the order' status is still yet Confirmed" disabled="disabled">Update</button>
                                </td>
<%
							}
							else {
%>
								<td>
                                    <button onclick="showUpdateDialog(this, <%=shipping.getId()%>)">Update</button>
                                </td>
<%
							}
%>
                            </tr>
<%
						}
%>
                        </tbody>
                    </table>
                    <div class="shipping-update-form" draggable="true" ondrag="dragUpdateDialog()">
                        <h3>Update Shipping</h3>
                        <div class="shipping-info-group">
                            <label><img width="4%" src="./resource/image/id.png"> Shipping's ID</label>
                            <input type="text" name="shipping-id" readonly>
                        </div>
                        <div class="shipping-info-group">
                            <label><img width="4%" src="./resource/image/status.png"> Status</label>
                            <select name="shipping-status" onchange="shippingStatusChanges()">
<%
							for (ShippingStatus status : ShippingStatus.values()) {
%>
                                <option value="<%=status%>"><%=status%></option>
<%
							}
%>                               
                            </select>
                        </div>
                        <div class="shipping-info-group">
                            <label><img width="4%" src="./resource/image/date.png"> Estimated Date</label>
                            <input type="date" name="shipping-date">
                        </div>
                        <div>
                            <button onclick="updateShippingStatus()">Update</button>
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