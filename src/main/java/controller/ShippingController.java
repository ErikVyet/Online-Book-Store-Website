package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import dao.ShippingDAO;
import dto.ShippingDTO;
import dto.UserDTO;
import model.Role;
import model.Shipping;

@WebServlet("/shipping")
public class ShippingController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
    	@Override
    	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    		return Date.valueOf(json.getAsString());
    	}
    }).create();
	
    public ShippingController() {
        super();
    }
    
    protected void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		ShippingDTO shippingDTO = gson.fromJson(reader, ShippingDTO.class);
		Shipping shipping = ShippingDAO.get(shippingDTO.getId());
		shipping.setStatus(shippingDTO.getStatus());
		shipping.setShipped_date(shippingDTO.getShipped_date());
		boolean result = ShippingDAO.update(shipping);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(result));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("./login.jsp");
			return;
		}
		UserDTO userDTO = (UserDTO) session.getAttribute("USER");
		if (userDTO == null || userDTO.getRole() != Role.admin) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		switch(action) {
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
