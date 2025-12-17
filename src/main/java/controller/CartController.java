package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.BookDAO;
import dao.CartDAO;
import dao.CartItemDAO;
import dto.BookDTO;
import dto.CartDTO;
import dto.CartItemDTO;
import dto.UserDTO;
import model.CartItem;
import model.CartItemId;
import model.CartStatus;

@WebServlet("/cart")
public class CartController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();
       
    public CartController() {
        super();
    }
    
    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		int quantity = gson.fromJson(reader, int.class);
		int userId = Integer.parseInt(request.getParameter("userId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		boolean result = true;
		
		CartItemDTO item = new CartItemDTO(-1, bookId, quantity);
		List<CartItemDTO> items = new ArrayList<CartItemDTO>();
		items.add(item);
		CartDTO cart = new CartDTO(
			userId,
			Date.valueOf(LocalDate.now()),
			CartStatus.available,
			items
		);
		if (!CartDAO.add(cart)) {
			result = false;
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(result));
		
	}
    
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		CartItemId id = gson.fromJson(reader, CartItemId.class);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem item = new CartItem();
		item.setId(id);
		item.setQuantity(item.getQuantity() + quantity);
		boolean result = CartItemDAO.update(item);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(result));
	}
    
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		int id = gson.fromJson(reader, int.class);
		boolean result = CartDAO.remove(id);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(result));
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int cartId = Integer.parseInt(request.getParameter("cartId"));
		HttpSession session = request.getSession(false);
		if (session != null) {
			UserDTO user = (UserDTO)session.getAttribute("USER");
			if (user.getId() != userId) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission on this site");
			}
		}
		CartDTO cart = CartDAO.find(cartId);
		double total = 0;
		for (CartItemDTO item : cart.getItems()) {
			total += item.getQuantity() * BookDAO.find(item.getBookId()).getPrice();
		}
		request.setAttribute("CART", cart);
		request.setAttribute("COST", total);
		request.getRequestDispatcher("./item.jsp").forward(request, response);
	}
    
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		CartItemId id = gson.fromJson(reader, CartItemId.class);
		double total = -1;
		if (CartItemDAO.remove(id)) {
			total = 0;
			List<CartItemDTO> items = CartDAO.find(id.getCartId()).getItems();
			for (CartItemDTO item : items) {
				BookDTO book = BookDAO.find(item.getBookId());
				total += item.getQuantity() * book.getPrice();
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(total));
	}
    
    protected void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		CartItem item = gson.fromJson(reader, CartItem.class);
		double total = -1;
		if (CartItemDAO.update(item)) {
			total = 0;
			List<CartItemDTO> items = CartDAO.find(item.getId().getCartId()).getItems();
			for (CartItemDTO itemDTO : items) {
				BookDTO book = BookDAO.find(itemDTO.getBookId());
				total += itemDTO.getQuantity() * book.getPrice();
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(total));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		switch(action) {
			case ("create"): {
				create(request, response);
				break;
			}
			case("add"): {
				add(request, response);
				break;
			}
			case ("delete"): {
				delete(request, response);
				break;
			}
			case("detail"): {
				detail(request, response);
				break;
			}
			case("deleteItem"): {
				deleteItem(request, response);
				break;
			}
			case("updateItem"): {
				updateItem(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
