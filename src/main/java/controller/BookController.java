package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.CategoryDAO;
import dao.PublisherDAO;
import dao.ReviewDAO;
import dto.BookDTO;
import dto.UserDTO;
import model.Author;
import model.Book;
import model.Category;
import model.Publisher;
import model.Review;
import model.ReviewId;
import model.Role;
import model.SearchModel;

@WebServlet("/book")
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 10,
	maxRequestSize = 1024 * 1024 * 50
)
public class BookController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();
       
    public BookController() {
        super();
    }
    
    protected void getBooksByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	int id = Integer.parseInt(gson.fromJson(reader, String.class));
    	List<BookDTO> books = BookDAO.getListByCategory(id);

    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(gson.toJson(books));
	}
    
    protected void getAllBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<BookDTO> books = BookDAO.getList();

    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(gson.toJson(books));
	}
    
    protected void getBookById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	int id = Integer.parseInt(gson.fromJson(reader, String.class));
    	BookDTO book = BookDAO.find(id);
    	
    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(gson.toJson(book));
	}
    
    protected void seeAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<BookDTO> books = BookDAO.getSortedListByRating(false);

    	request.setAttribute("COUNT", books.size());
    	request.setAttribute("RESULT", books);
    	request.getRequestDispatcher("./result.jsp").forward(request, response);
	}
    
    protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	
    	Boolean guest = (Boolean)session.getAttribute("GUEST");
    	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    	if (guest == null && userDTO != null && userDTO.getRole() == Role.admin) {
    		BufferedReader reader = request.getReader();
    		SearchModel search = gson.fromJson(reader, SearchModel.class);
    		List<BookDTO> result = BookDAO.getListByTitle(search.getContent());
    		if (result == null) {
    			result = BookDAO.getList();
    		}
    		
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("application/json");
    		response.getWriter().write(gson.toJson(result));
    	}
    	else {
    		String content = request.getParameter("content");
        	List<BookDTO> books = new ArrayList<BookDTO>();
        	if (content.isEmpty()) {
        		books = BookDAO.getList();
        	}
        	else {
        		 books = BookDAO.getListByTitle(content);
        	}
        	
        	request.setAttribute("COUNT", books.size());
        	request.setAttribute("RESULT", books);
        	request.getRequestDispatcher("./result.jsp").forward(request, response);
    	}
	}
    
    protected void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	
    	Boolean guest = (Boolean)session.getAttribute("GUEST");
    	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    	if (guest == null && userDTO != null && userDTO.getRole() == Role.admin) {
    		BufferedReader reader = request.getReader();
    		String option = request.getParameter("option");
    		boolean asc = gson.fromJson(reader, boolean.class);
    		
    		List<BookDTO> books = BookDAO.getSortedList(option, asc);
    		
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("application/json");
    		response.getWriter().write(gson.toJson(books));
    	}
    	else {
    		int categoryId = Integer.parseInt(request.getParameter("filter-option"));
        	List<BookDTO> books = BookDAO.getListByCategory(categoryId);
        	
        	request.setAttribute("COUNT", books.size());
        	request.setAttribute("RESULT", books);
        	request.getRequestDispatcher("./result.jsp").forward(request, response);
    	}
	}
    
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    	int id = Integer.parseInt(request.getParameter("id"));
    	BookDTO bookDTO = BookDAO.find(id);
    	request.setAttribute("BOOK", bookDTO);
    	String url = (userDTO != null && userDTO.getRole() == Role.admin) ? "./bookDetailEditForm.jsp" : "./info.jsp";
    	request.getRequestDispatcher(url).forward(request, response);
	}
    
    protected void rating(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BufferedReader reader = request.getReader();
    	ReviewId reviewId = gson.fromJson(reader, ReviewId.class);
    	int rating = Integer.parseInt(request.getParameter("rate"));
    	
    	Review review = new Review();
    	review.setId(reviewId);
    	review.setRating(rating);
    	
    	ReviewDAO.update(review);
    	
    	int newRating = BookDAO.find(reviewId.getBookId()).getRating();
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(newRating));
	}
    
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if (session != null) {
    		UserDTO userDTO = (UserDTO) session.getAttribute("USER");
    		if (userDTO.getRole() == Role.admin) {
    			Part imagePart = request.getPart("image");
    			String fileName = imagePart.getSubmittedFileName();
    			String folderPath = getServletContext().getRealPath("/resource/image/book");
    			String developmentPath = folderPath + File.separator + fileName;
    			
    			File dir = new File(folderPath);
    			if (!dir.exists()) {
    				dir.mkdirs();
    			}
    				
    			Book book = new Book();
    			book.setTitle(request.getParameter("title"));
    			book.setQuantity(Integer.parseInt(request.getParameter("quantity")));
    			book.setPrice(Double.parseDouble(request.getParameter("price")));
    			book.setDescription(request.getParameter("description"));
    			book.setPublication(Date.valueOf(request.getParameter("publication")));
    			book.setCategory(CategoryDAO.get(Integer.parseInt(request.getParameter("category"))));
    			book.setAuthor(AuthorDAO.get(Integer.parseInt(request.getParameter("author"))));
    			book.setPublisher(PublisherDAO.get(Integer.parseInt(request.getParameter("publisher"))));
    			book.setImage("./resource/image/book/" + fileName);
    			
    			if (BookDAO.add(book)) {
    				imagePart.write(developmentPath);
    				response.sendRedirect("./books.jsp");
    			}
    			else {
    				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "COULD NOT INSERT NEW BOOK INTO DATABASE");
    			}
    		}
    		else {
    			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
    		}
    		return;
    	}
    	response.sendRedirect("./login.jsp");
	}
    
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	UserDTO userDTO = (UserDTO)session.getAttribute("USER");
    	if (userDTO.getRole() != Role.admin) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
    		return;
    	}
    	int bookId = Integer.parseInt(request.getParameter("id"));
    	System.out.println(bookId);
    	boolean result = BookDAO.remove(bookId);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
    	response.getWriter().write(gson.toJson(result));
	}
    
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendRedirect("./login.jsp");
    		return;
    	}
    	UserDTO userDTO = (UserDTO)session.getAttribute("USER");
    	if (userDTO.getRole() != Role.admin) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed here");
    		return;
    	}
    	
    	int id = Integer.parseInt(request.getParameter("id"));
    	String title = request.getParameter("title");
    	Category category = CategoryDAO.get(Integer.parseInt(request.getParameter("category")));
    	Author author = AuthorDAO.get(Integer.parseInt(request.getParameter("author")));
    	Publisher publisher = PublisherDAO.get(Integer.parseInt(request.getParameter("publisher")));
    	int quantity = Integer.parseInt(request.getParameter("quantity"));
    	double price = Double.parseDouble(request.getParameter("price"));
    	String description = request.getParameter("description");
    	Date publication = Date.valueOf(request.getParameter("publication"));
    	Part imagePart = request.getPart("image");
    	
    	Book book = BookDAO.get(id);
    	book.setTitle(title);
    	book.setCategory(category);
    	book.setAuthor(author);
    	book.setPublisher(publisher);
    	book.setQuantity(quantity);
    	book.setPrice(price);
    	book.setDescription(description);
    	book.setPublication(publication);
    	
    	String fileName = imagePart.getSubmittedFileName();
    	String folderPath = getServletContext().getRealPath("/resource/image/book");
    	File dir = new File(folderPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!fileName.isBlank()) {
			book.setImage("./resource/image/book" + File.separator + fileName);
			imagePart.write(folderPath + File.separator + fileName);
    	}
    	
    	if (BookDAO.update(book)) {
    		response.sendRedirect("./books.jsp");
    	}
    	else {
    		response.sendError(HttpServletResponse.SC_CONFLICT , "Something went wrong, please try again later");
    	}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		switch(action) {
			case("getBooksByCategory"): {
				getBooksByCategory(request, response);
				break;
			}
			case("getAllBook"): {
				getAllBook(request, response);	
				break;
			}
			case("getBookById"): {
				getBookById(request, response);
				break;
			}
			case("search"): {
				search(request, response);
				break;
			}
			case("filter"): {
				filter(request, response);
				break;
			}
			case("seeAll"): {
				seeAll(request, response);
				break;
			}
			case("detail"): {
				detail(request, response);
				break;
			}
			case("rating"): {
				rating(request, response);
				break;
			}
			case("add"): {
				add(request, response);
				break;
			}
			case("delete"): {
				delete(request, response);
				break;
			}
			case("update"): {
				update(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
