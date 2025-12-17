package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

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

import dao.UserDAO;
import dto.UserDTO;
import model.Role;
import model.User;
import utility.MailManager;

@WebServlet("/user")
public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class, new JsonDeserializer<java.sql.Date>() {
		@Override
	    public java.sql.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return java.sql.Date.valueOf(json.getAsString());
	    }
	}).create();
       
    public UserController() {
        super();
    }
    
    protected void guestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session != null) {
    		session.invalidate();
    		session = request.getSession();
    	}
    	session.setAttribute("GUEST", true);
    	session.setMaxInactiveInterval(900);
    	response.sendRedirect("./home.jsp");
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		UserDTO user = UserDAO.getUser(username, password, email);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("USER", user);
			session.setMaxInactiveInterval(1800);
			if (user.getRole() == Role.customer) {
				response.sendRedirect("./home.jsp");
			}
			else {
				response.sendRedirect("./dashboard.jsp");
			}
		}
		else {
			String error = "Incorrect infomation";
			request.setAttribute("ERROR", error);
			request.getRequestDispatcher("./login.jsp").forward(request, response);
		}
	}
    
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session != null) {
    		session.invalidate();
    	}
    	response.sendRedirect("./login.jsp");
    }
    
    protected void getCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	String recipient = gson.fromJson(reader, String.class);
    	String req = request.getParameter("req");
    	
    	String code = "";
    	Random random = new Random();
    	for (int i = 0; i < 6; i++) {
    		code += random.nextInt(10);
    	}
    	
    	String subject, message;
    	if (req.equals("register")) {
    		subject = "Register New Account";
    		message = String.format("Hello, to register a new account, please enter this verification code: %s.\nThanks for trusting our service.\n\nBest regards, BookZ Development Team.", code);
    	}
    	else {
    		subject = "Password Recovery";
    		message = String.format("Hello, to reset your password, please enter this verification code: %s.\nThanks for trusting our service.\n\nBest regards, BookZ Development Team.", code);
    	}
   
    	boolean result = MailManager.sendMessage(subject, message, recipient);

    	if (result) {
    		HttpSession session = request.getSession(false);
    		if (session != null) {
    			session.invalidate();
    		}
    		session = request.getSession();
    		session.setAttribute("CODE", code);
    		session.setMaxInactiveInterval(60);
    	}
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(gson.toJson(result));
    }
    
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String orginalCode = (String) session.getAttribute("CODE");
    	
    	String fullname = request.getParameter("fullname");
    	String phone = request.getParameter("phone");
    	String address = request.getParameter("address");
    	String email = request.getParameter("email");
    	String code = request.getParameter("code");
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	if (orginalCode != null) {
    		if (orginalCode.equals(code)) {
    			User user = new User();
            	user.setFullname(fullname);
            	user.setPhone(phone);
            	user.setAddress(address);
            	user.setEmail(email);
            	user.setUsername(username);
            	user.setPassword(password);
            	user.setRole(Role.customer);
            	user.setCreated(Date.valueOf(LocalDate.now()));
            	session.invalidate();
            	if (UserDAO.add(user)) {
            		request.setAttribute("USERNAME", username);
            		request.setAttribute("EMAIL", email);
            		request.getRequestDispatcher("./login.jsp").forward(request, response);
            	}
            	else {
            		request.setAttribute("ERROR", "Email already existed");
            	}
    		}
    		else {
    			request.setAttribute("ERROR", "Invalid verification code");
    		}
    	}
    	else {
    		request.setAttribute("ERROR", "Verification code expired");
    	}
    	request.getRequestDispatcher("./register.jsp").forward(request, response);
    }
    
    protected void recovery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String originalCode = (String) session.getAttribute("CODE");
    	
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String confirmPassword = request.getParameter("confirm-password");
    	String code = request.getParameter("code");
    	
    	if (originalCode != null) {
    		if (originalCode.equals(code)) {
    			if (password.equals(confirmPassword)) {
    				if (UserDAO.updatePassword(email, password)) {
    					session.invalidate();
    					request.getRequestDispatcher("./login.jsp").forward(request, response);
    				}
    				else {
    					request.setAttribute("ERROR", "Something went wrong");
    				}
    			}
    			else {
    				request.setAttribute("ERROR", "Password confirmation must match new password");
    			}
    		}
    		else {
    			request.setAttribute("PASSWORD", password);
        		request.setAttribute("CONFIRM_PASSWORD", confirmPassword);
    			request.setAttribute("ERROR", "Incorrect verification code");
    		}
    	}
    	else {
    		request.setAttribute("PASSWORD", password);
    		request.setAttribute("CONFIRM_PASSWORD", confirmPassword);
    		request.setAttribute("ERROR", "Verification code expired");
    	}
    	request.setAttribute("EMAIL", email);
    	request.getRequestDispatcher("./recovery.jsp").forward(request, response);
    }
    
    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	User user = gson.fromJson(reader, User.class);
    	boolean result = UserDAO.update(user);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		switch(action) {
			case("guestLogin"): {
				guestLogin(request, response);
				break;
			}
			case("login"): {
				login(request, response);
				break;
			}
			case("logout"): {
				logout(request, response);
				break;
			}
			case("getCode"): {
				getCode(request, response);
				break;
			}
			case("register"): {
				register(request, response);
				break;
			}
			case("recovery"): {
				recovery(request, response);
				break;
			}
			case("edit"): {
				edit(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
