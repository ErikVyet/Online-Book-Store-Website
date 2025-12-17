package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.BookDAO;
import dao.CartDAO;
import dao.OrderDAO;
import dto.BookDTO;
import dto.CartDTO;
import dto.CartItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.ShippingDTO;
import dto.UserDTO;
import model.Cart;
import model.CartStatus;
import model.Order;
import model.OrderStatus;
import model.Payment;
import model.Role;
import model.ShippingMethod;

@WebServlet("/order")
public class OrderController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();
       
    public OrderController() {
        super();
    }
    
    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if (session != null) {
    		UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    		int payment = Integer.parseInt(request.getParameter("payment"));
        	int cartId = Integer.parseInt(request.getParameter("cartId"));
        	ShippingMethod transport = ShippingMethod.values()[Integer.parseInt(request.getParameter("transport")) - 1];
        	CartDTO cartDTO = CartDAO.find(cartId);
        	
        	OrderDTO orderDTO = new OrderDTO();
    		orderDTO.setUserId(userDTO.getId());
    		orderDTO.setDate(Date.valueOf(LocalDate.now()));
    		orderDTO.setStatus(OrderStatus.Pending);
    		orderDTO.setAddress(userDTO.getAddress());
    		double total = 0;
    		for (CartItemDTO item : cartDTO.getItems()) {
    			BookDTO bookDTO = BookDAO.find(item.getBookId());
    			OrderDetailDTO detail = new OrderDetailDTO();
    			detail.setBookId(bookDTO.getId());
    			if (item.getQuantity() > bookDTO.getQuantity()) {
    				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Sorry for this inconvenience, it seems like we don't have enough books to fulfill your order, please try again later");
    				return;
    			}
    			detail.setQuantity(item.getQuantity());
    			detail.setPrice(bookDTO.getPrice());
    			total += item.getQuantity() * bookDTO.getPrice();
    			orderDTO.getDetails().add(detail);
    		}
    		orderDTO.setTotal(transport == ShippingMethod.Standard ? total : total + 10000);
        	switch(payment) {
    	    	case (1): {
    	    		orderDTO.setPayment(Payment.COD);
    	    		break;
    	    	}
    	    	case (2): {
    	    		orderDTO.setPayment(Payment.Visa);
    	    		break;
    	    	}
        	}
        	ShippingDTO shippingDTO = new ShippingDTO();
        	shippingDTO.setMethod(transport);
        	orderDTO.setShipping(shippingDTO);
        	
        	if (OrderDAO.add(orderDTO)) {
        		Cart cart = CartDAO.get(cartId);
        		cart.setStatus(CartStatus.unavailable);
        		if (CartDAO.update(cart)) {
        			response.sendRedirect("./order.jsp");
        		}
        		else {
        			response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
        		}
        	}
        	else {
        		response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
        	}
    	}
    	else {
    		response.sendRedirect("./login.jsp");
    	}
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if (session != null) {
    		int orderId = Integer.parseInt(request.getParameter("orderId"));
    		int userId = Integer.parseInt(request.getParameter("userId"));
    		UserDTO user = (UserDTO) session.getAttribute("USER");
    		if (user.getId() == userId) {
    			OrderDTO orderDTO = OrderDAO.find(orderId);
    			request.setAttribute("ORDER", orderDTO);
    			request.getRequestDispatcher("./detail.jsp").forward(request, response);
    		}
    		else {
    			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not allowed here");
    		}
    	}
    	else {
    		response.sendRedirect("./login.jsp");
    	}
	}
    
    protected void cancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int orderId = Integer.parseInt(request.getParameter("orderId"));
    	boolean result = OrderDAO.cancel(orderId);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}
    
    protected void getInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    	if (userDTO == null || userDTO.getRole() != Role.admin) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not alllowed here");
    		return;
    	}
    	BufferedReader reader = request.getReader();
    	int orderId = gson.fromJson(reader, int.class);
    	OrderDTO orderDTO = OrderDAO.find(orderId);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(orderDTO));
	}
    
    protected void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    	if (userDTO == null || userDTO.getRole() != Role.admin) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not alllowed here");
    		return;
    	}
    	BufferedReader reader = request.getReader();
    	OrderStatus orderStatus = gson.fromJson(reader, OrderStatus.class);
    	int orderId = Integer.parseInt(request.getParameter("orderId"));
    	boolean result;
    	if (orderStatus == OrderStatus.Cancelled) {
    		result = OrderDAO.cancel(orderId);
    	}
    	else {
    		Order order = OrderDAO.get(orderId);
        	order.setStatus(orderStatus);
        	result = OrderDAO.update(order);
    	}
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		switch(action) {
			case("create"): {
				create(request, response);
				break;
			}
			case("detail"): {
				detail(request, response);
				break;
			}
			case("cancel"): {
				cancel(request, response);
				break;
			}
			case("getInfo"): {
				getInfo(request, response);
				break;
			}
			case("updateStatus"): {
				updateStatus(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
