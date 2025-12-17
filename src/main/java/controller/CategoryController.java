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

import dao.CategoryDAO;
import dto.CategoryDTO;
import dto.UserDTO;
import model.Category;
import model.Role;
import model.SearchModel;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();
       
    public CategoryController() {
        super();
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		
		if (CategoryDAO.add(category)) {
			response.sendRedirect("./categories.jsp");
		}
		else {
			response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
		}
	}
    
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		int id = gson.fromJson(reader, int.class);
		boolean result = CategoryDAO.remove(id);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(result));
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		CategoryDTO categoryDTO = CategoryDAO.find(id);
		request.setAttribute("CATEGORY", categoryDTO);
		request.getRequestDispatcher("./categoryDetailEditForm.jsp").forward(request, response);
	}
    
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Category category = CategoryDAO.get(id);
		category.setName(name);
		category.setDescription(description);
		
		if (CategoryDAO.update(category)) {
			response.sendRedirect("./categories.jsp");
		}
		else {
			response.sendError(HttpServletResponse.SC_CONFLICT, "Something went wrong, please try again later");
		}
	}
    
    protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		SearchModel search = gson.fromJson(reader, SearchModel.class);
		List<CategoryDTO> result = CategoryDAO.getListByName(search.getContent());
		
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
