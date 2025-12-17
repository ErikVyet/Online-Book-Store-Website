package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.CategoryDAO;
import dao.OrderDAO;
import dao.PublisherDAO;
import dao.ShippingDAO;
import dao.UserDAO;
import dto.AuthorDTO;
import dto.BookDTO;
import dto.CategoryDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.PublisherDTO;
import dto.ShippingDTO;
import dto.UserDTO;
import model.OrderStatus;
import model.Payment;
import model.Role;
import model.ShippingStatus;

@WebServlet("/statistic")
public class StatisticController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();
       
    public StatisticController() {
        super();
    }
    
    protected void books(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case(0): {
				List<CategoryDTO> categoryDTOs = CategoryDAO.getList();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(categoryDTOs));
				break;
			}
			case(1): {
				List<AuthorDTO> authorDTOs = AuthorDAO.getList();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(authorDTOs));
				break;
			}
			case(2): {
				List<PublisherDTO> publisherDTOs = PublisherDAO.getList();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(publisherDTOs));
				break;
			}
			case(3): {
				List<ShippingDTO> shippingDTOs = ShippingDAO.getList();
				List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
				for (ShippingDTO shippingDTO : shippingDTOs) {
					if (shippingDTO.getStatus() == ShippingStatus.Delivered) {
						orderDTOs.add(OrderDAO.find(shippingDTO.getOrderId()));
					}
				}
				
				Dictionary<Integer, Integer> dictionary = new Hashtable<Integer, Integer>();
				for (OrderDTO orderDTO : orderDTOs) {
					for (OrderDetailDTO orderDetailDTO : orderDTO.getDetails()) {
						int bookId = orderDetailDTO.getBookId();
						if (dictionary.get(bookId) != null) {
							int preValue = dictionary.remove(bookId);
							dictionary.put(bookId, orderDetailDTO.getQuantity() + preValue);
						}
						else {
							dictionary.put(bookId, orderDetailDTO.getQuantity());
						}
					}
				}
				List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
				Enumeration<Integer> bookIds = dictionary.keys();
				Enumeration<Integer> quantity = dictionary.elements();
				while (bookIds.hasMoreElements() && quantity.hasMoreElements()) {
					BookDTO bookDTO = BookDAO.find(bookIds.nextElement());
					bookDTO.setQuantity(quantity.nextElement());
					bookDTOs.add(bookDTO);
				}
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(bookDTOs));
				break;
			}
			case(4):
			case(5): {
				List<BookDTO> bookDTOs = BookDAO.getList();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(bookDTOs));
				break;
			}
		}
	}
    
    protected void revenues(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int option = Integer.parseInt(request.getParameter("option"));
    	switch(option) {
	    	case(0): {
	    		List<ShippingDTO> shippingDTOs = ShippingDAO.getDeliveredShippingList();
	    		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
	    		for (ShippingDTO shippingDTO : shippingDTOs) {
	    			orderDTOs.add(OrderDAO.find(shippingDTO.getOrderId()));
	    		}
	    		Map<Integer, Double> map = new HashMap<Integer, Double>();
	    		for (OrderDTO orderDTO : orderDTOs) {
	    			Date shippedDate = orderDTO.getShipping().getShipped_date();
	    			if (LocalDate.now().getYear() == (shippedDate.getYear() + 1900)) {
	    				int month = shippedDate.getMonth() + 1;
		    			if (map.containsKey(month)) {
		    				double total = map.remove(month);
		    				map.put(month, orderDTO.getTotal() + total);
		    			}
		    			else {
		    				map.put(month, orderDTO.getTotal());
		    			}
	    			}
	    		}
	    		List<Double> totals = new ArrayList<Double>(); 
	    		for (double value : map.values()) {
	    			totals.add(value);
	    		}
	    		
	    		response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(totals));
	    		break;
	    	}
	    	case(1): {
	    		List<ShippingDTO> shippingDTOs = ShippingDAO.getDeliveredShippingList();
	    		Dictionary<Integer, Double> dictionary = new Hashtable<Integer, Double>();
	    		for (ShippingDTO shippingDTO : shippingDTOs) {
	    			OrderDTO orderDTO = OrderDAO.find(shippingDTO.getOrderId());
	    			int year = shippingDTO.getShipped_date().getYear() + 1900;
	    			if (dictionary.get(year) != null) {
	    				double preTotal = dictionary.remove(year);
	    				dictionary.put(year, orderDTO.getTotal() + preTotal);
	    			}
	    			else {
	    				dictionary.put(year, orderDTO.getTotal());
	    			}
	    		}
	    		
	    		List<Object[]> result = new ArrayList<Object[]>();
	    		Enumeration<Integer> years = dictionary.keys();
	    		Enumeration<Double> totals = dictionary.elements();
	    		while (years.hasMoreElements() && totals.hasMoreElements()) {
	    			Object[] obj = new Object[] {
	    				years.nextElement(),
	    				totals.nextElement()
	    			};
	    			result.add(obj);
	    		}
	    		
	    		Collections.sort(result, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						return Integer.compare((int)o1[0], (int)o2[0]);
					}
	    			
	    		});
	    		
	    		response.setCharacterEncoding("UTF-8");
	    		response.setContentType("application/json");
	    		response.getWriter().write(gson.toJson(result));
	    		break;
	    	}
	    	case(2): {
	    		List<ShippingDTO> shippingDTOs = ShippingDAO.getDeliveredShippingList();
	    		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
	    		for (ShippingDTO shippingDTO : shippingDTOs) {
	    			orderDTOs.add(OrderDAO.find(shippingDTO.getOrderId()));
	    		}
	    		
	    		Dictionary<Integer, Integer> bookDictionary = new Hashtable<Integer, Integer>();
	    		for (OrderDTO orderDTO : orderDTOs) {
	    			for (OrderDetailDTO detailDTO : orderDTO.getDetails()) {
	    				int bookId = detailDTO.getBookId();
	    				if (bookDictionary.get(bookId) != null) {
	    					int preQuantity = bookDictionary.remove(bookId);
	    					bookDictionary.put(bookId, detailDTO.getQuantity() + preQuantity);
	    				}
	    				else {
	    					bookDictionary.put(bookId, detailDTO.getQuantity());
	    				}
	    			}
	    		}
	    		
	    		List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
	    		Enumeration<Integer> bookIds = bookDictionary.keys();
	    		Enumeration<Integer> bookQuantites = bookDictionary.elements();
	    		while (bookIds.hasMoreElements() && bookQuantites.hasMoreElements()) {
	    			BookDTO bookDTO = BookDAO.find(bookIds.nextElement());
	    			bookDTO.setPrice(bookDTO.getPrice() * bookQuantites.nextElement());
	    			bookDTOs.add(bookDTO);
	    		}
	    		
	    		Dictionary<Integer, Double> categoryDictionary = new Hashtable<Integer, Double>();
	    		for (BookDTO bookDTO : bookDTOs) {
	    			int categoryId = bookDTO.getCategoryId();
	    			if (categoryDictionary.get(categoryId) != null) {
	    				double prePrice = categoryDictionary.remove(categoryId);
	    				categoryDictionary.put(categoryId, bookDTO.getPrice() + prePrice);
	    			}
	    			else {
	    				categoryDictionary.put(categoryId, bookDTO.getPrice());
	    			}
	    		}
	    		List<CategoryDTO> categoryDTOs = CategoryDAO.getList();
	    		for (CategoryDTO categoryDTO : categoryDTOs) {
	    			int categoryId = categoryDTO.getId();
	    			if (categoryDictionary.get(categoryId) == null) {
	    				categoryDictionary.put(categoryId, 0.0);
	    			}
	    		}
	    		
	    		List<Object[]> result = new ArrayList<Object[]>();
	    		Enumeration<Integer> categoryIds = categoryDictionary.keys();
	    		Enumeration<Double> totalPrices = categoryDictionary.elements();
	    		while (categoryIds.hasMoreElements() && totalPrices.hasMoreElements()) {
	    			Object[] obj = new Object[] { 
	    				CategoryDAO.find(categoryIds.nextElement()).getName(), 
	    				totalPrices.nextElement() 
	    			};
	    			result.add(obj);
	    		}
	    		
	    		response.setCharacterEncoding("UTF-8");
	    		response.setContentType("application/json");
	    		response.getWriter().write(gson.toJson(result));
	    		break;
	    	}
    	}
	}
    
    protected void orders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case(0): {
				List<OrderDTO> orderDTOs = OrderDAO.getList();
				Dictionary<Integer, Integer> dictionary = new Hashtable<Integer, Integer>();
				for (OrderDTO orderDTO : orderDTOs) {
					int month = orderDTO.getDate().getMonth() + 1;
					if (dictionary.get(month) != null) {
						int preQuantity = dictionary.remove(month);
						dictionary.put(month, ++preQuantity);
					}
					else {
						dictionary.put(month, 1);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<Integer> months = dictionary.keys();
				Enumeration<Integer> quantites = dictionary.elements();
				while (months.hasMoreElements() && quantites.hasMoreElements()) {
					Object[] obj = new Object[] {
						months.nextElement(),
						quantites.nextElement()
					};
					result.add(obj);
				}
				
				Collections.sort(result, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						return Integer.compare((int)o1[0], (int)o2[0]);
					}
				});
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(1): {
				List<OrderDTO> orderDTOs = OrderDAO.getList();
				Dictionary<OrderStatus, Integer> dictionary = new Hashtable<OrderStatus, Integer>();
				for (OrderDTO orderDTO : orderDTOs) {
					OrderStatus status = orderDTO.getStatus();
					if (dictionary.get(status) != null) {
						int preQuantity = dictionary.remove(status);
						dictionary.put(status, ++preQuantity);
					}
					else {
						dictionary.put(status, 1);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<OrderStatus> keys = dictionary.keys();
				while (keys.hasMoreElements()) {
					OrderStatus status = keys.nextElement();
					Object[] obj = new Object[] {
						status,
						dictionary.get(status)
					};
					result.add(obj);
				}
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(2): {
				List<OrderDTO> orderDTOs = OrderDAO.getList();
				Dictionary<Payment, Integer> dictionary = new Hashtable<Payment, Integer>();
				for (OrderDTO orderDTO : orderDTOs) {
					Payment payment = orderDTO.getPayment();
					if (dictionary.get(payment) != null) {
						int preQuantity = dictionary.remove(payment);
						dictionary.put(payment, ++preQuantity);
					}
					else {
						dictionary.put(payment, 1);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<Payment> keys = dictionary.keys();
				while (keys.hasMoreElements()) {
					Payment payment = keys.nextElement();
					Object[] obj = new Object[] {
						payment,
						dictionary.get(payment)
					};
					result.add(obj);
				}
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
		}
	}
    
    protected void customers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case(0): {
				List<UserDTO> userDTOs = UserDAO.getList();
				Dictionary<Integer, Integer> userDictionary = new Hashtable<Integer, Integer>();
				for (UserDTO userDTO : userDTOs) {
					Date created = userDTO.getCreated();
					if (LocalDate.now().getYear() == (created.getYear() + 1900)) {
						int month = created.getMonth() + 1;
						if (userDictionary.get(month) != null) {
							int preQuantity = userDictionary.remove(month);
							userDictionary.put(month, ++preQuantity);
						}
						else {
							userDictionary.put(month, 1);
						}
					}
				}
				
				for (int i = 1; i <= 12; i++) {
					if (userDictionary.get(i) == null) {
						userDictionary.put(i, 0);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<Integer> months = userDictionary.keys();
				while (months.hasMoreElements()) {
					int month = months.nextElement();
					Object[] obj = new Object[] {
						month,
						userDictionary.get(month)
					};
					result.add(obj);
				}
				
				Collections.sort(result, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						return Integer.compare((int)o1[0], (int)o2[0]);
					}
				});
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(1): {
				List<UserDTO> userDTOs = UserDAO.getList();
				Dictionary<Integer, Integer> userDictionary = new Hashtable<Integer, Integer>();
				for (UserDTO userDTO : userDTOs) {
					int year = userDTO.getCreated().getYear() + 1900;
					if (userDictionary.get(year) != null) {
						int preQuantity = userDictionary.remove(year);
						userDictionary.put(year, ++preQuantity);
					}
					else {
						userDictionary.put(year, 1);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<Integer> years = userDictionary.keys();
				while (years.hasMoreElements()) {
					int year = years.nextElement();
					Object[] obj = new Object[] {
						year,
						userDictionary.get(year)
					};
					result.add(obj);
				}
				
				Collections.sort(result, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						return Integer.compare((int)o1[0], (int)o2[0]);
					}
				});
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(2): {
				List<UserDTO> userDTOs = UserDAO.getList();
				Dictionary<String, Integer> userDictionary = new Hashtable<String, Integer>();
				for (UserDTO userDTO : userDTOs) {
					String address = userDTO.getAddress();
					if (userDictionary.get(address) != null) {
						int preQuantity = userDictionary.remove(address);
						userDictionary.put(address, ++preQuantity);
					}
					else {
						userDictionary.put(address, 1);
					}
				}
				
				List<Object[]> result = new ArrayList<Object[]>();
				Enumeration<String> addresses = userDictionary.keys();
				while (addresses.hasMoreElements()) {
					String address = addresses.nextElement();
					Object[] obj = new Object[] {
						address,
						userDictionary.get(address)
					};
					result.add(obj);
				}
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(3): {
				List<UserDTO> result = UserDAO.getList();
				Collections.sort(result, new Comparator<UserDTO>() {
					@Override
					public int compare(UserDTO o1, UserDTO o2) {
						return Integer.compare(o1.getOrders().size(), o2.getOrders().size());
					}
				}.reversed());
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
			case(4): {
				List<UserDTO> userDTOs = UserDAO.getList();
				List<Object[]> result = new ArrayList<Object[]>();
				for (UserDTO userDTO : userDTOs) {
					List<OrderDTO> orderDTOs = userDTO.getOrders();
					double total = 0;
					for (OrderDTO orderDTO : orderDTOs) {
						if (orderDTO.getShipping().getStatus() == ShippingStatus.Delivered) {
							total += orderDTO.getTotal();
						}
					}
					Object[] obj = new Object[] {
						userDTO.getId(),
						total
					};
					result.add(obj);
				}
				
				Collections.sort(result, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						return Double.compare((double)o1[1], (double)o2[1]);
					}
				}.reversed());
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(gson.toJson(result));
				break;
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("./login.jsp");
			return;
		}
		UserDTO userDTO = (UserDTO) session.getAttribute("USER");
		if (userDTO != null && userDTO.getRole() != Role.admin) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
			return;
		}
		String action = request.getParameter("action");
		switch(action) {
			case("books"): {
				books(request, response);
				break;
			}
			case("revenues"): {
				revenues(request, response);
				break;
			}
			case("orders"): {
				orders(request, response);
				break;
			}
			case("customers"): {
				customers(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
