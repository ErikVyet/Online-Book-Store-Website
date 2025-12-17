package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.PublisherDAO;
import dto.PublisherDTO;
import dto.UserDTO;
import model.Publisher;
import model.Role;
import model.SearchModel;

@WebServlet("/publisher")
public class PublisherController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private Gson gson = new Gson();
	
    public PublisherController() {
        super();
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String address = request.getParameter("address");
    	String phone = request.getParameter("phone");
    	String email = request.getParameter("email");
    	
    	Publisher publisher = new Publisher();
    	publisher.setName(name);
    	publisher.setAddress(address);
    	publisher.setPhone(phone);
    	publisher.setEmail(email);
    	
    	if (PublisherDAO.add(publisher)) {
    		response.sendRedirect("./publishers.jsp");
    	}
    	else {
    		response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
    	}
	}
    
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	int publisherId = gson.fromJson(reader, int.class);
    	boolean result = PublisherDAO.remove(publisherId);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}
    
    protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	SearchModel search = gson.fromJson(reader, SearchModel.class);
    	List<PublisherDTO> result = PublisherDAO.getListByName(search.getContent());
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int publisherId = Integer.parseInt(request.getParameter("id"));
    	PublisherDTO publisherDTO = PublisherDAO.find(publisherId);
    	
    	request.setAttribute("PUBLISHER", publisherDTO);
    	request.getRequestDispatcher("./publisherDetailEditForm.jsp").forward(request, response);
	}
    
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int publisherId = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	String address = request.getParameter("address");
    	String phone = request.getParameter("phone");
    	String email = request.getParameter("email");
    	
    	Publisher publisher = PublisherDAO.get(publisherId);
    	publisher.setName(name);
    	publisher.setAddress(address);
    	publisher.setPhone(phone);
    	publisher.setEmail(email);
    	
    	if (PublisherDAO.update(publisher)) {
    		response.sendRedirect("./publishers.jsp");
    	}
    	else {
    		response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
    	}
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
			case ("add"): {
				add(request, response);
				break;
			}
			case ("delete"): {
				delete(request, response);
				break;
			}
			case ("search"): {
				search(request, response);
				break;
			}
			case ("detail"): {
				detail(request, response);
				break;
			}
			case ("update"): {
				update(request, response);
				break;
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
