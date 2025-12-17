package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.AuthorDAO;
import dto.AuthorDTO;
import dto.UserDTO;
import model.Author;
import model.Role;
import model.SearchModel;

@WebServlet("/author")
public class AuthorController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();

    public AuthorController() {
        super();
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String bio = request.getParameter("bio");
    	Date birth = Date.valueOf(request.getParameter("birth"));
    	String country = request.getParameter("country");
    	
    	Author author = new Author();
    	author.setName(name);
    	author.setBio(bio);
    	author.setBirth(birth);
    	author.setCountry(country);
    	
    	if (AuthorDAO.add(author)) {
    		response.sendRedirect("./authors.jsp");
    	}
    	else {
    		response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
    	}
	}
    
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	int authorId = gson.fromJson(reader, int.class);
    	boolean result = AuthorDAO.remove(authorId);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int authorId = Integer.parseInt(request.getParameter("id"));
    	AuthorDTO authorDTO = AuthorDAO.find(authorId);
    	request.setAttribute("AUTHOR", authorDTO);
    	request.getRequestDispatcher("./authorDetailEditForm.jsp").forward(request, response);
	}
    
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int authorId = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	String bio = request.getParameter("bio");
    	Date birth = Date.valueOf(request.getParameter("birth"));
    	String country = request.getParameter("country");
    	
    	Author author = AuthorDAO.get(authorId);
    	author.setName(name);
    	author.setBio(bio);
    	author.setBirth(birth);
    	author.setCountry(country);
    	
    	if (AuthorDAO.update(author)) {
    		response.sendRedirect("./authors.jsp");
    	}
    	else {
    		response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
    	}
	}
    
    protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	SearchModel search = gson.fromJson(reader, SearchModel.class);
    	List<AuthorDTO> result = AuthorDAO.getListByName(search.getContent());
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect("./login.jsp");
			return;
		}
		UserDTO userDTO = (UserDTO) session.getAttribute("USER");
		if (userDTO.getRole() != Role.admin) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch(action) {
			case("add"): {
				add(request, response);
				break;
			}
			case("delete"): {
				delete(request, response);
				break;
			}
			case("detail"): {
				detail(request, response);
				break;
			}
			case("update"): {
				update(request, response);
				break;
			}
			case("search"): {
				search(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
